package com.woshiku.jkshospticaldoctor.activity.utils;

public class Utils {
        private static long lastClickTime;  
        public static boolean isFastDoubleClick() {  
            long time = System.currentTimeMillis();     
            if ( time - lastClickTime < 500) {     
                return true;     
            }     
            lastClickTime = time;     
            return false;     
        }  
    }  