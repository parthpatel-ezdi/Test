package com.ezdi.mt.search.dto;

/**
 * Created by akash.p on 27/5/16.
 */
public class SearchSuggestionResponse {

    private String categoryId;
    private String radioOptions ;
    private boolean suggestionCategory ;
    private String suggestionCategoryType ;
    private String suggestionCategoryUrl ;
    private String suggestionCategoryUrlMethod;
    private String suggestionId;
    private String suggestionLabel ;
    private String suggestionString;
    private String valueSuggestion ;

    public SearchSuggestionResponse(){

    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getRadioOptions() {
        return radioOptions;
    }

    public void setRadioOptions(String radioOptions) {
        this.radioOptions = radioOptions;
    }

    public boolean getSuggestionCategory() {
        return suggestionCategory;
    }

    public void setSuggestionCategory(boolean suggestionCategory) {
        this.suggestionCategory = suggestionCategory;
    }

    public String getSuggestionCategoryType() {
        return suggestionCategoryType;
    }

    public void setSuggestionCategoryType(String suggestionCategoryType) {
        this.suggestionCategoryType = suggestionCategoryType;
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

    public String getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(String suggestionId) {
        this.suggestionId = suggestionId;
    }

    public String getSuggestionLabel() {
        return suggestionLabel;
    }

    public void setSuggestionLabel(String suggestionLabel) {
        this.suggestionLabel = suggestionLabel;
    }

    public String getSuggestionString() {
        return suggestionString;
    }

    public void setSuggestionString(String suggestionString) {
        this.suggestionString = suggestionString;
    }

    public String getValueSuggestion() {
        return valueSuggestion;
    }

    public void setValueSuggestion(String valueSuggestion) {
        this.valueSuggestion = valueSuggestion;
    }

    @Override
    public int hashCode() {
        char value[]=categoryId.toCharArray();
        int h = 0;
        if (value.length > 0) {
            char val[] = value;

            for (int i = 0; i < value.length; i++) {
                h = 31 * h + val[i];
            }
        }
        return h;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SearchSuggestionResponse) {
            return categoryId.equals(((SearchSuggestionResponse)obj).categoryId) && suggestionString.equals(((SearchSuggestionResponse)obj).suggestionString);
        }
        return super.equals(obj);
    }
}