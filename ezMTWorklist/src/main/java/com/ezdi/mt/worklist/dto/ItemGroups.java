/**
 * 
 */
package com.ezdi.mt.worklist.dto;

import com.ezdi.mt.worklist.constants.Constants;

import java.util.List;

/**
 * @author EZDI\atul.r
 *
 */
public class ItemGroups implements Comparable<ItemGroups> {

	private String itemGroupAlias;
	List<Items> items;
	
	public String getItemGroupAlias() {
		return itemGroupAlias;
	}
	public void setItemGroupAlias(String itemGroupAlias) {
		this.itemGroupAlias = itemGroupAlias;
	}
	public List<Items> getItems() {
		return items;
	}
	public void setItems(List<Items> items) {
		this.items = items;
	}
	@Override
	public int compareTo(ItemGroups o) {

        if(this.itemGroupAlias.equalsIgnoreCase(Constants.LESS_THAN_2HR) &&
                o.itemGroupAlias.equalsIgnoreCase(Constants.LESS_THAN_2HR)){
            return 0;
        }else if(this.itemGroupAlias.equalsIgnoreCase(Constants.LESS_THAN_2HR) ||
                o.itemGroupAlias.equalsIgnoreCase(Constants.LESS_THAN_2HR)){
            return 1;
        }else if(this.itemGroupAlias.equalsIgnoreCase(Constants.NOT_SPECIFIED) &&
                o.itemGroupAlias.equalsIgnoreCase(Constants.NOT_SPECIFIED)){
            return 0;
        }else if(this.itemGroupAlias.equalsIgnoreCase(Constants.NOT_SPECIFIED) ||
                o.itemGroupAlias.equalsIgnoreCase(Constants.NOT_SPECIFIED)){
            return 1;
        }

		return (this.itemGroupAlias).compareTo(o.itemGroupAlias);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((itemGroupAlias == null) ? 0 : itemGroupAlias.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemGroups other = (ItemGroups) obj;
		if (itemGroupAlias == null) {
			if (other.itemGroupAlias != null)
				return false;
		} else if (!itemGroupAlias.equals(other.itemGroupAlias))
			return false;
		return true;
	}
	
	
}
