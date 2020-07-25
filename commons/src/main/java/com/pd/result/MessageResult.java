package com.pd.result;

import lombok.Data;

import java.util.List;

/**
 * 消息返回类
 * 用于作为功能服务出现异常返回一个状态码，状态码实现携带特定信息
 * @author GTY
 */
@Data
public class MessageResult<T> {
    /**状态码：20000：成功，20001：失败*/
    private String code;

    /**返回的消息*/
    private String msg;

    /**返回的数据集*/
    private List<?> records;

    /**返回的单个数据*/
    private Object data;


}
