package com.ezdi.mt.search.business;

import com.ezdi.exception.SystemException;
import com.ezdi.mt.search.dto.SearchSuggestionRequest;
import com.ezdi.mt.search.dto.SearchSuggestionResponse;
import com.ezdi.mt.worklist.dto.WorkListRequest;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.List;

/**
 * Created by akash.p on 27/5/16.
 */
public interface SearchManagement {

    public List<SearchSuggestionResponse> getSearchSuggestion(SearchSuggestionRequest searchRequest);

    public String createSolrSearchFilterQuery(SearchSuggestionRequest searchRequest);
    
    public String createQueryForCassandra(SearchSuggestionRequest searchRequest);

    public QueryResponse getSolrWorklistQueryResult(WorkListRequest workListRequest);
}
