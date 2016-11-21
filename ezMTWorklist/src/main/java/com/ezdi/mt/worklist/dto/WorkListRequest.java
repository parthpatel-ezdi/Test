/**
 * 
 */
package com.ezdi.mt.worklist.dto;

import com.ezdi.mt.search.dto.PreviousSearchCriteria;

import java.io.Serializable;
import java.util.List;

/**
 * @author EZDI\atul.r
 *
 */
public class WorkListRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9184737228128919407L;
	private Pagination pagination;
	private Sort sort;
	private Integer userId;
    private List<Integer> statusList;
	private List<PreviousSearchCriteria> other;
    private Integer applicationId=0;

	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	public Sort getSort() {
		return sort;
	}
	public void setSort(Sort sort) {
		this.sort = sort;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<PreviousSearchCriteria> getOther() {
        return other;
    }

    public void setOther(List<PreviousSearchCriteria> other) {
        this.other = other;
    }

    public List<Integer> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Integer> statusList) {
        this.statusList = statusList;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }
}
