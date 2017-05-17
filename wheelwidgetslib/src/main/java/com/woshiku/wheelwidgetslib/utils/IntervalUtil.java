package com.woshiku.wheelwidgetslib.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017-04-27.
 */

public class IntervalUtil {
    public static List<String> getHours(){
        List<String> mList = new ArrayList<>();
        for(int i=0;i<=23;i++){
            String str;
            if(i<10){
                str = "0"+i;
            }else{
                str = i+"";
            }
            mList.add(str+"");
        }
        return mList;
    }
    public static List<String> getMinutes(){
        List<String> mList = new ArrayList<>();
        for(int i=0;i<=59;i++){
            String str;
            if(i<10){
                str = "0"+i;
            }else{
                str = i+"";
            }
            mList.add(str+"");
        }
        return mList;
    }
}
