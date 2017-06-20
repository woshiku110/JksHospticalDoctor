package com.woshiku.wheelwidgetslib.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2017/2/8.
 */
public class YmdUtil {
    /**
     * 得到当前时间
     * */
    public static int[] getTime(){
        int []time = {0,0,0};
        Calendar now = Calendar.getInstance();
        time[0] = now.get(Calendar.YEAR);
        time[1] = (now.get(Calendar.MONTH) + 1);
        time[2] = now.get(Calendar.DAY_OF_MONTH);
        return time;
    }
    /**
     * 拿到年的集合
     * **/
    public static List<String> getYearList(){
        int start = 1900;
        int end = 2100;
        List<String> strList = new ArrayList<>();
        for(int i= start;i<end;i++){
            strList.add(i+" "+"年");
        }
        return strList;
    }
    /**
     * 拿到月份的集合
     * */
    public static List<String> getMonthList(){
        List<String> strList = new ArrayList<>();
        for(int i=1;i<13;i++){
            String str = "";
            if(i<10){
                    str += " ";
                    str += i+" 月";
            }else{
                str += i+" 月";
            }
            strList.add(str);
        }
        return strList;
    }
    public static List<String> getDayList(int year,int month){
        List<String> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,month-1);
        int maxDate = cal.getActualMaximum(Calendar.DATE);
        for(int i=1;i<=maxDate;i++){
            String str = "";
            if(i<10){
                str += " ";
                str += i+" 日";
            }else{
                str += i+" 日";
            }
            list.add(str);
        }
        return list;
    }
    public static int getStartTime(){
        return 1900;
    }
}
