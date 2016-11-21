package com.ezdi.mt.search.enums;

/**
 * Created by akash.p on 25/8/16.
 */
public enum ProductApplication {

    MTSCRIBES(2),
    CONTROLLER(3)
    ;

    private Integer applicationId;

    ProductApplication(Integer applicationId){
        this.applicationId = applicationId;
    }

    public static ProductApplication getEnumById(Integer applicationId){
        switch (applicationId){
            case 2 : return MTSCRIBES;
            case 3 : return CONTROLLER;
        }
        //Default Value
        return MTSCRIBES;
    }

    public Integer getApplicationId() {
        return applicationId;
    }
}
