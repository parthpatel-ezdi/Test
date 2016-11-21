/**
 * 
 */
package com.ezdi.mt.worklist.dto;

/**
 * @author EZDI\atul.r
 *
 */
public class ItemSortBy {
	private boolean selected;
	private String sortingAlias;
	private String sortingName;
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getSortingAlias() {
		return sortingAlias;
	}
	public void setSortingAlias(String sortingAlias) {
		this.sortingAlias = sortingAlias;
	}
	public String getSortingName() {
		return sortingName;
	}
	public void setSortingName(String sortingName) {
		this.sortingName = sortingName;
	}
	
	
}
