package com.woshiku.wheelwidgetslib.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017-05-05.
 */

public class MedicalUtils {
    public static List<String> getAllAmount(){
        List<String> mList = new ArrayList<>();
        for(int i=1;i<=99;i++){
            String str = "";
            if(i<10){
                str = "0"+i;
            }else{
                str = i+"";
            }
            mList.add(str);
        }
        return mList;
    }
}
