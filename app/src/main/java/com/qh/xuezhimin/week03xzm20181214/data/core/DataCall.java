package com.qh.xuezhimin.week03xzm20181214.data.core;

import com.qh.xuezhimin.week03xzm20181214.bean.Result;

public interface DataCall<T> {

    void success(T data);

    void fail(Result result);

}