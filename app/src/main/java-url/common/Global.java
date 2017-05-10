package common;

import com.woshiku.jkshospticaldoctor.activity.domain.LoginReturnData;

/**
 * Created by admin on 2017-04-19.
 */

public class Global {
    public static String baseUrl = "http://123.207.243.224/jfs1.1/";
    public static String imagePath = "http://123.207.243.224/File/filebed/";
    public static String _token = "";
    public static String mainAction = "com.woshiku.mainaction";
    public static LoginReturnData loginReturnData;
    public static final int medicalSearchReurn = 20000;//药品搜索返回页面
    public static final int medicalSearchEnter = 20001;//药品搜索进入页面
    public static final int checkTicketEnter = 20002;//检查单进入界面
    public static final int checkTicketReturn = 20003;//检查单进入界面
    public static final String checkReceAction = "com.woshiku.checkReceDetailActivity";
}
