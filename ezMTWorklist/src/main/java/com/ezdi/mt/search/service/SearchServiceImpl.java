package com.ezdi.mt.search.service;

import com.ezdi.component.logger.EzdiLogManager;
import com.ezdi.component.logger.EzdiLogger;
import com.ezdi.mt.search.dao.SearchDao;
import com.ezdi.mt.search.enums.ProductApplication;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by akash.p on 27/5/16.
 */
@Service
public class SearchServiceImpl implements SearchService{

    @Autowired
    private SearchDao searchDao;

    private static EzdiLogger LOG = EzdiLogManager.getLogger(SearchServiceImpl.class);

    @Override
    public QueryResponse getSearchSuggestion(String searchKey, String filterCategory, String flQuery, int start, int max, ProductApplication currentApplication){
        LOG.info("Inside getSearchSuggestion Service");
        QueryResponse searchSuggestion = searchDao.getSearchSuggestion(searchKey, filterCategory, flQuery, start, max,currentApplication);
        LOG.info("Exit from getSearchSuggestion Service");
        return searchSuggestion;
    }

    @Override
    public QueryResponse getSearchResult(String flQuery, String fieldList, String sortField, String order, int start, int max, ProductApplication currentApplication){
        LOG.info("Inside getSearchResult Service");
        QueryResponse searchSuggestion = searchDao.getSearchResult(flQuery, fieldList, sortField, order, start, max,currentApplication);
        LOG.info("Exit from getSearchResult Service");
        return searchSuggestion;
    }
}
