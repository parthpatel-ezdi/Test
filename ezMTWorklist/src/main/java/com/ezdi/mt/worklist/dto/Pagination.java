package com.ezdi.mt.worklist.dto;

public class Pagination {
    private Integer start = 1;

    private long total = 0;

    private Integer count = 0;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String toString(){
    	   StringBuffer buffer = new StringBuffer();
    	   buffer.append("{");
    	   buffer.append("start:"+getStart() );
     	   buffer.append("}");
    	   return buffer.toString();
    }
}
