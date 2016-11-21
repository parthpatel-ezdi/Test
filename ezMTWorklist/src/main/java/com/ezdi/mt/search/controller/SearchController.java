package com.ezdi.mt.search.controller;

import com.ezdi.component.logger.EzdiLogManager;
import com.ezdi.component.logger.EzdiLogger;
import com.ezdi.exception.SystemException;
import com.ezdi.exception.controller.BaseController;
import com.ezdi.exception.enums.LoginCode;
import com.ezdi.exception.response.JsonResponse;
import com.ezdi.mt.search.business.SearchManagement;
import com.ezdi.mt.search.constant.SearchConstant;
import com.ezdi.mt.search.dto.SearchSuggestionRequest;
import com.ezdi.mt.search.dto.SearchSuggestionResponse;
import com.ezdi.mt.worklist.dto.WorkListRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by akash.p on 27/5/16.
 */
@RestController
public class SearchController extends BaseController {

    @Autowired
    private SearchManagement searchManagement;

    private static EzdiLogger LOG = EzdiLogManager.getLogger(SearchController.class);

    @RequestMapping(value="/searchSuggestion", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse searchSuggestion(@RequestBody SearchSuggestionRequest request){
        LOG.info("Inside searchSuggestion Controller");
        List<SearchSuggestionResponse> searchResponses = searchManagement.getSearchSuggestion(request);
        JsonResponse jsonResponse = getResponse(true, 200, SearchConstant.SUCCESS, searchResponses);
        LOG.info("Exit from searchSuggestion Controller");
        return jsonResponse;
    }

    @RequestMapping(value="/searchResult", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse searchResult(@RequestBody WorkListRequest request){
        LOG.info("Inside searchResult Controller");
        QueryResponse queryResponse = searchManagement.getSolrWorklistQueryResult(request);
        JsonResponse jsonResponse = getResponse(true, 200, SearchConstant.SUCCESS, queryResponse.getResults());
        LOG.info("Exit from searchResult Controller");
        return jsonResponse;
    }
}
