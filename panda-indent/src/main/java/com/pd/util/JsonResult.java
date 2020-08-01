package com.pd.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Component
public class JsonResult<T> implements Serializable {
    public static final int success = 200;
    public static final int error = 400;

    /**
     * 操作是否成功
     * 200 成功
     * 400 失败
     */
    private int stateCode;

    /**
     * 操作成功时存储数据
     * 单个数据
     * Json格式
     */
    private T data;

    /**
     * 操作成功时存储数据
     * 数据集合
     * Json格式
     */
    private List<T> dataList;

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    /**
     * 操作日志信息
     */
    private String message;

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
