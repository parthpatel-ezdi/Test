/*
package com.ezdi.mt.worklist.util;

import com.ezdi.component.logger.EzdiLogManager;
import com.ezdi.component.logger.EzdiLogger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

*/
/**
 * Created by akash.p on 7/6/16.
 *//*

public class DateContent {

    private static final EzdiLogger LOG = EzdiLogManager.getLogger(DateContent.class);

    public static String formatStringIntoString(final String date, String dateFormat) {
        String formattedString = null;
        Date formattedDate = null;
        if (!ProcessData.isValid(dateFormat)) {
            dateFormat = "MM-dd-yyyy HH:mm:ss";
        }

        try {
            if (ProcessData.isValid(date)) {
                formattedDate = formatStringIntoDate(date, dateFormat);
                formattedString = formatDateIntoString(formattedDate, dateFormat);
            }

        } catch (Exception e) {
            LOG.trace(e);
        }

        return formattedString;
    }

    public static Date formatStringIntoDate(final String date, final String dateFormat) {
        DateFormat formatter;
        Date formattedDate = null;
        if (ProcessData.isValid(dateFormat)) {
            formatter = new SimpleDateFormat(dateFormat, Locale.getDefault());
        } else {
            formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss", Locale.getDefault());
        }
        try {
            if (ProcessData.isValid(date)) {
                formattedDate = formatter.parse(date);
            }
        } catch (ParseException e) {
            LOG.trace(e);
        }

        return formattedDate;
    }

    public static String formatDateIntoString(final Date date, final String dateFormat) {
        DateFormat formatter;
        String formattedString = null;
        if (ProcessData.isValid(dateFormat)) {
            formatter = new SimpleDateFormat(dateFormat, Locale.getDefault());
        } else {
            formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss", Locale.getDefault());
        }
        try {
            if (ProcessData.isValid(date)) {
                formattedString = formatter.format(date);
            }
        } catch (Exception e) {
            LOG.trace(e);
        }

        return formattedString;
    }

    public static Date getCurrentGMTDate() {
        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        Calendar calendar = Calendar.getInstance(timeZone);
        return  calendar.getTime();
    }
}
*/
