package com.qh.xuezhimin.week03xzm20181214.data.presenter;

import com.qh.xuezhimin.week03xzm20181214.bean.Result;
import com.qh.xuezhimin.week03xzm20181214.data.core.BasePresenter;
import com.qh.xuezhimin.week03xzm20181214.data.core.DataCall;
import com.qh.xuezhimin.week03xzm20181214.data.model.DataModel;

public class DataPresenter extends BasePresenter {

    private int page = 1;
    private boolean isRefresh = true;

    public DataPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Result getData(Object... args) {
        isRefresh = (Boolean) args[0];//是否需要刷新
        if (isRefresh) {//刷新
            page = 1;
        } else {
            page++;
        }
        Result result = DataModel.goodsList((String) args[1], page + "");//调用网络请求获取数据
        return result;
    }

    public boolean isResresh() {
        return isRefresh;
    }



}
