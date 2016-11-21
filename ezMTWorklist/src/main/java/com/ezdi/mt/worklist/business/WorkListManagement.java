package com.ezdi.mt.worklist.business;

import com.ezdi.mt.worklist.dto.InitList;
import com.ezdi.mt.worklist.dto.WorkListRequest;
import com.ezdi.mt.worklist.dto.WorkListResponse;

public interface WorkListManagement {
	public WorkListResponse getTranscriptionList(WorkListRequest request);
	public InitList getInitList() throws Exception;
}
