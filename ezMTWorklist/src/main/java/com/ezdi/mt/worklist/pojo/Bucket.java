package com.ezdi.mt.worklist.pojo;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

/**
 * Created by EZDI\atul.r on 30/6/16.
 */
@Table("ez_mt_bucket_status_map")
public class Bucket{

    @PrimaryKeyColumn(name = "bucket_id", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private Integer bucketId;
    @PrimaryKeyColumn(name = "status_id", ordinal = 2, type = PrimaryKeyType.PARTITIONED)
    private Integer statusId;
    @Column(value = "bucket_value")
    private String bucketValue;
    @Column(value = "sequence")
    private String sequence;
    @Column(value = "status_value")
    private String statusValue;

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getBucketId() {
        return bucketId;
    }

    public void setBucketId(Integer bucketId) {
        this.bucketId = bucketId;
    }

    public String getBucketValue() {
        return bucketValue;
    }

    public void setBucketValue(String bucketValue) {
        this.bucketValue = bucketValue;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

}
