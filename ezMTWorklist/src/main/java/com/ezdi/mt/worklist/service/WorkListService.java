/**
 * 
 */
package com.ezdi.mt.worklist.service;

import java.util.List;

import com.ezdi.mt.worklist.dto.BucketListItem;
import com.ezdi.mt.worklist.dto.WorkListRequest;
import com.ezdi.mt.worklist.pojo.Bucket;
import com.ezdi.mt.worklist.pojo.StatusInfo;
import com.ezdi.mt.worklist.pojo.TranscriptionDocument;

/**
 * @author EZDI\atul.r
 *
 */
public interface WorkListService {
	public List<TranscriptionDocument> getTranscriptionList(WorkListRequest request);

    List<Bucket> getBucketList();

    public List<StatusInfo> getStatusInfo();
}
