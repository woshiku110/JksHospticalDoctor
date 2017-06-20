package com.woshiku.wheelwidgetslib.utils;

import android.util.Log;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2016/12/21.
 */
public class CommonUtil {
    final static int start = 1950;
    final static int end = 2050;
    public static boolean isLast(int yearIndex){
        if(yearIndex==(end-start)){
            Log.e("lookkat","year"+yearIndex);
            return true;
        }else{
            return false;
        }
    }
    //默认1970到2037
    public static List<String> getYear(){
        List<String> list = new ArrayList<>();
        int cha = end - start;
        if(end>start){
            for(int i=0;i<cha;i++){
                list.add((start+i)+"年");
            }
        }
        //list.add("");
        //list.add("");
        /*list.add("");
        list.add("");*/
        //list.add("");
        return list;
    }
    public static List<String> getMonth(){
        List<String> list = new ArrayList<>();
        for(int i=0;i<12;i++){
            list.add((i+1)+"月");
        }
        return list;
    }
    public static List<String> getDay(int year,int month){
        List<String> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,month-1);
        int maxDate = cal.getActualMaximum(Calendar.DATE);
        for(int i=0;i<maxDate;i++){
            list.add((i+1)+"日");
        }
        return list;
    }
    public static List<String> getHour(){
        List<String> list = new ArrayList<>();
        String str;
        for(int i=0;i<24;i++){
            if(i<10){
                str ="0"+i;
            }else{
                str = i+"";
            }
            list.add(str);
        }
        return list;
    }
    public static List<String> getFen(){
        List<String> list = new ArrayList<>();
        String str;
        for(int i=0;i<60;i++){
            if(i<10){
                str ="0"+i;
            }else{
                str = i+"";
            }
            list.add(str);
        }
        return list;
    }
    public static int getMaxDay(int year,int month){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,month-1);
        int maxDate = cal.getActualMaximum(Calendar.DATE);
        return maxDate;
    }
    public static int[]getTime(){
        int[] time = {0,0,0,0,0};
        Calendar now = Calendar.getInstance();
        time[0] = now.get(Calendar.YEAR);
        time[1] = (now.get(Calendar.MONTH) + 1);
        time[2] = now.get(Calendar.DAY_OF_MONTH);
        time[3] = now.get(Calendar.HOUR_OF_DAY);
        time[4] = now.get(Calendar.MINUTE);
        return time;
    }

    public static int getYearIndex(int year){
        String str = year+"年";
        int index = 0;
        List<String> list = getYear();
        for(int i=0;i<list.size();i++){
            if(list.get(i).equals(str)){
                index = i;
                return index;
            }
        }
        return index;
    }
    public static int getMonthIndex(int month){
        String str = month+"月";
        int index = 0;
        List<String> list = getMonth();
        for(int i=0;i<list.size();i++){
            if(list.get(i).equals(str)){
                index = i;
                return index;
            }
        }
        return index;
    }

    public static int getDayIndex(int year,int month,int day){
        String str = day+"日";
        int index = 0;
        List<String> list = getDay(year, month);
        for(int i=0;i<list.size();i++){
            if(list.get(i).equals(str)){
                index = i;
                return index;
            }
        }
        return index;
    }
    public static int getYearThrIndex(int index){
        return start+index;
    }
    public static int getMonthThrIndex(int index){
        return index+1;
    }
    public static int getDayThrIndex(int index){
        return index+1;
    }
    public static String getWeek(int yearIndex,int monthIndex,int dayIndex){
        String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        Calendar cal = Calendar.getInstance();
        int year = getYearThrIndex(yearIndex);
        int month = getMonthThrIndex(monthIndex);
        int day = getDayThrIndex(dayIndex);
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,month-1);
        cal.set(Calendar.DAY_OF_MONTH,day);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }
        return weeks[week_index];
    }
    public static String getDefaultWeek(int year,int month,int day){
        String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,month-1);
        cal.set(Calendar.DAY_OF_MONTH,day);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }
        Log.e("week",year+"\t"+month+"\t"+day);
        return weeks[week_index];
    }
    public static String[] getAllTime(int yearIndex,int monthIndex,int dayIndex,int hourIndex,int fenIndex,boolean isDefault){
        String []date = {"","","","",""};
        String year = start+yearIndex+"";
        String month = (monthIndex+1)+"";
        String day;
        if (isDefault){
            day = (dayIndex-1)+"";
        }else{
            day = (dayIndex+1)+"";
        }
        String hour = hourIndex+"";
        String fen = fenIndex+"";
        date[0] = year;
        date[1] = month;
        date[2] = day;
        date[3] = hour;
        date[4] = fen;
        return date;
    }
}
