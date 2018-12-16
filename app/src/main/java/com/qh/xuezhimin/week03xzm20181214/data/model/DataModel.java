package com.qh.xuezhimin.week03xzm20181214.data.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qh.xuezhimin.week03xzm20181214.bean.Data;
import com.qh.xuezhimin.week03xzm20181214.bean.Result;
import com.qh.xuezhimin.week03xzm20181214.http.OkHttpUtils;

import java.lang.reflect.Type;
import java.util.List;

public class DataModel {

    public static Result goodsList(final String name, final String page) {

        String resultString = OkHttpUtils.get("http://www.zhaoapi.cn/product/searchProducts?keywords=" + name + "&page=" + page + "");

        try {
            Gson gson = new Gson();

            Type type = new TypeToken<Result<List<Data>>>() {
            }.getType();

            Result result = gson.fromJson(resultString, type);

            return result;
        } catch (Exception e) {

        }
        Result result = new Result();
        result.setCode(-1);
        result.setMsg("数据解析异常");
        return result;
    }


}
