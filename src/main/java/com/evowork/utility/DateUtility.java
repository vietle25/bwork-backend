package com.evowork.utility;

import io.netty.util.internal.StringUtil;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author tuannd
 * @date 17/12/2016
 * @since 1.0
 */
public class DateUtility {

    public static final String DATE_FORMAT_SQL = "yyyy/MM/dd";
    public static final String FORMAT_DATE = "dd/MM/yyyy";
    public static final String FORMAT_DATE_SQL = "yyyy-MM-dd";
    public static final String FORMAT_MONTH_OF_YEAR = "yyyy-MM";
    public static final String FORMAT_DATE_TIME_ZONE = "yyyy-MM-dd HH:mm:ss.SSSZZZ";
    public static final String FORMAT_DATE_WITHOUT_TIME_ZONE = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE_FOR_FILE = "dd_MM_yyyy_HH_mm_ss";
    public static final String FORMAT_TIME_SECONDS = "HH:mm:ss";
    public static final String FORMAT_TIME = "HH:mm";
    public static final String FORMAT_HOUR = "HH";
    public static final String FORMAT_MINUTES = "mm";
    public static final String FORMAT_SECONDS = "ss";
    public static final String FORMAT_TIME_SECOND = "HH:mm:ss.SSSZZZ";
    public static final String FORMAT_DATE_TIME = FORMAT_TIME + " " + FORMAT_DATE;
    public static final String FORMAT_DATE_TIME_FULL = FORMAT_DATE + " " + FORMAT_TIME_SECOND;
    public static final String FORMAT_DATE_MONTH = "dd/MM";
    public static final String FORMAT_YEAR = "yy";
    public static final String FORMAT_MONTH = "MM";
    public static final SimpleDateFormat defaultDateFormat = new SimpleDateFormat(FORMAT_DATE_TIME);
    public static final String FORMAT_IST = "EEE MMM dd HH:mm:ss z yyyy";

    /**
     * The Constant SECOND.
     */
    public static final long SECOND = 1000;
    /**
     * The Constant MINUTE.
     */
    public static final long MINUTE = SECOND * 60;
    /**
     * The Constant HOUR.
     */
    public static final long HOUR = MINUTE * 60;

    public static String now() {
        return formatDate(Calendar.getInstance().getTimeInMillis());
    }

    public static String formatNow(String format) {
        return formatDate(Calendar.getInstance().getTimeInMillis(), format);
    }

    public static String formatDate(long milliseconds) {
        return formatDateTime(new Date(milliseconds), FORMAT_DATE);
    }

    public static String formatDate(long milliseconds, String format) {
        return formatDateTime(new Date(milliseconds), format);
    }

    public static String formatTime(long milliseconds) {
        return formatDateTime(new Date(milliseconds), FORMAT_TIME);
    }

    public static String formatTime(long milliseconds, String format) {
        return formatDateTime(new Date(milliseconds), format);
    }

    public static String formatDateTime(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date valueOf(String date, String format) throws ParseException {
        if (StringUtility.isEmpty(format)) {
            format = FORMAT_DATE;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(date);
    }

    public static Date valueOf(String date) throws ParseException {
        return valueOf(date, null);
    }

    /**
     * Format date
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * Format date
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDateWithTimeZone(Date date, String format, String timeZone) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        return sdf.format(date);
    }

    /**
     * Format String date with format
     *
     * @param date1
     * @param format1
     * @param format2
     * @return
     */
    public static String convertDateWithFormat(String date1, String format1, String format2) {
        String t = "";
        try {
            if (!StringUtil.isNullOrEmpty(date1)) {
                Date date = new SimpleDateFormat(format1).parse(date1);
                t = formatDateTime(date, format2);
            }
        } catch (ParseException e) {
            System.out.print(e);
        }
        return t;
    }

    /**
     * Convert string to timestamp
     *
     * @param strDate
     * @return
     */
    public static Timestamp convertStringToTimestamp(String strDate) {
        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat(FORMAT_DATE_TIME_ZONE);
            // you can change format of date
            Date date = formatter.parse(strDate);
            java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());
            return timeStampDate;
        } catch (ParseException e) {
            System.out.println("Exception :" + e);
            return null;
        }
    }

    /**
     * Add days
     *
     * @param day
     * @param numDay
     * @return
     */
    public static String addDays(String day, Integer numDay) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_SQL);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(day));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, numDay);  // number of days to add
        return sdf.format(c.getTime());  // dt is now the new date
    }

    /**
     * Convert string to date
     *
     * @param strDate
     * @return
     */
    public static java.sql.Date convertStringToDate(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE_SQL);
        Date parsed = null;
        try {
            parsed = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date date = new java.sql.Date(parsed.getTime());
        return date;
    }
}
