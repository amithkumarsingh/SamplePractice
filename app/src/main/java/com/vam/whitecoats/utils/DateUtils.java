package com.vam.whitecoats.utils;

import android.content.Context;

import com.vam.whitecoats.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    private static long MILLIS_VALUE = 1000;

    public static String longToMessageDate(long dateLong) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateLong);
        String timeString = new SimpleDateFormat("hh:mm a").format(calendar.getTime());
        return timeString;
    }

    public static String longToMessageListHeaderDate(long dateLong) {
        String timeString;
        SimpleDateFormat baseFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateLong);
        String currentDate = baseFormatter.format(new Date());
        String givenDate = baseFormatter.format(calendar.getTime());
        if (currentDate.equals(givenDate)) {
            DateFormat formatter = new SimpleDateFormat("hh:mm a");
            timeString = formatter.format(calendar.getTime());
        } else {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTimeInMillis(dateLong);
            timeString = formatter.format(calendar.getTime());
        }

        return timeString;
    }

    public static String longToMessageListHeaderTimeandDate(long dateLong) {
        String timeString;
        SimpleDateFormat baseFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateLong);
        String currentDate = baseFormatter.format(new Date());
        String givenDate = baseFormatter.format(calendar.getTime());
        if (currentDate.equals(givenDate)) {
            DateFormat formatter = new SimpleDateFormat("hh:mm a");
            timeString = formatter.format(calendar.getTime());
        } else {
            DateFormat formatter = new SimpleDateFormat("hh:mm a  dd/MM/yyyy");
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTimeInMillis(dateLong);
            timeString = formatter.format(calendar.getTime());
        }

        return timeString;
    }

    public static String convertLongtoDate(Long millisec) {
        Long millisec_ = millisec;
        try {
            Date mydate = new Date(millisec_);
            long days = (new Date().getTime() - mydate.getTime()) / 86400000;
            if (days == 0) {
                long hours = (new Date().getTime() - mydate.getTime()) / 3600000;
                if (hours >= 12) {
                    DateFormat formatter = new SimpleDateFormat("dd");
                    Calendar calendarone = Calendar.getInstance();
                    calendarone.setTimeInMillis(mydate.getTime());
                    Calendar calendartwo = Calendar.getInstance();
                    calendartwo.setTimeInMillis(new Date().getTime());
                    int dayone = Integer.parseInt(formatter.format(calendarone.getTime()));
                    int daytwo = Integer.parseInt(formatter.format(calendartwo.getTime()));
                    int daycomparison = daytwo - dayone;
                    if (daycomparison == 0) {
                        return "Today";
                    } else if (daycomparison == 1) {
                        return "Yesterday";
                    }
                } else if (hours > 0 && hours < 12) {
                    return hours + "hr";
                } else if (hours == 0) {
                    long mins = (new Date().getTime() - mydate.getTime()) / 60000;
                    if (mins < 60 && mins > 0) {
                        return mins + "mins";
                    } else if (mins == 0) {
                        long seconds = (new Date().getTime() - mydate.getTime()) / 1000;
                        if (seconds < 60 && seconds > 0) {
                            return seconds + "secs";
                        }
                    }
                }
            } else if (days == 1) {
                DateFormat formatter = new SimpleDateFormat("dd");
                Calendar calendarone = Calendar.getInstance();
                calendarone.setTimeInMillis(mydate.getTime());
                Calendar calendartwo = Calendar.getInstance();
                calendartwo.setTimeInMillis(new Date().getTime());
                int dayone = Integer.parseInt(formatter.format(calendarone.getTime()));
                int daytwo = Integer.parseInt(formatter.format(calendartwo.getTime()));
                int daycomparison = daytwo - dayone;
                if (daycomparison == 1) {
                    return "Yesterday";
                } else {
                    DateFormat Dformatter = new SimpleDateFormat("dd/MM/yyyy");
                    Dformatter.setTimeZone(TimeZone.getTimeZone("GMT"));
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(millisec);
                    return Dformatter.format(calendar.getTime());
                }
            } else {
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(millisec);
                return formatter.format(calendar.getTime());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public static String convertLongtoDates(Long millisec) {
        try {
            DateFormat Dformatter = new SimpleDateFormat("dd/MM/yyyy");
            String timeZone = Calendar.getInstance().getTimeZone().getID();
            Dformatter.setTimeZone(TimeZone.getTimeZone(timeZone));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(millisec);
            return Dformatter.format(calendar.getTime());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public static String getChatHistoryDate(Date time, Context context) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String reportDate = "";
        try {
            time = dateFormat.parse(dateFormat.format(time));
            /**
             * Get Today's date
             */
            Calendar now = Calendar.getInstance();
            Date todayDate = dateFormat.parse(dateFormat.format(now.getTime()));
            /**
             * Get Yesterday's date
             */
            Calendar yesterday = Calendar.getInstance();
            yesterday.add(Calendar.DATE, -1);
            Date yesterdayDate = dateFormat.parse(dateFormat.format(yesterday.getTime()));
            if (time.compareTo(todayDate) == 0) {
                reportDate = context.getString(R.string.label_today);
            } else if (time.compareTo(yesterdayDate) == 0) {
                reportDate = context.getString(R.string.label_yesterday);
            } else {
                reportDate = dateFormat.format(time);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return reportDate;
    }

    public static Date getChatHistoryTime(Date time, Context context) {
        /**
         * Get Today's date
         */
        Date finalTime = null;
        try {
            finalTime = new Date();
            DateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
            String reportTime = formatter.format(time);
            finalTime = formatter.parse(reportTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return finalTime;
    }

    public static String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
        return sdf.format(cal.getTime());
    }

    public static String getCurrentTimeWithTimeZone() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss z");
        String datetime = dateformat.format(c.getTime());
        return datetime;
    }

    /**
     * @param milli
     * @param pattern
     * @return converted date
     */
    public static String parseMillisIntoDate(long milli, String pattern) {
        String formattedDate = "";
        if (milli > 0) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            Date date = new Date(milli);
            formattedDate = dateFormat.format(date);
        }
        return formattedDate;
    }


    /**
     * @param milli
     * @param pattern
     * @return converted date
     */
    public static String parseMillisIntoDateWithDefaultValue(long milli, String pattern) {
        String formattedDate = "";
        if (milli != -19800001) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            Date date = new Date(milli);
            formattedDate = dateFormat.format(date);
        }
        return formattedDate;
    }

    public static boolean compareDates(long date1, long date2) {
        boolean result = false;
        try {
            SimpleDateFormat baseFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date1);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTimeInMillis(date2);
            Date convertedDate1 = baseFormatter.parse(baseFormatter.format(calendar.getTime()));
            Date convertedDate2 = baseFormatter.parse(baseFormatter.format(calendar2.getTime()));
            if (convertedDate1.before(convertedDate2)) {
                result = true; // If start date is before end date.
            } else if (convertedDate1.equals(convertedDate2)) {
                result = true; // If two dates are equal.
            } else {
                result = false; // If start date is after the end date.
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int compareTime(String fromTime, String toTime) {
        int status = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
            Date date1 = sdf.parse(fromTime);
            Date date2 = sdf.parse(toTime);
            if (date1.after(date2)) { //fromTime is after toTime
                status = 1;
            } else if (date1.before(date2)) {//fromTime is before toTime
                status = -1;
            } else if (date1.equals(date2)) {//fromTime is equal with toTime
                status = 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return status;
    }
}