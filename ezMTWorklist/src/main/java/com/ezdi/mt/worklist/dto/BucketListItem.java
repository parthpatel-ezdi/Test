package com.ezdi.mt.worklist.dto;

/**
 * Created by EZDI\atul.r on 30/6/16.
 */
public class BucketListItem implements Comparable<BucketListItem>{

    private String bucketId;
    private String bucketName;
    private String bucketCategory = "bucket_id";

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketCategory() {
        return bucketCategory;
    }

    public void setBucketCategory(String bucketCategory) {
        this.bucketCategory = bucketCategory;
    }


    @Override
    public int compareTo(BucketListItem that) {
        return this.bucketId.compareTo(that.bucketId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BucketListItem that = (BucketListItem) o;

        if (bucketId != null ? !bucketId.equals(that.bucketId) : that.bucketId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
