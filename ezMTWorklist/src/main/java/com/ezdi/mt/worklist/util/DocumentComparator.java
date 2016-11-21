package com.ezdi.mt.worklist.util;

import com.ezdi.mt.worklist.pojo.TranscriptionDocument;

import java.util.Comparator;

/**
 * Created by EZDI\atul.r on 8/6/16.
 */
public class DocumentComparator {

    public static Comparator<TranscriptionDocument> sortByPhyName(){
        return new Comparator<TranscriptionDocument>() {
            @Override
            public int compare(TranscriptionDocument o1, TranscriptionDocument o2) {
                return o1.getDictatingPhysicianName().compareTo(o2.getDictatingPhysicianName());
            }
        };
    }

    public static Comparator<TranscriptionDocument> sortByHospitalName(){
        return new Comparator<TranscriptionDocument>() {
            @Override
            public int compare(TranscriptionDocument o1, TranscriptionDocument o2) {
                return o1.getHospitalName().compareTo(o2.getHospitalName());
            }
        };
    }

    public static Comparator<TranscriptionDocument> sortByWorkType(){
        return new Comparator<TranscriptionDocument>() {
            @Override
            public int compare(TranscriptionDocument o1, TranscriptionDocument o2) {
                return o1.getWorktypeValue().compareTo(o2.getWorktypeValue());
            }
        };
    }

    public static Comparator<TranscriptionDocument> sortByRemTat(){
        return new Comparator<TranscriptionDocument>() {
            @Override
            public int compare(TranscriptionDocument o1, TranscriptionDocument o2) {
                return o1.getRemainingTat().compareTo(o2.getRemainingTat());
            }
        };
    }
}
