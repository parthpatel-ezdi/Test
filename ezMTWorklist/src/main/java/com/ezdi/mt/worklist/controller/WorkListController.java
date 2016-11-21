/**
 *
 */
package com.ezdi.mt.worklist.controller;

import com.ezdi.component.logger.EzdiLogManager;
import com.ezdi.component.logger.EzdiLogger;
import com.ezdi.exception.controller.BaseController;
import com.ezdi.exception.response.Header;
import com.ezdi.exception.response.JsonResponse;
import com.ezdi.mt.core.util.ProcessData;
import com.ezdi.mt.search.constant.SearchConstant;
import com.ezdi.mt.worklist.business.WorkListManagement;
import com.ezdi.mt.worklist.dto.InitList;
import com.ezdi.mt.worklist.dto.WorkListRequest;
import com.ezdi.mt.worklist.dto.WorkListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author EZDI\atul.r
 *
 */
@RestController
@RequestMapping(value = "/search")
public class WorkListController extends BaseController {
    private static EzdiLogger LOG = EzdiLogManager.getLogger(WorkListController.class);

    //@Autowired
    //private RoleMasterDao roleMasterDao;

    @Autowired
    WorkListManagement workListManagement;

    /*@ExceptionHandler(NoHostAvailableException.class)
    public void handleNoHostAvailableException(NoHostAvailableException e) throws Exception {
        roleMasterDao.handleNoHostAvailableException();
    }*/

    @RequestMapping(value="/getWorklist", method = RequestMethod.POST)
    @ResponseBody
    public WorkListResponse getTranscriptionList(@Valid @RequestBody WorkListRequest request){
        LOG.info("Inside Controller getTranscriptionList");
        WorkListResponse workListResponse = null;
        try {
            workListResponse = workListManagement.getTranscriptionList(request);
        } catch (Exception e) {
            LOG.error("Error occurred while returning the work list "+e);
            workListResponse = new WorkListResponse();
            workListResponse.setHeader(new Header("Something went wrong", 0, false));
        }
        LOG.info("Exit from Controller getTranscriptionList");
        return workListResponse;
    }

    @RequestMapping(value="/getInitLists", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getInitList(HttpSession session) throws Exception {
        JsonResponse response = null;
        LOG.info("Inside getInitList controller - ");
            InitList sortList = workListManagement.getInitList();
        return getResponse(true,200,"Success",sortList);

    }
    //MDC POC
    /*@RequestMapping(value="/getRoles", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getRoles() throws Exception {
        JsonResponse response = null;
        try {
            ResultSet object = roleMasterDao.getRoles();
            if(ProcessData.isValid(object)){
                response = getResponse(true,200, SearchConstant.SUCCESS,"success");
            }else{
                response = getResponse(false,000, SearchConstant.FAILURE,null);
            }

        } catch (Exception e) {
            LOG.error("Error occurred while returning sort drop down list is - " + e);
            throw e;
        }
        return response;
    }

    @RequestMapping(value="/shiftToDataCenter1", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse shiftToDataCenter1() throws Exception {
        JsonResponse response = null;
        try {
            Boolean object = roleMasterDao.shiftToDataCenter1();
            if(ProcessData.isValid(object)){
                response = getResponse(true,200, SearchConstant.SUCCESS,"success");
            }else{
                response = getResponse(false,000, SearchConstant.FAILURE,null);
            }

        } catch (Exception e) {
            LOG.error("Error occurred while returning sort drop down list is - " + e);
            throw e;
        }
        return response;
    }*/
}
