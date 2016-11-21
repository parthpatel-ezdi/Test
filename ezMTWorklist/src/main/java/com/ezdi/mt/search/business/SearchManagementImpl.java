package com.ezdi.mt.search.business;

import com.ezdi.component.logger.EzdiLogManager;
import com.ezdi.component.logger.EzdiLogger;
import com.ezdi.mt.core.util.DateContent;
import com.ezdi.mt.core.util.ProcessData;
import com.ezdi.mt.search.constant.SearchConstant;
import com.ezdi.mt.search.dto.PreviousSearchCriteria;
import com.ezdi.mt.search.dto.SearchSuggestionRequest;
import com.ezdi.mt.search.dto.SearchSuggestionResponse;
import com.ezdi.mt.search.enums.ProductApplication;
import com.ezdi.mt.search.service.SearchService;
import com.ezdi.mt.search.util.SearchUtil;
import com.ezdi.mt.worklist.dto.CategoryOptions;
import com.ezdi.mt.worklist.dto.WorkListRequest;
import com.ezdi.mt.worklist.pojo.BucketStatus;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by akash.p on 27/5/16.
 */
@Component
public class SearchManagementImpl implements SearchManagement {

    @Autowired
    private SearchService searchService;

    @Autowired
    private SearchUtil searchUtil;

    @Autowired
    private CloudSolrClient cloudSolrClient;

    private static EzdiLogger LOG = EzdiLogManager.getLogger(SearchManagementImpl.class);

    @Override
    public List<SearchSuggestionResponse> getSearchSuggestion(SearchSuggestionRequest searchRequest) {
        LOG.info("Inside getSearchSuggestion business");

        ProductApplication currentApplication = ProductApplication.getEnumById(searchRequest.getApplicationId());

        String searchKey = searchRequest.getCurrentSearchCriteria().getKey();
        //Handled slash
       searchKey = searchKey.replaceAll("\\\\","\\\\\\\\");

        String filterCategory = searchRequest.getCurrentSearchCriteria().getFilterCategory();

        String solrSearchFilterQuery = createSolrSearchFilterQuery(searchRequest);

        String flQuery = SearchConstant.EMPTY_STRING;

        if(currentApplication.equals(ProductApplication.MTSCRIBES)) {
            //TODO NEEDS TO HANDLE USER ID LATER WHEN SESSION COMES INTO PICTURE
            flQuery = SearchConstant.USER_ID_TEMP_FL_QUERY;
        }

        if(ProcessData.isValid(solrSearchFilterQuery)) {
            flQuery += ( ProcessData.isValid(flQuery)?SearchConstant.AND_WITH_SPACE:SearchConstant.EMPTY_STRING ) + solrSearchFilterQuery;
        }

        LOG.info("searchKey : "+searchKey+" filterCategory: "+filterCategory+" flQuery"+flQuery);

        Set<SearchSuggestionResponse> searchSuggestionResponse;

        if(ProcessData.isValid(filterCategory)){
            searchSuggestionResponse = new LinkedHashSet<SearchSuggestionResponse>();
        }else {
            searchSuggestionResponse = searchUtil.getSearchSuggestionFields(searchKey,currentApplication);
        }

        long before = System.currentTimeMillis();

        int start = 0;
        int max = SearchConstant.MAX_SEARCH_SUGGESTION * 2;
        int loopCount = 0;

        while (loopCount!=2) {

            loopCount++;

            QueryResponse queryResponse = searchService.getSearchSuggestion(searchKey, filterCategory, flQuery, start, max,currentApplication);
            int numFound = (int)queryResponse.getResults().getNumFound();
            LOG.info("Time Required to fetch search response: " + (System.currentTimeMillis() - before));

            before = System.currentTimeMillis();

            Map<String, Map<String, List<String>>> highlightingResponse = queryResponse.getHighlighting();

            outerLoop:
            for (Map<String, List<String>> result : highlightingResponse.values()) {
                for (Map.Entry<String, List<String>> columns : result.entrySet()) {
                    String key = columns.getKey();
                    for (String row : columns.getValue()) {
                        if (searchSuggestionResponse.size() >= SearchConstant.MAX_SEARCH_SUGGESTION) {
                            break outerLoop;
                        }
                        searchSuggestionResponse.add(searchUtil.processSearchResponseSolr(key, searchKey, row));
                    }
                }
            }

            //This is for Search Suggestion Performance Issue.
            if(searchSuggestionResponse.size()<20 && max<numFound){
                start = max;
                max = numFound;
            }else {
                loopCount=2;
            }
        }

        LOG.info("Time Required to create search response list: "+(System.currentTimeMillis()-before) + " Exit from getSearchSuggestion business");

        return new ArrayList<SearchSuggestionResponse>(searchSuggestionResponse);
    }

    @Override
    public String createSolrSearchFilterQuery(SearchSuggestionRequest searchRequest) {
        StringBuffer flQuery = new StringBuffer();
        Boolean andFlag=true;

        List<PreviousSearchCriteria> previousSearchCriteria = searchRequest.getPreviousSearchCriteria();
        //flQuery.append("user_id:10");
        for(int i=0;i< previousSearchCriteria.size();i++){

            PreviousSearchCriteria criteria = previousSearchCriteria.get(i);

            String key = criteria.getCategoryName();

            //TODO NEEDS TO HANDLE WHEN BUCKET IS AVAILABLE

            /*if(key.contains(SearchConstant.BUCKET_ID)){
                andFlag=false;
                continue;
            }*/
            List<CategoryOptions> categoryOptions = criteria.getCategoryOptions();

            String categoryType = criteria.getCategoryType();
            if(ProcessData.isValidCollection(categoryOptions)) {

                if(i!=0){
                    if(andFlag) {
                        flQuery.append(SearchConstant.AND_WITH_SPACE);
                    }
                    andFlag=true;
                }

                if (SearchConstant.VALUE.equalsIgnoreCase(categoryType)) {
                    /*if(key.contains(SearchConstant.BUCKET_ID)){
                        key = SearchConstant.DOCUMENT_STATUS_ID;
                    }*/
                    setValueTypePreviousCriteria(flQuery, key, categoryOptions,ProductApplication.getEnumById(searchRequest.getApplicationId()));
                } else {
                    setDateTimeTatPreviousCriteria(flQuery, key, categoryType, categoryOptions);
                }
            }
        }
        return flQuery.toString();
    }

    @Override
    public String createQueryForCassandra(SearchSuggestionRequest searchRequest) {
        List<PreviousSearchCriteria> previousSearchCriteria = searchRequest.getPreviousSearchCriteria();
        for(int i=0;i< previousSearchCriteria.size();i++){

        }
        return null;
    }

    private void setValueTypePreviousCriteria(StringBuffer flQuery, String key, List<CategoryOptions> categoryOptions, ProductApplication currentApplication) {
        String actualKey = key;
        if(key.contains(SearchConstant.BUCKET_ID) || key.contains(SearchConstant.SUB_BUCKET_ID) || key.contains(SearchConstant.STATUS_ID)){
            key = SearchConstant.DOCUMENT_STATUS_ID;
        }

        flQuery.append(SearchConstant.CIRCULAR_OPEN_BRACKET+key+SearchConstant.COLON+SearchConstant.CIRCULAR_OPEN_BRACKET);

        for(int j=0;j< categoryOptions.size();j++){

            String optionName = categoryOptions.get(j).getOptionName();
            if(key.contains(SearchConstant.DOCUMENT_STATUS_ID)){
                String statuses = searchUtil.getBucketStatusEnum(ProcessData.getInteger(optionName),actualKey,currentApplication);
                        //BucketStatus.getBucketStatusEnum(ProcessData.getInteger(optionName));
                flQuery.append(statuses);
            }else{
                //optionName=optionName.replaceAll(SearchConstant.SINGLE_SPACE,SearchConstant.PLUS_OPERATOR);
                flQuery.append("\"" + optionName +"\"");
            }

            if(j!=categoryOptions.size()-1) {
                flQuery.append(SearchConstant.OR_WITH_SPACE);
            }
        }
        flQuery.append(SearchConstant.CIRCULAR_CLOSE_BRACKET+SearchConstant.CIRCULAR_CLOSE_BRACKET);
    }

    private String getValueAsPerCategoryType(String key, String categoryType, String rawValue,boolean isMaxFormat) {
        if(SearchConstant.REMAINING_TAT.equalsIgnoreCase(key)){
            return getDateForRemainingTat(rawValue);
        }else if(SearchConstant.DATE.equalsIgnoreCase(categoryType)){
            String dateString = DateContent.formatDateIntoString(DateContent.formatStringIntoDate(rawValue, SearchConstant.MM_DD_YYY), SearchConstant.YYYY_MM_DD);

            if(isMaxFormat){
                return dateString + SearchConstant.SOLR_MAX_DATE_FORMAT;
            }else {
                return dateString + SearchConstant.SOLR_MIN_DATE_FORMAT;
            }

        }else if(SearchConstant.NUMBER.equalsIgnoreCase(categoryType)){
            Integer rawValueInt = Integer.parseInt(rawValue);
            if(SearchConstant.TAT.equalsIgnoreCase(key) || SearchConstant.AUDIO_FILE_PLAY_TIME.equalsIgnoreCase(key)){
                //Converted into hours and minutes
                return rawValueInt*60+SearchConstant.EMPTY_STRING;
            }
            return rawValue;
        }
        return SearchConstant.EMPTY_STRING;
    }

    private void setDateTimeTatPreviousCriteria(StringBuffer flQuery, String key, String categoryType, List<CategoryOptions> categoryOptions) {
        CategoryOptions options = categoryOptions.get(0);
        String operator = options.getOperator();
        String fromNumber = options.getFrom();
        String toNumber = options.getTo();
        StringBuffer queryValue = new StringBuffer();

        if(ProcessData.isValid(operator) && ProcessData.isValid(fromNumber)) {
            String actualFromValue = getValueAsPerCategoryType(key,categoryType,fromNumber,false);
            String actualFromValueMax = getValueAsPerCategoryType(key,categoryType,fromNumber,true);
            if (SearchConstant.LESS_THAN.equalsIgnoreCase(operator)) {
                queryValue.append(SearchConstant.START_WITH_ANY_STRING_SOLR_FORMAT);
                //EZMT-626
                queryValue.append(SearchConstant.NUMBER.equalsIgnoreCase(categoryType) && !SearchConstant.REMAINING_TAT.equalsIgnoreCase(key)?Integer.parseInt(actualFromValue)-1:actualFromValue);
                queryValue.append(SearchConstant.SINGLE_SPACE + SearchConstant.RECTANGLE_CLOSE_BRACE);
            }else if (SearchConstant.GREATER_THAN.equalsIgnoreCase(operator)) {
                queryValue.append(SearchConstant.RECTANGLE_OPEN_BRACE);
                //EZMT-626
                queryValue.append(SearchConstant.NUMBER.equalsIgnoreCase(categoryType) && !SearchConstant.REMAINING_TAT.equalsIgnoreCase(key)?Integer.parseInt(actualFromValueMax)+1:actualFromValueMax);
                queryValue.append(SearchConstant.END_WITH_ANY_STRING_SOLR_FORMAT);
            }else if (SearchConstant.EQUAL.equalsIgnoreCase(operator)) {
                queryValue.append(SearchConstant.RECTANGLE_OPEN_BRACE);
                queryValue.append(actualFromValue);
                queryValue.append(SearchConstant.TO_WITH_SPACE);
                queryValue.append(actualFromValueMax);
                queryValue.append(SearchConstant.RECTANGLE_CLOSE_BRACE);
            }else if (SearchConstant.BETWEEN.equalsIgnoreCase(operator)) {
                queryValue.append(SearchConstant.RECTANGLE_OPEN_BRACE);
                queryValue.append(actualFromValue);

                if(ProcessData.isValid(toNumber)) {
                    queryValue.append(SearchConstant.TO_WITH_SPACE);
                    queryValue.append(getValueAsPerCategoryType(key,categoryType,toNumber,true));
                    queryValue.append(SearchConstant.RECTANGLE_CLOSE_BRACE);
                }else {
                    queryValue.append(SearchConstant.END_WITH_ANY_STRING_SOLR_FORMAT);
                }
            }
        }

        if(ProcessData.isValid(queryValue)){
            flQuery.append(key + SearchConstant.COLON + queryValue);
        }
    }

    private String getDateForRemainingTat(String toDate) {
        Calendar currentGMTDate = Calendar.getInstance();
        currentGMTDate.setTime(DateContent.getCurrentGMTDate());
        currentGMTDate.add(Calendar.HOUR, Integer.parseInt(toDate));

        StringBuffer remTatDateString = new StringBuffer(DateContent.formatDateIntoString(currentGMTDate.getTime(), SearchConstant.YYYY_MM_DD_HH_MM_SS));
        int spaceIndex = remTatDateString.indexOf(SearchConstant.SINGLE_SPACE);
        remTatDateString.replace (spaceIndex,spaceIndex+1, SearchConstant.T);
        remTatDateString.append(SearchConstant.SOLR_MILISECONDS_FORMAT);

        return remTatDateString.toString();
    }

    @Override
    public QueryResponse getSolrWorklistQueryResult(WorkListRequest workListRequest) {
        Integer applicationId = workListRequest.getApplicationId();
        ProductApplication currentApplication = ProductApplication.getEnumById(applicationId);

        cloudSolrClient.setDefaultCollection(searchUtil.getSolrSearchCollection(currentApplication));
        cloudSolrClient.connect();
        SearchSuggestionRequest searchSuggestionRequest = new SearchSuggestionRequest();
        searchSuggestionRequest.setPreviousSearchCriteria(workListRequest.getOther());
        searchSuggestionRequest.setApplicationId(applicationId);
        String flQuery =createSolrSearchFilterQuery(searchSuggestionRequest);

        if(currentApplication.equals(ProductApplication.MTSCRIBES)) {
            // TODO user id need to take from request currently hard coding
            String userIdClause = "(user_id:10)";
            if (ProcessData.isValid(flQuery)) {
                flQuery += " AND " + userIdClause;
            } else {
                flQuery = userIdClause;
            }
        }

        return searchService.getSearchResult(flQuery, null, workListRequest.getSort().getKey(), workListRequest.getSort().getOrder(), 0, Integer.MAX_VALUE,currentApplication);
    }
}
