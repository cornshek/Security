package com.pd.util;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author GTY
 * 银行卡认证接口
 */
public class BankCard {

    /**
     * 用到的HTTP工具包：okhttp 3.13.1
     * @param appCode  AppCode码
     * @param url  第三方身份验证网站
     * @param params :
     *        Map<String, String> params = new HashMap<>();
     *         params.put("idcard", 身份证);
     *         params.put("name", 姓名);
     *         params.put("bankcard", 卡号);
     *         params.put("mobile", 电话);
     *  "返回状态码" + response.code() + ",message:" + response.message());
     *   所有数据：response.body().string();
     */
    public static int get(String appCode, String url, Map<String, String> params) throws IOException {
        url = url + buildRequestUrl(params);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).addHeader("Authorization", "APPCODE " + appCode).build();
        Response response = client.newCall(request).execute();
        int code = response.code();
        return code;
    }

    public static String buildRequestUrl(Map<String, String> params) {
        StringBuilder url = new StringBuilder("?");
        Iterator<String> it = params.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            url.append(key).append("=").append(params.get(key)).append("&");
        }
        return url.toString().substring(0, url.length() - 1);
    }
}
