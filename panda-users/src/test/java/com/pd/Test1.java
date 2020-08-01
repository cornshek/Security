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


    @Test
    public void demo(){
        //"data":[{"file_name":"51信用卡","file_value":[{"index_value":4472,"index_name":"笔数"},{"index_value":9923,"index_name":"金额"}]},{"file_name":"量化派","file_value":[{"index_value":8303,"index_name":"笔数"},{"index_value":9659,"index_name":"金额"}]},{"file_name":"携程","file_value":[{"index_value":1504,"index_name":"笔数"},{"index_value":5067,"index_name":"金额"}]}]
       String js =  "[\n" +
               "　　[\"smith\",1001,\"clerck\",7788,2000.00,200.0]\n" +
               "　　[\"smith\",1001,\"clerck\",7788,2000.00,200.0]\n" +
               "　　[\"smith\",1001,\"clerck\",7788,2000.00,200.0]\n" +
               "]";
        JSONArray array = JSONArray.fromObject(js);
        for (int i = 0; i < array.size(); i++) {
            net.sf.json.JSONObject jsonObject = array.getJSONObject(i);
            System.out.println(jsonObject.toString());
        }
    }
}
