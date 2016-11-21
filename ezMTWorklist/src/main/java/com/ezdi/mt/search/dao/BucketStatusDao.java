package com.ezdi.mt.search.dao;

import java.util.List;

/**
 * Created by akash.p on 30/8/16.
 */
public interface BucketStatusDao {

    public List<Integer> getAllControllerBucketStatusId(String column, Integer value);
}
