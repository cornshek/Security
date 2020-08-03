package com.pd;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.pd.util.Sessions;
import net.sf.json.JSONArray;
import org.junit.Test;

import java.util.HashMap;

public class Test1 {

    @Test
    public void test()throws Exception{
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FydkCuGP3faFVWDB6Fg", "9zOHiWZqVEwEOtbvIcuNY7UE608za7");
        IAcsClient client = new DefaultAcsClient(profile);

        //构建请求
        CommonRequest request = new CommonRequest();

        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        //自定义参数(手机号,验证码,签名,模板)
        request.putQueryParameter("PhoneNumbers", "18770544902");
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
        System.out.println(data);
        //将验证码存储在session中
        Sessions.setSessionData("note",vcode,5);
    }

}
