package com.tasconline;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kiwitech on 15/10/18.
 */
public class DateUtil {

    public static long convertStringToDateMili(String date, String format) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(date).getTime();
    }

    public static Date convertStringToDate(String date, String format) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(date);
    }
}
