/**
 * 
 */
package com.ezdi.mt.worklist.dao;

import com.datastax.driver.core.exceptions.InvalidQueryException;
import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.ezdi.component.logger.EzdiLogManager;
import com.ezdi.component.logger.EzdiLogger;
import com.ezdi.exception.SystemException;
import com.ezdi.exception.enums.DatabaseCode;
import com.ezdi.exception.enums.LoginCode;
import com.ezdi.mt.core.util.ProcessData;
import com.ezdi.mt.worklist.constants.Constants;
import com.ezdi.mt.worklist.dto.BucketListItem;
import com.ezdi.mt.worklist.dto.WorkListRequest;
import com.ezdi.mt.worklist.pojo.Bucket;
import com.ezdi.mt.worklist.pojo.StatusInfo;
import com.ezdi.mt.worklist.pojo.TranscriptionDocument;
import com.ezdi.mt.worklist.util.DocumentComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author EZDI\atul.r
 *
 */
@Repository
public class WorkListDaoImpl implements WorkListDao {

    private static EzdiLogger LOG = EzdiLogManager.getLogger(WorkListDaoImpl.class);
	@Autowired
	@Qualifier("CassandraDocumentTemplate")
	public CassandraOperations cassandraDocumentTemplate;

    @Autowired
    @Qualifier("CassandraMasterTemplate")
    public CassandraOperations cassandraMasterTemplate;
	
	@Override
	public List<TranscriptionDocument> getTranscriptionList(WorkListRequest request){
        LOG.info("Inside getTranscriptionList Dao");
        long before = System.currentTimeMillis();
        List<TranscriptionDocument> transcriptionList = new ArrayList<TranscriptionDocument>();

        try {
            for(Integer status : request.getStatusList()){
                String workListQuery = String.format(Constants.GET_WORK_LIST_QUERY,request.getUserId(),status);
                LOG.info("Get work list query : "+workListQuery);
                transcriptionList.addAll(cassandraDocumentTemplate.select(workListQuery, TranscriptionDocument.class));
            }
            System.out.println((System.currentTimeMillis() - before));
            LOG.info("Time Required to fetch search response: " + (System.currentTimeMillis() - before));
            getSortedByPrimary(transcriptionList, request);
        }catch(InvalidQueryException e) {
            LOG.error("Exception occurred while getting document list is : "+e);
            throw new SystemException(DatabaseCode.INVALID_CASSANDRA_QUERY);
        } catch (NoHostAvailableException e){
            LOG.error("Exception occurred while getting document list is : "+e);
            throw new SystemException(DatabaseCode.CASSANDRA_DOWN);
        } catch (Exception e) {
            LOG.error("Exception occurred while getting document list is : " + e);
            throw new SystemException(LoginCode.SOMETHING_WENT_WRONG);
        }
        LOG.info("Exit from Dao getTranscriptionList");
        return transcriptionList;
	}



    private void getSortedByPrimary(List<TranscriptionDocument> transcriptionList, WorkListRequest request) throws Exception{
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

    @Override
    public List<Bucket> getBucketList() {
        LOG.info(" Inside the getBucketList");
        String query = Constants.GET_BUCKET_LIST;
        List<Bucket> bucketList = null;
        try {
            bucketList = cassandraMasterTemplate.select(query, Bucket.class);
            if(ProcessData.isValidCollection(bucketList)){
                LOG.info(" Exit from getBucketList");
                return bucketList;
            }
        } catch(InvalidQueryException e) {
            LOG.error("Exception occurred while getting bucket list is : "+e);
            throw new SystemException(DatabaseCode.INVALID_CASSANDRA_QUERY);
        } catch (NoHostAvailableException e){
            LOG.error("Exception occurred while getting bucket list is : "+e);
            throw new SystemException(DatabaseCode.CASSANDRA_DOWN);
        } catch (Exception e) {
            LOG.error("Exception occurred while getting bucket list is : " + e);
            throw new SystemException(LoginCode.SOMETHING_WENT_WRONG);
        }
        LOG.info(" Exit from getBucketList");
        return null;
    }

    @Override
    public List<StatusInfo> getStatusInfo() {
        LOG.info(" Inside getStatusInfo dao");
        String query = Constants.CASSANDRA_QUERY_FOR_FETCHING_STATUS_INFO;
        LOG.info("Cassandra query: " + query);
        List<StatusInfo> statusInfoList;
        try {
            statusInfoList = cassandraMasterTemplate.select(query, StatusInfo.class);
        } catch(InvalidQueryException e) {
            LOG.error("Exception occurred while getting the status info list : "+e);
            throw new SystemException(DatabaseCode.INVALID_CASSANDRA_QUERY);
        } catch (NoHostAvailableException e){
            LOG.error("Exception occurred while getting the status info list : "+e);
            throw new SystemException(DatabaseCode.CASSANDRA_DOWN);
        } catch (Exception e) {
            LOG.error("Exception occurred while getting the status info list : " + e);
            throw new SystemException(LoginCode.SOMETHING_WENT_WRONG);
        }
        LOG.info(" Exit from getStatusInfo dao");
        return statusInfoList;
    }
}
