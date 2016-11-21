package com.ezdi.mt.search.dto;

import java.io.Serializable;

/**
 * Created by akash.p on 1/6/16.
 */
public class CurrentSearchCriteria implements Serializable {

    private static final long serialVersionUID = -6901735092877746166L;

    private String key;
    private String filterCategory;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFilterCategory() {
        return filterCategory;
    }

    public void setFilterCategory(String filterCategory) {
        this.filterCategory = filterCategory;
    }
}
