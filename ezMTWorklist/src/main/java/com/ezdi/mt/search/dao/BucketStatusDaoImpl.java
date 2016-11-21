package com.ezdi.mt.search.dao;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.ezdi.mt.search.constant.SearchConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by akash.p on 30/8/16.
 */
@Repository
public class BucketStatusDaoImpl implements BucketStatusDao {

    @Autowired
    @Qualifier("CassandraMasterTemplate")
    public CassandraOperations cassandraMasterTemplate;

    @Override
    public List<Integer> getAllControllerBucketStatusId(String column, Integer value) {
        Select select =  QueryBuilder.select(SearchConstant.STATUS_ID)
                        .from(SearchConstant.EZ_MASTER_KEYSPACE,SearchConstant.CONTROLLER_BUCKET_STATUS_MAP_TABLE)
                        .where(QueryBuilder.eq(column, value))
                        .limit(Integer.MAX_VALUE)
                        .allowFiltering();

        return cassandraMasterTemplate.queryForList(select, Integer.class);
    }
}
