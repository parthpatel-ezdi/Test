/**
 * 
 */
package com.ezdi.mt.worklist.dto;

/**
 * @author EZDI\atul.r
 *
 */
public class CategoryOptions
{
	private String optionAlias;
    private String optionName;
    private String optionValue;
    private String from;
    private String to;
    private String operator;
    private String value;
    

    public String getOptionName ()
    {
        return optionName;
    }

    public void setOptionName (String optionName)
    {
        this.optionName = optionName;
    }

    public String getOptionValue ()
    {
        return optionValue;
    }

    public void setOptionValue (String optionValue)
    {
        this.optionValue = optionValue;
    }

    
    public String getOptionAlias() {
		return optionAlias;
	}

	public void setOptionAlias(String optionAlias) {
		this.optionAlias = optionAlias;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
    public String toString()
    {
        return "ClassPojo [optionName = "+optionName+", optionValue = "+optionValue+"]";
    }
}