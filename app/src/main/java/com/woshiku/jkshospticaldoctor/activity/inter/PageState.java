package com.woshiku.jkshospticaldoctor.activity.inter;

/**
 * Created by admin on 2017-04-24.
 */

public abstract class PageState {
    final public static int PageWaiting = 0,PageFail = 1,PageNoData = 2,PageOk = 3;
    protected int pageState = PageOk;//默认数据没有问题
    public int getPageState() {
        return pageState;
    }
    public void setPageState(int pageState) {
        this.pageState = pageState;
    }
}
