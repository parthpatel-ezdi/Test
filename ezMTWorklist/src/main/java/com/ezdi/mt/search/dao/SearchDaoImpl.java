package com.ezdi.mt.search.dao;

import com.ezdi.component.logger.EzdiLogManager;
import com.ezdi.component.logger.EzdiLogger;
import com.ezdi.exception.SystemException;
import com.ezdi.exception.enums.DatabaseCode;
import com.ezdi.exception.enums.LoginCode;
import com.ezdi.mt.core.util.SolrCloudUtil;
import com.ezdi.mt.search.enums.ProductApplication;
import com.ezdi.mt.search.util.SearchUtil;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by akash.p on 27/5/16.
 */
@Repository
public class SearchDaoImpl implements SearchDao {

    @Autowired
    private SearchUtil searchUtil;

    @Autowired
    private SolrCloudUtil solrCloudUtil;

    @Autowired
    private CloudSolrClient cloudSolrClient;

    private static EzdiLogger LOG = EzdiLogManager.getLogger(SearchDaoImpl.class);

    @Override
    public QueryResponse getSearchSuggestion(String searchKey, String filterCategory, String flQuery, int start, int max, ProductApplication currentApplication){
        LOG.info("Inside getSearchSuggestion Dao");
        SolrQuery highlightQuery = searchUtil.createHighlightQuery(solrCloudUtil.getSolrSearchHandler(), searchKey, flQuery, start, max
                , filterCategory, currentApplication);
        LOG.info("highlightQuery : "+highlightQuery);

        QueryResponse response = null;
        try {
            //response = solrTemplate.getSolrServer().query(highlightQuery);
            cloudSolrClient.setDefaultCollection(searchUtil.getSolrSearchCollection(currentApplication));
            response = cloudSolrClient.query(highlightQuery);
        }catch (SolrException e) {
            LOG.error(" Exception occurred in getSearchSuggestion Dao method and exception is : ", e);
            throw new SystemException(DatabaseCode.SOLR_DOWN);
        } catch (Exception e) {
            LOG.error(" Exception occurred in getSearchSuggestion Dao method and exception is : ", e);
            throw new SystemException(LoginCode.SOMETHING_WENT_WRONG);
        }

        LOG.info("Exit from getSearchSuggestion Dao");
        return response;
    }

    @Override
    public QueryResponse getSearchResult(String flQuery, String fieldList, String sortField, String order, int start, int max, ProductApplication currentApplication) {
        LOG.info("Inside getSearchResult Dao");
        SolrQuery searchResultQuery = searchUtil.createSearchResultQuery(searchUtil.getSolrSearchCollection(currentApplication), flQuery, fieldList, sortField, order, start, max);
        LOG.info("searchResultQuery : "+searchResultQuery);

        QueryResponse response = null;
        try {
            response = cloudSolrClient.query(searchResultQuery);
        }catch (SolrException e) {
            LOG.error(" Exception occurred in getSearchSuggestion Dao method and exception is : ", e);
            throw new SystemException(DatabaseCode.SOLR_DOWN);
        }catch (Exception e) {
            LOG.error(" Exception occurred in getSearchResult Dao method and exception is : ", e);
            throw new SystemException(LoginCode.SOMETHING_WENT_WRONG);
        }

        LOG.info("Exit from getSearchResult Dao");
        return response;
    }
}
