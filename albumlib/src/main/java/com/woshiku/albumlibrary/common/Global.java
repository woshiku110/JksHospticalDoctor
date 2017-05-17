package com.woshiku.albumlibrary.common;

/**
 * Created by Administrator on 2016/8/12.
 */
public class Global {
    //默认全部图片
    public static int userSelected = 0;
    //图片最多可以被选择几张
    public static int maxPicsAmount = 5;
    //1单选 2多选（默认）
    public enum ModeChoose{SINGLE,MULTI};
    //默认
    public static ModeChoose modeChoose = ModeChoose.MULTI;
}
