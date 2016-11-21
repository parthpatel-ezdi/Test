package com.ezdi.mt.worklist.dto;

import java.util.List;

public class InitList {

	private List<ItemSortBy> itemSortBy;

    private List<BucketListItem> bucketList;

	public List<ItemSortBy> getItemSortBy() {
		return itemSortBy;
	}

	public void setItemSortBy(List<ItemSortBy> itemSortBy) {
		this.itemSortBy = itemSortBy;
	}

    public List<BucketListItem> getBucketList() {
        return bucketList;
    }

    public void setBucketList(List<BucketListItem> bucketList) {
        this.bucketList = bucketList;
    }
}
