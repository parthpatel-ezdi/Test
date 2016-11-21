package com.ezdi.mt.search.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.p on 27/5/16.
 */
public class SearchSuggestionRequest implements Serializable {

    private static final long serialVersionUID = -6901735092877746166L;

    private CurrentSearchCriteria currentSearchCriteria;

    private List<PreviousSearchCriteria> previousSearchCriteria = new ArrayList<PreviousSearchCriteria>();

    private Integer applicationId=0;

    public CurrentSearchCriteria getCurrentSearchCriteria() {
        return currentSearchCriteria;
    }

    public void setCurrentSearchCriteria(CurrentSearchCriteria currentSearchCriteria) {
        this.currentSearchCriteria = currentSearchCriteria;
    }

    public List<PreviousSearchCriteria> getPreviousSearchCriteria() {
        return previousSearchCriteria;
    }

    public void setPreviousSearchCriteria(List<PreviousSearchCriteria> previousSearchCriteria) {
        this.previousSearchCriteria = previousSearchCriteria;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }
}