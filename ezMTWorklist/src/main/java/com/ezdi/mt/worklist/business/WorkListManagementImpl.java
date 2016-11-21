/**
 * 
 */
package com.ezdi.mt.worklist.business;

import com.ezdi.component.logger.EzdiLogManager;
import com.ezdi.component.logger.EzdiLogger;
import com.ezdi.mt.core.util.ProcessData;
import com.ezdi.mt.search.business.SearchManagement;
import com.ezdi.mt.search.constant.SearchConstant;
import com.ezdi.mt.search.dto.PreviousSearchCriteria;
import com.ezdi.mt.worklist.constants.Constants;
import com.ezdi.mt.worklist.dto.*;
import com.ezdi.mt.worklist.pojo.Bucket;
import com.ezdi.mt.worklist.pojo.StatusInfo;
import com.ezdi.mt.worklist.pojo.TranscriptionDocument;
import com.ezdi.mt.worklist.service.WorkListService;
import com.ezdi.mt.worklist.util.DocumentComparator;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author EZDI\atul.r
 *
 */
@Service
public class WorkListManagementImpl implements WorkListManagement {

    private static EzdiLogger LOG = EzdiLogManager.getLogger(WorkListManagementImpl.class);

    @Autowired
    WorkListService workListService;

    @Autowired
    private SearchManagement searchManagement;

	@Override
	public WorkListResponse getTranscriptionList(WorkListRequest workListRequest){
        LOG.info("Inside business getTranscriptionList");
        WorkListResponse workListResponse = new WorkListResponse();
        Data dataItemList = new Data();
        BeanFiller beanFiller = new BeanFiller();
        List<TranscriptionDocument> paginatedList = new ArrayList<TranscriptionDocument>();
        List<TranscriptionDocument> totalDocumentList = new ArrayList<TranscriptionDocument>();
        Map<Integer, String> statusMilestoneMap = convertStatusInfoListIntoMap(workListService.getStatusInfo());

        try {
            QueryResponse totalList = searchManagement.getSolrWorklistQueryResult(workListRequest);

            totalDocumentList = getTranscriptionList(totalList.getResults());
            getSortedByPrimary(totalDocumentList,workListRequest);

        }catch (Exception e) {
            getStatusList(workListRequest);
            totalDocumentList = workListService.getTranscriptionList(workListRequest);
        }

        getPaginatedList(paginatedList, totalDocumentList, workListRequest);
        Collections.sort(paginatedList);

		beanFiller.setResponseObject(workListResponse, workListRequest, paginatedList,totalDocumentList, statusMilestoneMap);

        dataItemList.setItemGroups(beanFiller.setItemGroups());
        beanFiller.setWorklistResponse();
		workListResponse.setData(dataItemList);
        LOG.info("exit from business getTranscriptionList");
		return workListResponse;
	}

    @Override
	public InitList getInitList(){
        LOG.info("Inside business getSortValues()");
		InitList initList = new InitList();
        List<Bucket> bucketList = null;
            bucketList = workListService.getBucketList();
            initList.setItemSortBy(getSortItem());
            if(ProcessData.isValidCollection(bucketList)){
                initList.setBucketList(getBucketItemList(bucketList));
            }

        LOG.info("Exit from  business getSortValues()");
		return initList;
	}

    private List<BucketListItem> getBucketItemList(List<Bucket> bucketList){
        Set<BucketListItem> bucketItemList = new TreeSet<BucketListItem>();
        List<BucketListItem> buckets = new ArrayList<BucketListItem>();
        for(Bucket bucket:bucketList){
            BucketListItem bucketListItem = new BucketListItem();
            bucketListItem.setBucketId(ProcessData.getAsString(bucket.getBucketId()));
            bucketListItem.setBucketName(bucket.getBucketValue());
            bucketItemList.add(bucketListItem);
        }
        buckets.addAll(bucketItemList);
        return buckets;
    }
	private List<ItemSortBy> getSortItem(){
		List<ItemSortBy> itemList = new ArrayList<ItemSortBy>();
		ItemSortBy tat = new ItemSortBy();
		ItemSortBy hospital = new ItemSortBy();
		ItemSortBy physician = new ItemSortBy();
		ItemSortBy worktype = new ItemSortBy();
		
		tat.setSelected(true);
		tat.setSortingAlias("Remaining TAT");
		tat.setSortingName("remaining_tat");
		
		hospital.setSelected(false);
		hospital.setSortingAlias("Hospital");
		hospital.setSortingName("hospital_value");
		
		physician.setSelected(false);
		physician.setSortingAlias("Dictating Physician");
		physician.setSortingName("dictating_physician");
		
		worktype.setSelected(false);
		worktype.setSortingAlias("Work type");
		worktype.setSortingName("worktype_value");
		itemList.add(tat);
		itemList.add(hospital);
		itemList.add(physician);
		itemList.add(worktype);
		return itemList;
	}

    List<TranscriptionDocument> getPaginatedList(List<TranscriptionDocument> paginatedList,
                                                 List<TranscriptionDocument> transcriptionDocList,
                                                 WorkListRequest workListRequest){
        LOG.info("Inside business getPaginatedList()");
        int start = workListRequest.getPagination().getStart();
        if(ProcessData.isValidCollection(transcriptionDocList)){
                for(int i = start-1; i<start+ Constants.MAX_COUNT-1 && i<transcriptionDocList.size(); i++ ){
                    paginatedList.add(transcriptionDocList.get(i));
                }
        }
        LOG.info("exit from business getPaginatedList()");
        return paginatedList;
    }

    List<TranscriptionDocument> getTranscriptionList(List<SolrDocument> solrDocumentList){
        List<TranscriptionDocument> documentList = new ArrayList<TranscriptionDocument>();
            if (ProcessData.isValid(solrDocumentList)) {
                for (SolrDocument solrDocument : solrDocumentList) {
                    TranscriptionDocument document = new TranscriptionDocument();
                    document.setSolrId(ProcessData.getAsString(solrDocument.getFieldValue(SearchConstant.SOLR_ID)));
                    document.setUniqueId(ProcessData.getAsString(solrDocument.getFieldValue(SearchConstant.UNIQUE_ID)));
                    document.setDocumentId(ProcessData.getAsString(solrDocument.getFieldValue(SearchConstant.DOCUMENT_ID)));

                    document.setHospitalName(ProcessData.getAsString(solrDocument.getFieldValue(SearchConstant.HOSPITAL_NAME)));
                    document.setHospitalShortName(ProcessData.getAsString(solrDocument.getFieldValue(SearchConstant.HOSPITAL_SHORT_NAME)));
                    document.setHospitalId(ProcessData.getInteger(solrDocument.getFieldValue(SearchConstant.HOSPITAL_ID)));
                    document.setWorktypeId(ProcessData.getInteger(solrDocument.getFieldValue(SearchConstant.WORK_TYPE_ID)));

                    document.setDictatingPhysicianName(ProcessData.getAsString(solrDocument.getFieldValue(SearchConstant.DICTATING_PHY)));
                    document.setWorktypeValue(ProcessData.getAsString(solrDocument.getFieldValue(SearchConstant.WORKTYPE_VALUE)));
                    document.setTurnAroundTime(ProcessData.getInteger(solrDocument.getFieldValue(SearchConstant.TURN_AROUND_TIME)));
                    document.setAudioFilePlayTimeInSec(ProcessData.getInteger(solrDocument.getFieldValue(SearchConstant.AUDIO_FILE_PLAY_TIME)));
                    document.setDateOfDictation(ProcessData.getDate(solrDocument.getFieldValue(SearchConstant.AUDIO_FILE_DICT_TIME)));
                    document.setTatExpiry(ProcessData.getDate(solrDocument.getFieldValue(SearchConstant.TAT_EXPIRY_DATE_TIME)));
                    document.setDocumentName(ProcessData.getAsString(solrDocument.getFieldValue(SearchConstant.DOCUMENT_NAME)));
                    document.setAudioId(ProcessData.getAsString(solrDocument.getFieldValue(SearchConstant.AUDIO_ID)));
                    document.setDocumentPath(ProcessData.getAsString(solrDocument.getFieldValue(SearchConstant.DOCUMENT_PATH)));
                    document.setDocumentCurrentStatusId(ProcessData.getInteger(solrDocument.getFieldValue(SearchConstant.DOCUMENT_STATUS_ID)));
                    document.setAccountNo(ProcessData.getAsString(solrDocument.getFieldValue(SearchConstant.PATIENT_ACCOUNT_NO)));

                    document.setAccessionNumber(ProcessData.getAsString(solrDocument.getFieldValue(SearchConstant.PATIENT_ACCESSION_NO)));
                    document.setMrn(ProcessData.getAsString(solrDocument.getFieldValue(SearchConstant.PATIENT_MRN_NO)));
                    document.setTemplateName(ProcessData.getAsString(solrDocument.getFieldValue((SearchConstant.TEMPLATE_NAME))));
                    documentList.add(document);
                }
            }
        return documentList;
    }

    private void getSortedByPrimary(List<TranscriptionDocument> transcriptionList, WorkListRequest request){
        if(ProcessData.isValid(transcriptionList)){
            if(ProcessData.isValid(request.getSort().getKey()) && request.getSort().getKey().equalsIgnoreCase("hospital_value")){
                Collections.sort(transcriptionList, DocumentComparator.sortByHospitalName());
            }else if(ProcessData.isValid(request.getSort().getKey()) && request.getSort().getKey().equalsIgnoreCase("dictating_physician")){
                Collections.sort(transcriptionList, DocumentComparator.sortByPhyName());
            }else if(ProcessData.isValid(request.getSort().getKey()) && request.getSort().getKey().equalsIgnoreCase("worktype_value")){
                Collections.sort(transcriptionList, DocumentComparator.sortByWorkType());
            }else if(ProcessData.isValid(request.getSort().getKey()) && request.getSort().getKey().equalsIgnoreCase("remaining_tat")){
                Collections.sort(transcriptionList, DocumentComparator.sortByRemTat());
            }
        }
    }

    private void getStatusList(WorkListRequest workListRequest){
        Set<Integer> statuses = new HashSet<Integer>();
        List<PreviousSearchCriteria> previousSearchCriteria = workListRequest.getOther();

        for(int i=0;i< previousSearchCriteria.size();i++){
            PreviousSearchCriteria criteria = previousSearchCriteria.get(i);
            String key = criteria.getCategoryName();
            List<CategoryOptions> categoryOptions = criteria.getCategoryOptions();

            String categoryType = criteria.getCategoryType();
            if(ProcessData.isValidCollection(categoryOptions)) {

                if (SearchConstant.VALUE.equalsIgnoreCase(categoryType)) {

                    if(key.contains(SearchConstant.BUCKET_ID)){

                        for(int j=0;j< categoryOptions.size();j++){
                            String optionName = categoryOptions.get(j).getOptionName();
                            if(Constants.ONE_STRING.equalsIgnoreCase(optionName)){
                                statuses.add(Constants.NEW);
                            }else if(Constants.TWO_STRING.equalsIgnoreCase(optionName)){
                                statuses.add(Constants.EDITING);
                                statuses.add(Constants.DRAFTED);
                            }
                        }
                    }
                }
            }
        }
        List <Integer> statusList = new ArrayList<Integer>();
        statusList.addAll(statuses);
        workListRequest.setStatusList(statusList);
    }

    private Map<Integer, String> convertStatusInfoListIntoMap(List<StatusInfo> statusInfoList) {
        Map<Integer, String> statusMilestoneMap = new HashMap<Integer, String>();
        for(StatusInfo statusInfo: statusInfoList)
            statusMilestoneMap.put(statusInfo.getStatusId(), statusInfo.getMilestoneValue());
        return statusMilestoneMap;
    }
}
