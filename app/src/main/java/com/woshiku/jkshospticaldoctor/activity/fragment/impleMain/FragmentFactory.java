package com.woshiku.jkshospticaldoctor.activity.fragment.impleMain;

import android.support.v4.app.FragmentActivity;

import com.woshiku.jkshospticaldoctor.activity.fragment.BaseFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/16.
 */
public class FragmentFactory {
    protected static Map<Integer,BaseFragment> map = new HashMap<>();
    public static int count = 5;
    /**
     * @param mActivity 放入FragmentActivity
     * @param pos 创建第几个fragment
     * */
    public static BaseFragment createFragment(FragmentActivity mActivity,int pos){
        BaseFragment baseFragment = map.get(pos);
        if(baseFragment == null){
            switch(pos){
                case 0:
                    baseFragment = new PreorderFragment(mActivity);
                    map.put(pos, baseFragment);
                    break;
                case 1:
                    baseFragment = new HoldDialogFragment(mActivity);
                    map.put(pos, baseFragment);
                    break;
                case 2:
                    baseFragment = new PersonalCenterFragment(mActivity);
                    map.put(pos, baseFragment);
                    break;
            }
        }
        return baseFragment;
    }
    /**
     * @param pos 通过pos拿到相应的fragment
     * */
    public static BaseFragment getFragment(int pos){
        BaseFragment baseFragment = map.get(pos);
        if(baseFragment!=null){
            return baseFragment;
        }
        return null;
    }
    /**
     * @desc 清除fragments
     * */
    public static void clearFragments(){
        map = new HashMap<>();
    }
}
