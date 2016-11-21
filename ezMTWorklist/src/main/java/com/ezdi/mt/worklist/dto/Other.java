/**
 * 
 */
package com.ezdi.mt.worklist.dto;

import java.util.List;

/**
 * @author EZDI\atul.r
 *
 */
public class Other
{
	private int suggestionId;
	private String categoryName;
	private String categoryType;
    private String suggestionString;
    private String suggestionLabel;
    private String suggestionCategory;
    private String suggestionCategoryUrl;
    private String suggestionCategoryUrlMethod;
    private String suggestionCategoryType;

    private List<CategoryOptions> categoryOptions;

   

    public String getSuggestionString ()
    {
        return suggestionString;
    }

    public void setSuggestionString (String suggestionString)
    {
        this.suggestionString = suggestionString;
    }

    public String getCategoryName ()
    {
        return categoryName;
    }

    public void setCategoryName (String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getSuggestionLabel ()
    {
        return suggestionLabel;
    }

    public void setSuggestionLabel (String suggestionLabel)
    {
        this.suggestionLabel = suggestionLabel;
    }

    public String getSuggestionCategory ()
    {
        return suggestionCategory;
    }

    public void setSuggestionCategory (String suggestionCategory)
    {
        this.suggestionCategory = suggestionCategory;
    }

    public String getCategoryType ()
    {
        return categoryType;
    }

    public void setCategoryType (String categoryType)
    {
        this.categoryType = categoryType;
    }


    public List<CategoryOptions> getCategoryOptions() {
		return categoryOptions;
	}

	public void setCategoryOptions(List<CategoryOptions> categoryOptions) {
		this.categoryOptions = categoryOptions;
	}

	
    public int getSuggestionId() {
		return suggestionId;
	}

	public void setSuggestionId(int suggestionId) {
		this.suggestionId = suggestionId;
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

	@Override
    public String toString()
    {
        return "ClassPojo [suggestionString = "+suggestionString+", categoryName = "+categoryName+", suggestionLabel = "+suggestionLabel+", suggestionCategory = "+suggestionCategory+", categoryType = "+categoryType+", categoryOptions = "+categoryOptions+", suggestionId = "+suggestionId+"]";
    }
}
			

