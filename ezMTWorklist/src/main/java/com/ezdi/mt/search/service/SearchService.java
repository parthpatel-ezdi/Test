package com.ezdi.mt.search.service;

import com.ezdi.exception.SystemException;
import com.ezdi.mt.search.enums.ProductApplication;
import org.apache.solr.client.solrj.response.QueryResponse;

/**
 * Created by akash.p on 27/5/16.
 */
public interface SearchService {

    public QueryResponse getSearchSuggestion(String searchKey, String filterCategory, String flQuery, int start, int max, ProductApplication currentApplication);

    public QueryResponse getSearchResult(String flQuery, String fieldList, String sortField, String order, int start, int max, ProductApplication currentApplication);
}
