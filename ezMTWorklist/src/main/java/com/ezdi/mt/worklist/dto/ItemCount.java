package com.ezdi.mt.worklist.dto;

/**
 * Created by EZDI\atul.r on 1/6/16.
 */
public class ItemCount {
    private Integer filesCount;
    private String remainingTime;

    public String getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }

    public Integer getFilesCount() {
        return filesCount;
    }

    public void setFilesCount(Integer filesCount) {
        this.filesCount = filesCount;
    }
}
