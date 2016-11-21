/**
 * 
 */
package com.ezdi.mt.worklist.service;

import com.ezdi.component.logger.EzdiLogManager;
import com.ezdi.component.logger.EzdiLogger;
import com.ezdi.mt.worklist.dao.WorkListDao;
import com.ezdi.mt.worklist.dto.BucketListItem;
import com.ezdi.mt.worklist.dto.WorkListRequest;
import com.ezdi.mt.worklist.pojo.Bucket;
import com.ezdi.mt.worklist.pojo.StatusInfo;
import com.ezdi.mt.worklist.pojo.TranscriptionDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author EZDI\atul.r
 *
 */
@Service
public class WorkListServiceImpl implements WorkListService {
    private static EzdiLogger LOG = EzdiLogManager.getLogger(WorkListServiceImpl.class);
    @Autowired
    WorkListDao worklistServiceDao;

@Override
public List<TranscriptionDocument> getTranscriptionList(WorkListRequest request){
    LOG.info("Inside service getTranscriptionList");
	List<TranscriptionDocument> transcriptionList = worklistServiceDao.getTranscriptionList(request);
    LOG.info("Exit from service getTranscriptionList");
	return transcriptionList;
	}

    @Override
    public List<Bucket> getBucketList() {
        return worklistServiceDao.getBucketList();
    }

    @Override
    public List<StatusInfo> getStatusInfo() {
        LOG.info("Inside getStatusInfo service");
        List<StatusInfo> statusInfoList = worklistServiceDao.getStatusInfo();
        LOG.info("Exit from getStatusInfo service");
        return statusInfoList;
    }
}
