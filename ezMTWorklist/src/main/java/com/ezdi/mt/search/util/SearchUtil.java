package com.ezdi.mt.search.util;

import com.ezdi.component.logger.EzdiLogManager;
import com.ezdi.component.logger.EzdiLogger;
import com.ezdi.exception.SystemException;
import com.ezdi.exception.enums.LoginCode;
import com.ezdi.mt.core.util.SolrCloudUtil;
import com.ezdi.mt.search.constant.SearchConstant;
import com.ezdi.mt.search.dao.BucketStatusDao;
import com.ezdi.mt.search.dto.SearchSuggestionResponse;
import com.ezdi.mt.core.util.ProcessData;
import com.ezdi.mt.search.enums.ProductApplication;
import com.ezdi.mt.worklist.constants.Constants;
import com.ezdi.mt.worklist.pojo.BucketStatus;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.*;

/**
 * Created by akash.p on 27/5/16.
 */

@Configuration
public class SearchUtil {

    private static EzdiLogger LOG = EzdiLogManager.getLogger(SearchUtil.class);

    private Map<String, String> searchFieldMap = new HashMap<String, String>();
    private static Map<String, String> controllerStatusMap = new HashMap<String, String>();

    private List<String> searchFieldKeys;

    @Autowired
    private SolrCloudUtil solrCloudUtil;

    @Autowired
    private BucketStatusDao bucketStatusDao;

    public void setSearchFieldMap(ProductApplication currentApplication){
        try {
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            Properties properties = PropertiesLoaderUtils.loadProperties(resourceLoader
                    .getResource(SearchConstant.SEARCH_FIELD_PROP_FILE + Constants.UNDERSCORE+currentApplication.getApplicationId()+SearchConstant.PROPERTIES_EXT));

            searchFieldKeys = (List<String>) Collections.list(properties.propertyNames());

            searchFieldMap.clear();

            for(String property:searchFieldKeys){
                searchFieldMap.put(property,properties.getProperty(property));
            }
        } catch (Exception e) {
            LOG.error(" Exception occurred in setSearchFieldMap method and exception is : ", e);
            throw new SystemException(LoginCode.SOMETHING_WENT_WRONG);
        }
    }

    public Map<String, String> getSearchFieldMap() {
        return searchFieldMap;
    }

    public String[] getSearchKeys() {
        return searchFieldKeys.toArray(new String[searchFieldKeys.size()]);
    }

    public SolrQuery createHighlightQuery(String searchHandle, String query, String flQuery, Integer start,
                                          Integer rows, String hlField, ProductApplication currentApplication){
        setSearchFieldMap(currentApplication);

        SolrQuery solrQuery = new SolrQuery();

        solrQuery.setRequestHandler(searchHandle);
        solrQuery.setHighlight(true);
        solrQuery.setHighlightSimplePre(SearchConstant.LESS_THAN_OPERATOR);
        solrQuery.setHighlightSimplePost(SearchConstant.GREATER_THAN_OPERATOR);
        solrQuery.setFields(SearchConstant.SEARCH_SELECT_DEFAULT_FIELD);

        solrQuery.setQuery(SearchConstant.DOUBLE_QUOTES+query+SearchConstant.DOUBLE_QUOTES);

        if(ProcessData.isValid(flQuery)) {
            solrQuery.setFilterQueries(flQuery);
        }

        solrQuery.setStart(start);
        solrQuery.setRows(rows);

        if(ProcessData.isValid(hlField)) {
            solrQuery.set(SearchConstant.HIGHLIGHT_FIELD,hlField);
        }else {
            solrQuery.set(SearchConstant.HIGHLIGHT_FIELD,getSearchKeys());
        }

        return solrQuery;
    }

    public Set<SearchSuggestionResponse> getSearchSuggestionFields(String key, ProductApplication currentApplication){
        setSearchFieldMap(currentApplication);

        Set<SearchSuggestionResponse> searchSuggestionResponse = new LinkedHashSet<SearchSuggestionResponse>();

        for(Map.Entry<String,String> field :searchFieldMap.entrySet()){
            String value = field.getValue().toUpperCase();
            String upperCaseKey = key.toUpperCase();
            if (value.contains(upperCaseKey)) {
                SearchSuggestionResponse response = processSearchResponseField(field.getKey(), upperCaseKey, value);
                searchSuggestionResponse.add(response);
            }
        }

        return searchSuggestionResponse;
    }

    private SearchSuggestionResponse processSearchResponseField(String categoryKey,String searchKey,
                                                           String searchValue) {
        SearchSuggestionResponse response = new SearchSuggestionResponse();
        response.setCategoryId(categoryKey);
        response.setSuggestionId(searchValue);

        //response.setSuggestionString(searchValue.replaceAll("(?i)"+searchKey, SearchConstant.HTML_B_OPEN_TAG + searchKey + SearchConstant.HTML_B_CLOSE_TAG));
        //response.setSuggestionString(searchValue);

        StringBuffer returnSearchValue = getHighlightingString(searchKey, searchValue);
        response.setSuggestionString(returnSearchValue.toString());

        response.setSuggestionCategory(true);
        response.setSuggestionCategoryUrl(SearchConstant.SEARCH_SUGGESTION_URL);
        response.setSuggestionCategoryUrlMethod(SearchConstant.POST_METHOD);

        if (categoryKey.indexOf(SearchConstant.DATE) != -1 && !categoryKey.equalsIgnoreCase(SearchConstant.TAT_EXPIRY_DATE_TIME)) {
            response.setSuggestionCategoryType(SearchConstant.DATE);
        }else if (categoryKey.indexOf(SearchConstant.TIME) != -1) {
            response.setSuggestionCategoryType(SearchConstant.NUMBER);
        }else  {
            response.setSuggestionCategoryType(SearchConstant.VALUE);
        }

        response.setSuggestionLabel(searchValue);
        response.setRadioOptions(null);
        response.setValueSuggestion(null);
        return response;
    }


    public SearchSuggestionResponse processSearchResponseSolr(String categoryKey,String searchKey,
                                                               String searchValue) {
        searchValue=searchValue.replaceAll(SearchConstant.LESS_THAN_OPERATOR,SearchConstant.EMPTY_STRING).replaceAll(SearchConstant.GREATER_THAN_OPERATOR, SearchConstant.EMPTY_STRING);

        String categoryValue = searchFieldMap.get(categoryKey);
        SearchSuggestionResponse response = new SearchSuggestionResponse();
        response.setCategoryId(categoryKey);
        response.setSuggestionId(searchValue);
        //response.setSuggestionString(searchValue.replaceAll("(?i)"+searchKey,SearchConstant.HTML_B_OPEN_TAG+searchKey+SearchConstant.HTML_B_CLOSE_TAG) + SearchConstant.IN_WITH_SPACE
        //       + categoryValue.toUpperCase());

        //response.setSuggestionString(searchValue + SearchConstant.IN_WITH_SPACE
         //       + categoryValue.toUpperCase());

        StringBuffer returnSearchValue = getHighlightingString(searchKey, searchValue);
        response.setSuggestionString(returnSearchValue.toString() + SearchConstant.IN_WITH_SPACE + categoryValue.toUpperCase());

        response.setSuggestionCategory(false);
        response.setSuggestionCategoryUrl(SearchConstant.EMPTY_STRING);
        response.setSuggestionCategoryUrlMethod(SearchConstant.EMPTY_STRING);

        if (categoryKey.indexOf(SearchConstant.DATE) != -1) {
            response.setSuggestionCategoryType(SearchConstant.DATE);
        }else {
            response.setSuggestionCategoryType(SearchConstant.VALUE);
        }

        response.setSuggestionLabel(searchValue);
        response.setRadioOptions(null);
        response.setValueSuggestion(null);
        return response;
    }

    private StringBuffer getHighlightingString(String searchKey, String searchValue) {
        StringBuffer returnSearchValue = new StringBuffer(searchValue);
        String searchValueLowerCase = searchValue.toLowerCase();
        String searchKeyLowerCase = searchKey.toLowerCase();
        int bOpenLength = SearchConstant.HTML_B_OPEN_TAG.length();
        int bCloseLength = SearchConstant.HTML_B_CLOSE_TAG.length();
        int searchKeyBoldTagLength = searchKey.length() + bOpenLength;
        int extraCount=0;

        int skipCharacters = -1;
        for (int i = -1; (i = searchValueLowerCase.indexOf(searchKeyLowerCase, i+ skipCharacters)) != -1; ) {
            //returnSearchValue.insert(i+extraCount,SearchConstant.HTML_B_OPEN_TAG);
            //returnSearchValue.insert(i+extraCount + searchKeyBoldTagLength,SearchConstant.HTML_B_CLOSE_TAG);
            extraCount+=bOpenLength+bCloseLength;
            skipCharacters = searchKey.length();
        }
        return returnSearchValue;
    }

    public SolrQuery createSearchResultQuery(String solrSearchHandler, String flQuery, String fieldList, String sortField, String order, int start, int max) {
        SolrQuery solrQuery = new SolrQuery();

        solrQuery.setRequestHandler(solrSearchHandler);
        if(ProcessData.isValid(fieldList)) {
            solrQuery.setFields(fieldList);
        }
        if(ProcessData.isValid(sortField)){
            if(sortField.equalsIgnoreCase("hospital_value")){
                solrQuery.setSort(SearchConstant.HOSPITAL_NAME, SolrQuery.ORDER.asc);
            }else if(sortField.equalsIgnoreCase("dictating_physician")){
                solrQuery.setSort(SearchConstant.DICTATING_PHY, SolrQuery.ORDER.asc);
            }else if(sortField.equalsIgnoreCase("worktype_value")){
                solrQuery.setSort(SearchConstant.WORKTYPE_VALUE, SolrQuery.ORDER.asc);
            }else if(sortField.equalsIgnoreCase("remaining_tat")){
                solrQuery.setSort(SearchConstant.TAT_EXPIRY_DATE_TIME, SolrQuery.ORDER.asc);
            }
        }
        solrQuery.setQuery(SearchConstant.DEFAULT_SEARCH_QUERY);

        if(ProcessData.isValid(flQuery)) {
            solrQuery.setFilterQueries(flQuery);
        }
        solrQuery.setStart(start);
        solrQuery.setRows(max);

        return solrQuery;
    }

    public String getSolrSearchCollection(ProductApplication currentApplication) {
        switch (currentApplication){
            case MTSCRIBES:return solrCloudUtil.getSolrSearchCollection();
            case CONTROLLER:return solrCloudUtil.getSolrControllerCollection();
        }
        return null;
    }

    public String getBucketStatusEnum(Integer statusValue, String actualKey, ProductApplication currentApplication) {

        switch (currentApplication){
            case MTSCRIBES:return BucketStatus.getBucketStatusEnum(statusValue);
            case CONTROLLER:return getControllerBucketStatusIds(actualKey, statusValue);
        }

        return null;
    }

    private String getControllerBucketStatusIds(String actualKey, Integer statusValue) {
        String key = actualKey + SearchConstant.COLON + statusValue;
        String value = controllerStatusMap.get(key);

        if(ProcessData.isValid(value)){
            return value;
        }

        StringBuffer allStatusIds = new StringBuffer();
        allStatusIds.append(SearchConstant.CIRCULAR_OPEN_BRACKET);
        List<Integer> allControllerBucketStatusId = Constants.STATUS_ID.equalsIgnoreCase(actualKey)? Arrays.asList(statusValue) : bucketStatusDao.getAllControllerBucketStatusId(actualKey, statusValue);
        for(Integer status : allControllerBucketStatusId){
             allStatusIds
            .append(status)
            .append(SearchConstant.OR_WITH_SPACE)
            ;
        }

        String statuses = allStatusIds.substring(0, allStatusIds.lastIndexOf(SearchConstant.OR_WITH_SPACE))+SearchConstant.CIRCULAR_CLOSE_BRACKET;
        controllerStatusMap.put(key,statuses);
        return statuses;
    }
}
