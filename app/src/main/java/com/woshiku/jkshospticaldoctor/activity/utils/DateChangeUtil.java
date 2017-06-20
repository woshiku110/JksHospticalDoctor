package com.woshiku.jkshospticaldoctor.activity.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017-05-15.
 * 日期转换工具
 */

public class DateChangeUtil {

    public static List<String> getShowDate(List<String> dateList){
        List<String> validList = new ArrayList<>();
        for(String dateStr:dateList){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
            Date dd = StrToDate(dateStr);
            if(sdf.format(dd).equals(sdf.format(new Date()))){//当天日期
                validList.add(sdf.format(dd));
            }
            if(dd.after(new Date())){//如果日期是今天之前的
                validList.add(sdf.format(dd));
            }
        }
        /*LogUtil.print("valid date:"+validList.toString());*/
        return validList;
    }

    /**
     * 字符串转换成日期
     * @param str
     * @return date
     */
    private static Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getUploadDate(String dd){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        Date date = null;
        try {
            date = sdf.parse(dd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdfOne = new SimpleDateFormat("yyyy-MM-dd");
        return sdfOne.format(date);
    }
}
