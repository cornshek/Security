package com.pd.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import java.util.HashMap;
import com.aliyuncs.http.MethodType;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author GTY
 * 发送短信工具类
 */
public class SendNote {


    /**
     * 阿里短信的网关
     *
     * @param phone 电话号码
     * @return
     * @throws Exception
     */
    public NoteData ali(String phone) throws Exception {
        //连接阿里云
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FydkCuGP3faFVWDB6Fg", "9zOHiWZqVEwEOtbvIcuNY7UE608za7");
        IAcsClient client = new DefaultAcsClient(profile);

        //构建请求
        CommonRequest request = new CommonRequest();

        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        //自定义参数(手机号,验证码,签名,模板)
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "知乎互联");
        request.putQueryParameter("TemplateCode", "SMS_197625777");

        //构建一个短信验证码
        String vcode = "";

        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int) (Math.random() * 9);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", vcode);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));
        CommonResponse response = client.getCommonResponse(request);
        String data = response.getData();
        NoteData noteData = new NoteData();
        noteData.setNote(vcode);
        noteData.setResultData(data);
        return noteData;
    }

    /**
     * 互亿无线短信网关
     *
     * @param phone
     * @return
     * @throws Exception
     */
    public NoteData yihu(String phone) throws Exception {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod("http://106.ihuyi.com/webservice/sms.php?method=Submit");
        client.getParams().setContentCharset("GBK");
        method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=GBK");
        //产生一个随机数
        int mobile_code = (int) ((Math.random() * 9 + 1) * 100000);
        /**
         * 查看用户名 登录用户中心->验证码通知短信>产品总览->API接口信息->APIID
         * 查看密码 登录用户中心->验证码通知短信>产品总览->API接口信息->APIKEY
         * 提交短信
         */
        String content = "您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。";
        NameValuePair[] data = {
                new NameValuePair("account", "C82679624"),
                new NameValuePair("password", "cf4526b0b38716e8883b74f3c1d8c4ee"),
                new NameValuePair("mobile", phone),
                new NameValuePair("content", content),
        };

        method.setRequestBody(data);
        //发送请求
        client.executeMethod(method);
        //获得响应体
        String SubmitResult = method.getResponseBodyAsString();
        //将响应体转换成文本
        Document doc = DocumentHelper.parseText(SubmitResult);
        Element root = doc.getRootElement();
        NoteData noteData = new NoteData();
        noteData.setResultData(root);
        noteData.setNote(mobile_code);
        return noteData;
    }

    @lombok.Data
    public class NoteData {
        /**
         * 返回短信的发送成功或者失败的信息（数据值）
         */
        private Object resultData;
        /**
         * 返回验证码
         */
        private Object note;
    }
}
