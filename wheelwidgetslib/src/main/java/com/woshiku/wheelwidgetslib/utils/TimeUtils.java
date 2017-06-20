package com.woshiku.wheelwidgetslib.utils;

import java.util.Calendar;

/**
 * Created by admin on 2017-05-16.
 */

public class TimeUtils {
    public static int getHour(){
        return Calendar.getInstance().get(Calendar.HOUR);
    }
    public static int getMinute(){
        return Calendar.getInstance().get(Calendar.MINUTE);
    }
}
