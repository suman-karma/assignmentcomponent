/*
 * $Source: /web/cvs/connect-suite/connect/connect-server/src/main/java/com/mhe/connect/business/common/DateUtil.java,v
 * $ $Revision$ $Date: 2004/11/05 11:29 AM DateUtil.java Copyright 2001 The McGraw-Hill Companies. All Rights
 * Reserved Created on Jul 1, 2003, 12:32:03 PM by Hariharasudhan
 */

package io.mhe.assignmentcomponent.vo;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Use {@DateTimeSupport} instead of that class as well as Joda date/time API.
 */
@Deprecated
public abstract class DateUtil {

    public static final String DB_TIMEZONE_ID = "US/Eastern";
    public static final String UTC_TIMEZONE_ID = "UTC";

    public static final Map<String, SimpleDateFormat> DATE_FORMATTERS = new HashMap<String, SimpleDateFormat>();

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'VV'";
    public static final String DATE_FORMAT_WITH_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss'VV'";

    public static final TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone(DB_TIMEZONE_ID);

    public static final String DEFAULT_TIME_AND_TIMEZONE_FOR_DELAY_FEEDBACK_DATE = "12:00 am EST";

    public static final int NUM_SECONDS_IN_AN_HOUR = 60 * 60;
    public static final int NUM_MILLI_SECONDS_IN_AN_HOUR = NUM_SECONDS_IN_AN_HOUR * 1000;
    public static final int NUM_MINUTES_IN_AN_HOUR = 60;

    public static final String DATE_FORMAT_IN_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String DATE_FORMAT_IN_UTC_WITH_OFF_SET = "yyyy-MM-dd'T'HH:mm:ssZ";

    public static final String APX_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final int NUM_SECONDS_IN_MINUTE = 60;
    public static final int NUM_MILLI_SECONDS_IN_MINUTE = NUM_SECONDS_IN_MINUTE * 1000;
    public static final int NUM_HOURS_IN_DAY = 24;
    public static final int NUM_MILLI_SECONDS_IN_DAY = NUM_HOURS_IN_DAY
            * NUM_MILLI_SECONDS_IN_AN_HOUR;
    public static final int DATETIME_PARTS_AR_SIZE = 4;
    public static final int DATETIME_PARTS_DATE_INDEX = 0;
    public static final int DATETIME_PARTS_HOUR_INDEX = 1;
    public static final int DATETIME_PARTS_MINUTE_INDEX = 2;
    public static final int DATETIME_PARTS_AM_PM_INDEX = 3;
    public static final int CENTUARY_19TH = 1900;
    public static final int MILLI_SEC_UNIT = 1000;

    @Deprecated
    public static String formatDate(Date date, String timezoneId) {

        return DateTimeSupport.convertToString(new DateTime(date), DateTimeSupport.DEFAULT_DATE_FORMAT, timezoneId);
    }

    @Deprecated
    public static String formatDateThreadSafe(Date date, String timezoneId) {

        return DateTimeSupport.convertToString(new DateTime(date), DateTimeSupport.DEFAULT_DATE_FORMAT, timezoneId);
    }

    @Deprecated
    public static String formatDate(Date date, String timezoneId, String pattern) {

        return DateTimeSupport.convertToString(new DateTime(date), pattern, timezoneId);
    }

    @Deprecated
    public static String formatDateAMPMlowercase(Date date, String timezoneId, String pattern) {
        return DateTimeSupport.convertToString(date, pattern, timezoneId).toLowerCase();
    }

    @Deprecated
    public static Date format(Date date, String timezoneId) {

        final DateTime dt = new DateTime(date);
        return dt.toDateTime(DateTimeSupport.timeZoneForID(timezoneId)).toDate();
    }

    @Deprecated
    public static Date parseDateString(String dateInEst) {

        return parseDateString(dateInEst, DB_TIMEZONE_ID);
    }

    @Deprecated
    public static Date parseDateString(String dateInEst, String timeZone) {

        return DateTimeSupport.convertToDate(dateInEst, DateTimeSupport.DEFAULT_DATE_FORMAT, timeZone);
    }

    @Deprecated
    public static Date parseUTCDateStringWithOffSet(String utcDate, String timeZone) {

        return DateTimeSupport.convertToDate(utcDate, "yyyy-MM-dd'T'HH:mm:ssZ", timeZone);
    }

    @Deprecated
    public static String getTimeZone(String timeZoneId, String style) {
        // Style is not need since we always use the same pattern
        return DateTimeSupport.getTimezoneDisplayName(timeZoneId);
    }

    @Deprecated
    public static String formatToDefaultTimeZone(Date d) {

        return DateTimeSupport.convertToString(d, "dd-MMM-yyyy HH:mm:ss", DateTimeSupport.DEFAULT_TIMEZONE);
    }

    @Deprecated
    public static String formatDate(Date d) {

        return DateTimeSupport.convertToString(d, "yyyy-MM-dd HH:mm:ss", DateTimeSupport.DEFAULT_TIMEZONE);
    }

    @Deprecated
    public static double getHourOffSetFromUTC(String timeZone) {
        return DateTimeSupport.hoursOffsetFromUTC(timeZone);
    }

    @Deprecated
    public static String getDateInUserTimeZoneAndFormat(Date date, String timezone, String dateFormat) {
        return DateTimeSupport.convertToString(date, dateFormat, timezone);
    }

    @Deprecated
    public static long getDateDiffInDays(Date date1, Date date2) {
        long diff = date1.getTime() - date2.getTime();
        return diff / (NUM_MILLI_SECONDS_IN_DAY);
    }

    @Deprecated
    public static String[] getDateComponentsAsString(Date date) {

        return new String[]{DateTimeSupport.convertToString(new DateTime(date), "M/d/YYYY")};
    }

    @Deprecated
    public static String[] getDateComponentsAsString(Date date, String courseTimeZone) {

        final String strDate = DateTimeSupport.convertToString(new DateTime(date), "MM/dd/YYYY", courseTimeZone);
        final String hours = DateTimeSupport.convertToString(new DateTime(date), "hh", courseTimeZone);
        final String mins = DateTimeSupport.convertToString(new DateTime(date), "mm", courseTimeZone);
        final String ampm = DateTimeSupport.convertToString(new DateTime(date), "a", courseTimeZone);

        return new String[]{strDate, hours, mins, ampm};
    }

    @Deprecated
    public static Date converToDate(String dateStr, String formatStr, String timeZone) {

        return DateTimeSupport.convertToDate(dateStr, formatStr, timeZone);
    }

    @Deprecated
    public static String convertToDefaultTimeZone(String dateStr, String timeZone) {
        return convertBetweenTimeZone(dateStr, timeZone, DB_TIMEZONE_ID);
    }

    @Deprecated
    public static String convertFromDefaultTimeZone(String dateStr, String timeZone) {
        return convertBetweenTimeZone(dateStr, DB_TIMEZONE_ID, timeZone);
    }

    @Deprecated
    public static String convertFromDefaultTimeZone(String dateStr, String timeZone, String pattern) {
        return convertBetweenTimeZone(dateStr, DB_TIMEZONE_ID, timeZone, pattern);
    }

    @Deprecated
    public static String convertBetweenTimeZone(String dateStr, String fromTimeZone, String toTimeZone) {
        return convertBetweenTimeZone(dateStr, fromTimeZone, toTimeZone, "yyyy-MM-dd HH:mm:ss");
    }

    @Deprecated
    public static String convertBetweenTimeZone(String dateStr, String fromTimeZone, String toTimeZone, String pattern) {
        if (dateStr == null) {
            return null;
        }
        DateTime dt = DateTimeSupport.convertToDateTime(dateStr, pattern, fromTimeZone);
        return DateTimeSupport.convertToString(dt, pattern, toTimeZone);
    }

    @Deprecated
    public static String convertBetweenTimeZoneAndPattern(String dateStr, String fromTimeZone, String toTimeZone, String fromPattern, String toPattern) {
        if (dateStr == null) {
            return null;
        }
        return DateTimeSupport.convertBetweenTimeZoneAndPattern(dateStr, fromTimeZone, toTimeZone, fromPattern, toPattern);
    }

    @Deprecated
    public static String convertBetweenTimeZone(String dateStr, String fromTimeZone, String toTimeZone, String fromPattern, String toPattern) {
        if (dateStr == null) {
            return null;
        }
        DateTime dateTime = DateTimeSupport.convertToDateTime(dateStr, fromPattern, fromTimeZone);
        return DateTimeSupport.convertToString(dateTime, toPattern, toTimeZone);
    }

    @Deprecated
    public static String getDateInISOFormat(Date date, String timezone) {

        return DateTimeSupport.convertToString(date, "yyyy-MM-dd'T'HH:mm:ss'Z'Z", timezone);
    }

    // Unused
    @Deprecated
    public static String getDateInISOFormat(String dateStr, String pattern, String dateInTimeZone) {
        Date d = converToDate(dateStr, pattern, dateInTimeZone);
        return getDateInISOFormat(d, dateInTimeZone);
    }

    @Deprecated
    public static Date getDateFromString(String date) {
        try {
            return DateTimeSupport.convertToDate(date, "yyyy-MM-dd HH:mm:ss");
        } catch (Exception e) {
            return null;
        }
    }

    @Deprecated
    public static String getTimeFromString(String date, String timeZone) {
        if (date == null || date.trim().length() == 0) {
            return "";
        }

        return DateTimeSupport.convertToString(getDateFromString(date), "hh:mm a", timeZone);
    }

    @Deprecated
    public static String getTimeFromLongString(String dateInLongFormat, String timeZone) {

        if (dateInLongFormat == null || dateInLongFormat.trim().length() == 0) {
            return "";
        }

        return DateTimeSupport.convertToString(getDateFromLongString(dateInLongFormat), "hh:mm a", timeZone);
    }

    @Deprecated
    public static Date getDateFromLongString(String date) {
        try {
            return DateTimeSupport.convertToDate(date, "EEE MMM dd HH:mm:ss zzz yyyy");
        } catch (final Exception e) {
            return null;
        }
    }

    public static String getDateInUTC(String dateStr, Date dateIn) {

		Date dateInput = null;
		String dateStrInUTC = null;

		if (dateIn == null && !GenUtil.isBlankString(dateStr)) {
			dateInput = getDateFromString(dateStr);
		} else {
			dateInput = dateIn;
		}
		if (null != dateInput) {
			dateStrInUTC = convertDateToTimeZone(dateInput, UTC_TIMEZONE_ID, DATE_FORMAT_IN_UTC);
		}
		return dateStrInUTC;
    }

    @Deprecated
    public static String convertDateToTimeZone(Date d, String timeZone, String formatter) {
        return DateTimeSupport.convertToString(d, formatter, timeZone);
    }

    @Deprecated
    public static String convertDate(String date, String fromDateFormat, String toDateFormat) {
        try {
            final DateTime dateTime = DateTimeSupport.convertToDateTime(date, fromDateFormat);
            return DateTimeSupport.convertToString(dateTime, toDateFormat);
        } catch (final Exception e) {
            return null;
        }
    }
    /**
     * @throws ParseException 
     * @comment This method covert the date in desired format.
     * @param Date Format in String
     * @param TimeZone
     * @param Date
     * 
     */
    public static String converDateFormat(String dateFomat,String timeZone,Date date) throws ParseException {
    	SimpleDateFormat formatter = new SimpleDateFormat(dateFomat);
		TimeZone tz = TimeZone.getTimeZone(timeZone);
		formatter.setTimeZone(tz);
		DateTimeFormatter dateTimeformatter = DateTimeFormat.forPattern(dateFomat).withZone(DateTimeZone.forID(timeZone));
		DateTimeFormatter attemptDueDateTimeFormatter = DateTimeFormat.forPattern(DATE_FORMAT).withZone(DateTimeZone.forID("UTC"));
		return dateTimeformatter.parseDateTime(formatter.format(date)).withZone(DateTimeZone.forID(UTC_TIMEZONE_ID)).toString(attemptDueDateTimeFormatter);	
    }
    /**
     * @throws ParseException 
     * @comment This method covert the date into desired format String.
     * @param Date Format in String
     * @param TimeZone
     * @param Date in String
     */
    public static String converDateFormat(String dateFomat,String timeZone,String date) throws ParseException {
		DateTimeFormatter dateTimeformatter = DateTimeFormat.forPattern(dateFomat).withZone(DateTimeZone.forID(timeZone));
		DateTimeFormatter attemptDueDateTimeFormatter = DateTimeFormat.forPattern(DATE_FORMAT).withZone(DateTimeZone.forID("UTC"));
		return dateTimeformatter.parseDateTime(date).withZone(DateTimeZone.forID(UTC_TIMEZONE_ID)).toString(attemptDueDateTimeFormatter);
		
    }
    
    public static java.sql.Timestamp convertToTimeStamp(Date date) {
		return new java.sql.Timestamp(date.getTime());
	}
    
}
