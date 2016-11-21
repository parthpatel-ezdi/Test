package com.ezdi.mt.worklist.pojo;

import com.ezdi.mt.core.util.ProcessData;
import com.ezdi.mt.search.constant.SearchConstant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by EZDI\atul.r on 1/7/16.
 */
public enum BucketStatus {
    NEW_FILES(1,1300,"New Files","New file"),
    EDITING(2,1320,"Draft Files","Editing"),
    DRAFT(2,1340,"Draft Files","MT-Draft"),

    SUBMITTED(3,1360,"Transcribed Files","Submitted");


    private Integer bucketId,  statusId;
    private String bucketValue, bucketStatus;

    BucketStatus(Integer bucketId, Integer statusId, String bucketValue, String bucketStatus) {
        this.bucketId = bucketId;
        this.statusId = statusId;
        this.bucketValue = bucketValue;
        this.bucketStatus = bucketStatus;
    }

    public Integer getBucketId() {
        return bucketId;
    }

    public void setBucketId(Integer bucketId) {
        this.bucketId = bucketId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getBucketValue() {
        return bucketValue;
    }

    public void setBucketValue(String bucketValue) {
        this.bucketValue = bucketValue;
    }

    public String getBucketStatus() {
        return bucketStatus;
    }

    public void setBucketStatus(String bucketStatus) {
        this.bucketStatus = bucketStatus;
    }

    public static String getBucketStatusEnum(Integer bucketId){
        switch (bucketId){
            case 1 : return ProcessData.getAsString(SearchConstant.CIRCULAR_OPEN_BRACKET+BucketStatus.NEW_FILES.statusId+SearchConstant.CIRCULAR_CLOSE_BRACKET);
            case 2 : return ProcessData.getAsString(SearchConstant.CIRCULAR_OPEN_BRACKET+BucketStatus.EDITING.statusId+ SearchConstant.OR_WITH_SPACE+ BucketStatus.DRAFT.statusId+SearchConstant.CIRCULAR_CLOSE_BRACKET);
            case 3 : return ProcessData.getAsString(SearchConstant.CIRCULAR_OPEN_BRACKET+BucketStatus.SUBMITTED.statusId+SearchConstant.CIRCULAR_CLOSE_BRACKET);
        }
        return null;
    }
}
