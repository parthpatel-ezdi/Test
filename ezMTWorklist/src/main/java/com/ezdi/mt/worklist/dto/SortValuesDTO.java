/**
 * 
 */
package com.ezdi.mt.worklist.dto;

import com.ezdi.exception.response.Header;

/**
 * @author EZDI\atul.r
 *
 */
public class SortValuesDTO {
	private Header header;
	private InitList data;
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public InitList getData() {
		return data;
	}
	public void setData(InitList data) {
		this.data = data;
	}
	
	
}
