package de.davidartmann.profpedia.utils;

import android.support.v4.util.TimeUtils;
import android.util.LayoutDirection;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Helper class for date handling.
 * Created by david on 17.01.16.
 */
public class DateHelper {

    /**
     * Returns the seconds of the given weekday.
     * @param incomingWeekDay constant from calender of the day.
     * @return seconds of the weekday or 0.
     */
    public static long getSecondsByGivenWeekDay(int incomingWeekDay) {
        //Log.d("DATEHELPER-WEEKDAY", incomingWeekDay+"");
        long returnSeconds = 0;
        // this returns one hour in the future for local time and for UTC the correct time we would need
        //Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Germany/Berlin"), Locale.GERMANY);
        Calendar calendar = Calendar.getInstance();
        int actualDayConst = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        // the fragment is for today
        if (incomingWeekDay == actualDayConst) {
            returnSeconds = calendar.getTimeInMillis() / 1000;
        }
        // we got a fragment of a future day
        else {
            int offset;
            if (incomingWeekDay > actualDayConst) {
                offset = incomingWeekDay - actualDayConst;
            } else {
                offset = 7 - actualDayConst + incomingWeekDay;
            }
            returnSeconds = calendar.getTimeInMillis() / 1000 + 86400 * offset;
        }
        //Log.d("DATEHELPER-RETURNVALUE", returnSeconds+"");
        return returnSeconds;
    }
}
