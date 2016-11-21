/**
 * 
 */
package com.ezdi.mt.worklist.dto;

import com.ezdi.exception.response.Header;

/**
 * @author EZDI\atul.r
 *
 */
public class WorkListResponse {

	private Header header;
	private Data data;
	private Pagination pagination;
	private ItemCount itemCount;
    private Sort sort;
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

    public ItemCount getItemCount() {
        return itemCount;
    }

    public void setItemCount(ItemCount itemCount) {
        this.itemCount = itemCount;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
