/**
 * 
 */
package com.ezdi.mt.worklist.business;

import com.ezdi.component.logger.EzdiLogManager;
import com.ezdi.component.logger.EzdiLogger;
import com.ezdi.exception.response.Header;
import com.ezdi.mt.core.util.ProcessData;
import com.ezdi.mt.worklist.constants.Constants;
import com.ezdi.mt.worklist.dto.*;
import com.ezdi.mt.worklist.pojo.TranscriptionDocument;

import java.util.*;

/**
 * @author EZDI\atul.r
 *
 */
public class BeanFiller {

    private static EzdiLogger LOG = EzdiLogManager.getLogger(BeanFiller.class);
    private WorkListResponse workListResponse;
	private WorkListRequest workListRequest;
	private String groupBy;
	List<TranscriptionDocument> paginatedList;
    List<TranscriptionDocument> totalList;
    private Map<Integer, String> statusMilestoneMap;

	public void setResponseObject(WorkListResponse workListResponse, WorkListRequest workListRequest,
			List<TranscriptionDocument> transcriptionDocList, List<TranscriptionDocument> totalList, Map<Integer, String> statusMilestoneMap){
        LOG.info("Inside setResponseObject()");
		this.workListRequest = workListRequest;
		this.workListResponse = workListResponse;
		this.paginatedList = transcriptionDocList;
		this.groupBy = workListRequest.getSort().getKey();
        this.totalList = totalList;
        this.statusMilestoneMap = statusMilestoneMap;
	}
	
	public List<ItemGroups> setItemGroups(){
        LOG.info("Inside setItemGroups()");
		int sortOrder = 0;
		List<ItemGroups> groupsList = groupsList();
        long before = System.currentTimeMillis();
            for (ItemGroups itemGroup : groupsList) {
                for (TranscriptionDocument document : this.paginatedList) {

                    if ((this.groupBy.equals(Constants.GROUP_BY_HOSPITAL)) && (document.getHospitalName().equalsIgnoreCase(itemGroup.getItemGroupAlias())
                            || (!ProcessData.isValid(document.getHospitalName()) && Constants.NOT_SPECIFIED.equals(itemGroup.getItemGroupAlias())))) {
                        sortOrder++;
                        itemGroup.getItems().add(buildItem(document, sortOrder));
                    } else if ((this.groupBy.equals(Constants.GROUP_BY_PHYSICIAN)) && (document.getDictatingPhysicianName().equalsIgnoreCase(itemGroup.getItemGroupAlias())
                            || (!ProcessData.isValid(document.getHospitalName()) && Constants.NOT_SPECIFIED.equals(itemGroup.getItemGroupAlias())))) {
                        sortOrder++;
                        itemGroup.getItems().add(buildItem(document, sortOrder));
                    } else if ((this.groupBy.equals(Constants.GROUP_BY_WORKTYPE)) && (document.getWorktypeValue().equalsIgnoreCase(itemGroup.getItemGroupAlias())
                            || (!ProcessData.isValid(document.getHospitalName()) && Constants.NOT_SPECIFIED.equals(itemGroup.getItemGroupAlias())))) {
                        sortOrder++;
                        itemGroup.getItems().add(buildItem(document, sortOrder));
                    } else if (this.groupBy.equals(Constants.GROUP_BY_TAT)) {

                        if ((ProcessData.isValid(document.getRemainingTat()) && (Constants.LESS_THAN_2HR.equalsIgnoreCase(itemGroup.getItemGroupAlias()))) && document.getRemainingTat() <= 120) {
                            sortOrder++;
                            itemGroup.getItems().add(buildItem(document, sortOrder));
                        } else if ((ProcessData.isValid(document.getRemainingTat()) && (Constants.BETWEEN_2HRTO4HR.equalsIgnoreCase(itemGroup.getItemGroupAlias()))) && (document.getRemainingTat() > 120 && document.getRemainingTat() <= 240)) {
                            sortOrder++;
                            itemGroup.getItems().add(buildItem(document, sortOrder));
                        } else if ((ProcessData.isValid(document.getRemainingTat()) && (Constants.GREATER_THAN_4HR.equalsIgnoreCase(itemGroup.getItemGroupAlias()))) && (document.getRemainingTat() > 240)) {
                            sortOrder++;
                            itemGroup.getItems().add(buildItem(document, sortOrder));
                        }
                    }
                }
            }
        LOG.info("Exit setItemGroups() time taken : "+ (System.currentTimeMillis() -before));
		return groupsList;
	}
	
	private Items buildItem(TranscriptionDocument document, int sortOrder){
		Items item = new Items();
            item.setItemSortOrder(sortOrder);
            item.setItemUniqueKey(document.getUniqueId());
            item.setItemPosition1(document.getDocumentId());
            item.setItemPosition2(document.getHospitalShortName());
            item.setItemPosition3(document.getDictatingPhysicianName());
            item.setItemPosition4(document.getWorktypeValue());
            item.setItemPosition5(getTimeInHrs(document.getRemainingTat()));
            item.setItemPosition6(getTimeInHrs(document.getTurnAroundTime()));
            item.setItemPosition7(getPlayTime(document));
            item.setItemPosition8(document.getHospitalName());
            item.setItemPosition9(document.getHospitalId());
            item.setItemPosition10(document.getWorktypeId());
            item.setItemPosition11(document.getDocumentName());
            item.setItemPosition12(document.getAudioId());
            item.setItemPosition13(document.getDocumentPath());
            item.setItemPosition14(document.getDocumentCurrentStatusId());
            item.setItemPosition15(getSolrId(document));
            item.setItemPosition16(document.getAccountNo());
            item.setItemPosition17(document.getAccessionNumber());
            item.setItemPosition18(document.getMrn());
            item.setItemPosition19(document.getTemplateName());
            item.setItemPosition20(statusMilestoneMap.get(document.getDocumentCurrentStatusId()));
            item.setDisabled(false);
		return item;
	}

    private String getSolrId(TranscriptionDocument document){
        String solrId = null;
        if(ProcessData.isValid(document.getSolrId())){
            solrId = document.getSolrId();
        }else{
            // In case of solr down, we get the work list data from cassandra,
            // so need to create solr id manually
            solrId = ProcessData.getAsString(document.getUserId())+"_"+document.getDocumentId();
        }
        return solrId;
    }

    private String getPlayTime(TranscriptionDocument document){
        StringBuilder time = new StringBuilder();
            if (ProcessData.isValid(document.getAudioFilePlayTimeInSec()) && document.getAudioFilePlayTimeInSec() > 0) {

                String mins = String.valueOf(document.getAudioFilePlayTimeInSec() / 60);
                mins = mins.length() == 1 ? "0" + mins : mins;

                String secs = String.valueOf(document.getAudioFilePlayTimeInSec() % 60);
                secs = secs.length() == 1 ? "0" + secs : secs;

                time.append(mins).append(":").append(secs);
            } else {
                time.append(0).append(0).append(":").append(0).append(0);
            }
        return time.toString();
    }

    private String getTimeInHrs(Integer timeInMins){
        StringBuffer time = new StringBuffer();
            if(ProcessData.isValid(timeInMins) && timeInMins!=0){
                if(timeInMins<0){
                    time.append("-");
                    timeInMins = timeInMins*-1;
                }
                String hours = String.valueOf(timeInMins / 60);
                String mins = String.valueOf(timeInMins % 60);
                mins = mins.length() == 1?"0"+mins:mins;
                time.append(hours).append(":").append(mins);
            }else {
                time.append(0).append(":").append(0).append(0);
            }
        return time.toString();
    }

	public List<ItemGroups> groupsList(){
        LOG.info("Inside  groupsList()");
		List<ItemGroups> groupsList = new ArrayList<ItemGroups>();
		SortedSet<ItemGroups> groupSet = new TreeSet<ItemGroups>();
        long before = System.currentTimeMillis();
            for (TranscriptionDocument document : this.paginatedList) {

                if (Constants.GROUP_BY_HOSPITAL.equalsIgnoreCase(this.groupBy)) {
                    ItemGroups itemGroup = new ItemGroups();
                    List<Items> itemsList = new ArrayList<Items>();
                    itemGroup.setItems(itemsList);
                    itemGroup.setItemGroupAlias(ProcessData.isValid(document.getHospitalName()) ? document.getHospitalName() : "Not specified");
                    groupSet.add(itemGroup);
                } else if (Constants.GROUP_BY_PHYSICIAN.equalsIgnoreCase(this.groupBy)) {
                    ItemGroups itemGroup = new ItemGroups();
                    List<Items> itemsList = new ArrayList<Items>();
                    itemGroup.setItems(itemsList);
                    itemGroup.setItemGroupAlias(ProcessData.isValid(document.getDictatingPhysicianName()) ? document.getDictatingPhysicianName() : "Not specified");
                    groupSet.add(itemGroup);

                } else if (Constants.GROUP_BY_WORKTYPE.equalsIgnoreCase(this.groupBy)) {
                    ItemGroups itemGroup = new ItemGroups();
                    List<Items> itemsList = new ArrayList<Items>();
                    itemGroup.setItems(itemsList);
                    itemGroup.setItemGroupAlias(ProcessData.isValid(document.getWorktypeValue()) ? document.getWorktypeValue() : "Not specified");
                    groupSet.add(itemGroup);
                } else if (Constants.GROUP_BY_TAT.equalsIgnoreCase(this.groupBy)) {
                    if ((ProcessData.isValid(document.getRemainingTat()) && document.getRemainingTat() <= 120)) {
                        ItemGroups itemGroup1 = new ItemGroups();
                        List<Items> itemsList1 = new ArrayList<Items>();
                        itemGroup1.setItems(itemsList1);
                        itemGroup1.setItemGroupAlias(Constants.LESS_THAN_2HR);
                        groupSet.add(itemGroup1);
                    }
                    if ((ProcessData.isValid(document.getRemainingTat()) && document.getRemainingTat() > 120 && document.getRemainingTat() <= 240)) {
                        ItemGroups itemGroup2 = new ItemGroups();
                        List<Items> itemsList2 = new ArrayList<Items>();
                        itemGroup2.setItems(itemsList2);
                        itemGroup2.setItemGroupAlias(Constants.BETWEEN_2HRTO4HR);
                        groupSet.add(itemGroup2);
                    }
                    if (document.getRemainingTat() > 240) {
                        ItemGroups itemGroup3 = new ItemGroups();
                        List<Items> itemsList3 = new ArrayList<Items>();
                        itemGroup3.setItems(itemsList3);
                        itemGroup3.setItemGroupAlias(Constants.GREATER_THAN_4HR);
                        groupSet.add(itemGroup3);
                    }
                }
            }
		if(ProcessData.isValidCollection(groupSet)){
			groupsList.addAll(groupSet);
		}
        LOG.info("Exit groupsList() time taken : "+(System.currentTimeMillis()-before));
		return groupsList;
	}

    public void setWorklistResponse(){

            LOG.info("Inside setWorklistResponse()");
            Pagination pagination = new Pagination();
            pagination.setStart(this.workListRequest.getPagination().getStart());
            pagination.setCount(Constants.MAX_COUNT);
            pagination.setTotal(totalList.size());
            Header header = new Header("success", 200, true);

            ItemCount itemCount = null;
            int totalPlayTime = 0;
            if (ProcessData.isValidCollection(totalList)) {
                itemCount = new ItemCount();
                itemCount.setFilesCount(this.totalList.size());
                for (TranscriptionDocument document : totalList) {
                    totalPlayTime = totalPlayTime + (document.getAudioFilePlayTimeInSec());
                }
                if(totalPlayTime>0){
                    itemCount.setRemainingTime((totalPlayTime / 60 > 0 ? totalPlayTime / 60 + " min ":"") + (totalPlayTime % 60 > 0 ? totalPlayTime % 60 + " sec" : ""));
                }else {
                    itemCount.setRemainingTime(0+" min");
                }

            }

            this.workListResponse.setItemCount(itemCount);
            this.workListResponse.setHeader(header);
            this.workListResponse.setPagination(pagination);
            this.workListResponse.setSort(this.workListRequest.getSort());
            LOG.info("Exit setWorklistResponse()");
    }

}
