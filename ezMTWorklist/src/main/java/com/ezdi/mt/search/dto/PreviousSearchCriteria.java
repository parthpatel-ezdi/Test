package com.ezdi.mt.search.dto;

import com.ezdi.mt.worklist.dto.CategoryOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.p on 1/6/16.
 */
public class PreviousSearchCriteria implements Serializable {

    private static final long serialVersionUID = -6901735092877746166L;

    private String suggestionId;
    private String categoryName;
    private String categoryType;
    private String suggestionString;
    private String suggestionLabel;
    private Boolean suggestionCategory;
    private String suggestionCategoryUrl;
    private String suggestionCategoryUrlMethod;
    private String suggestionCategoryType;
    private List<CategoryOptions> categoryOptions = new ArrayList<CategoryOptions>();
    private String valueSuggestion;
    private Object radioOptions;


    public String getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(String suggestionId) {
        this.suggestionId = suggestionId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getSuggestionString() {
        return suggestionString;
    }

    public void setSuggestionString(String suggestionString) {
        this.suggestionString = suggestionString;
    }

    public String getSuggestionLabel() {
        return suggestionLabel;
    }

    public void setSuggestionLabel(String suggestionLabel) {
        this.suggestionLabel = suggestionLabel;
    }

    public Boolean getSuggestionCategory() {
        return suggestionCategory;
    }

    public void setSuggestionCategory(Boolean suggestionCategory) {
        this.suggestionCategory = suggestionCategory;
    }

    public String getSuggestionCategoryUrl() {
        return suggestionCategoryUrl;
    }

    public void setSuggestionCategoryUrl(String suggestionCategoryUrl) {
        this.suggestionCategoryUrl = suggestionCategoryUrl;
    }

    public String getSuggestionCategoryUrlMethod() {
        return suggestionCategoryUrlMethod;
    }

    public void setSuggestionCategoryUrlMethod(String suggestionCategoryUrlMethod) {
        this.suggestionCategoryUrlMethod = suggestionCategoryUrlMethod;
    }

    public String getSuggestionCategoryType() {
        return suggestionCategoryType;
    }

    public void setSuggestionCategoryType(String suggestionCategoryType) {
        this.suggestionCategoryType = suggestionCategoryType;
    }

    public List<CategoryOptions> getCategoryOptions() {
        return categoryOptions;
    }

    public void setCategoryOptions(List<CategoryOptions> categoryOptions) {
        this.categoryOptions = categoryOptions;
    }

    public String getValueSuggestion() {
        return valueSuggestion;
    }

    public void setValueSuggestion(String valueSuggestion) {
        this.valueSuggestion = valueSuggestion;
    }

    public Object getRadioOptions() {
        return radioOptions;
    }

    public void setRadioOptions(Object radioOptions) {
        this.radioOptions = radioOptions;
    }
}
