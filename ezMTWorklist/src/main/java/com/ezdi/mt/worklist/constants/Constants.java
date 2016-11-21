/**
 * 
 */
package com.ezdi.mt.worklist.constants;

import java.util.List;

/**
 * @author EZDI\atul.r
 *
 */
public class Constants {
	
	public static final String GROUP_BY_HOSPITAL = "hospital_value";
	public static final String GROUP_BY_WORKTYPE = "worktype_value";
	public static final String GROUP_BY_PHYSICIAN = "dictating_physician";
	public static final String GROUP_BY_TAT = "remaining_tat";
	public static final String LESS_THAN_2HR = "< 2 hrs";
	public static final String BETWEEN_2HRTO4HR = "2 hrs to 4 hrs";
	public static final String GREATER_THAN_4HR= "> 4 hrs";

    public static final Integer READY_FOR_TX= 1000;
    public static final Integer COMPLETED= 1150;
    public static final Integer NEW= 1300;
    public static final Integer EDITING= 1320;
    public static final Integer DRAFTED= 1340;

    public static final String NOT_SPECIFIED="Not specified";

    public static final Integer MAX_COUNT = 20;
    public static final Integer ONE = 1;
    public static final Integer ZERO = 0;
    public static final Integer SIXTY = 60;


    public static final String ONE_STRING = "1";
    public static final String TWO_STRING = "2";

    public static final String GET_WORK_LIST_QUERY = "select * from ez_document_master_mt where user_id=%d and document_current_status_id=%d allow filtering";
    public static final String GET_BUCKET_LIST = "select * from ez_mt_bucket_status_map";
    public static final String CASSANDRA_QUERY_FOR_FETCHING_STATUS_INFO = "select status_id, milestone_id, milestone_value from ez_status_master";

    public static final String UNDERSCORE = "_";
    public static final String STATUS_ID = "status_id";
}
