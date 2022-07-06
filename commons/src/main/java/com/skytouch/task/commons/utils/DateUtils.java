package com.skytouch.task.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class with methods used for handling common Date related tasks.
 *
 * @author Waldo Terry
 */
public class DateUtils {

    //String Date pattern to use.
    private static final String JSON_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(JSON_DATE_PATTERN);

    /**
     * Serializes a <code>Date</code> object to the <code>yyyy-MM-dd HH:mm:ss</code> defined in this class.
     *
     * @param date The object to serialize.
     * @return A String in the <code>yyyy-MM-dd HH:mm:ss</code> format, equivalent to the received date.
     */
    public static String toDateString(Date date) {
        return FORMATTER.format(date);
    }

    /**
     * Attempts to read  date from a given string using the pattern defined in this class.
     *
     * @param date String that must conform to the pattern <code>yyyy-MM-dd HH:mm:ss</code>
     * @return A <code>Date</code> object equivalent to the initial string.
     * @throws ParseException If the received String does not match the pattern <code>yyyy-MM-dd HH:mm:ss</code>
     */
    public static Date parseDate(String date) throws ParseException {
        return FORMATTER.parse(date);
    }
}
