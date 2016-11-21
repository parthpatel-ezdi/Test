/**
 * 
 *//*

package com.ezdi.mt.worklist.util;

import com.ezdi.component.logger.EzdiLogManager;
import com.ezdi.component.logger.EzdiLogger;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

*/
/**
 * @author EZDI\atul.r
 *
 *//*

public abstract class ProcessData {

    private static EzdiLogger LOG = EzdiLogManager.getLogger(ProcessData.class);
	 */
/**
     * to check whether this object is valid or not.
     *//*

    public static boolean isValid(final Object obj) {
        if (obj == null) {
            return false;
        } else {
            if (obj instanceof String) {
                final String str = (String) obj;
                return !(str.isEmpty() || "null".equals(str));
            } else if (obj instanceof Map) {
                final HashMap json = (HashMap) obj;
                if (json == null || json.isEmpty()) {
                    return false;
                }
            }
            return true;
        }
    }
    
    */
/**
     * to check whether this object is valid or not.
     *//*

    public static boolean isValidCollection(final Collection<?> collection) {
        return null != collection && !collection.isEmpty();
    }

    public static String getAsString(final Object obj){
        return isValid(obj)?obj.toString():"";
    }

    */
/**
     * get integer from object
     *//*

    public static Integer getInteger(final Object obj) {
        if (obj == null) {
            return null;
        } else {
            if (obj instanceof Integer) {
                return (Integer) obj;
            }
            if (obj instanceof Double) {
                return (int) Math.round(getDouble(obj));
            }
            if (obj instanceof Float) {
                return Math.round(getFloat(obj));
            }
            if (obj instanceof String) {
                try {
                    if (isValid(obj)) {
                        return Integer.parseInt((String) obj);
                    } else {
                        return null;
                    }
                } catch (Exception exception) {
                    LOG.error(exception);
                    return null;
                }
            } else {
                try {
                    return Integer.parseInt(obj.toString());
                } catch (Exception exception) {
                    LOG.error(exception);
                    return null;
                }
            }
        }
    }

    public static Float getFloat(final Object obj) {

        if (obj == null) {
            return null;
        } else {
            if (obj instanceof Float) {
                return (Float) obj;
            }
            if (obj instanceof String) {
                try {
                    return Float.parseFloat((String) obj);
                } catch (Exception exception) {
                    LOG.error(exception);
                    return null;
                }
            } else {
                try {
                    return Float.parseFloat(obj.toString());
                } catch (Exception exception) {
                    LOG.error(exception);
                    return null;
                }
            }
        }
    }

    */
/**
     * get integer from object
     *//*

    public static Long getLong(final Object obj) {
        if (isValid(obj)) {
            if (obj instanceof Long) {
                return (Long) obj;
            }
            if (obj instanceof String) {
                try {
                    return Long.parseLong((String) obj);
                } catch (Exception exception) {
                    LOG.error(exception);
                    return null;
                }
            } else {
                try {
                    return Long.parseLong(obj.toString());
                } catch (Exception exception) {
                    LOG.error(exception);
                    return null;
                }
            }

        } else {
            return null;
        }
    }

    */
/**
     * get double from object
     *//*

    public static Double getDouble(final Object obj) {
        if (isValid(obj)) {
            if (obj instanceof Double) {
                return (Double) obj;
            }
            if (obj instanceof String) {
                try {
                    return Double.parseDouble((String) obj);
                } catch (Exception exception) {
                    LOG.error(exception);
                    return null;
                }
            } else {
                try {
                    return Double.parseDouble(obj.toString());
                } catch (Exception exception) {
                    LOG.error(exception);
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    public static Date getDate(final Object object) {
        if (!isValid(object)) {
            return null;
        }
        if (object instanceof Timestamp) {
            return new Date(((Timestamp) object).getTime());
        }
        if (object instanceof Date) {
            return ((Date) object);
        }
        if (object instanceof String) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(((String) object));
            } catch (ParseException parseException) {
                LOG.error(parseException);
            }
        }
        return null;
    }
}
*/
