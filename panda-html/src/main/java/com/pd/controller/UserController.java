package com.pd.controller;

import com.pd.client.UserServiceClient;
import com.pd.pojo.Account;
import com.pd.pojo.Person;
import com.pd.pojo.User;
import com.pd.result.MessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 用户功能层
 *
 * @author GTY
 */
@RestController
public class UserController {
    @Autowired
    private HttpSession session;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private HttpServletRequest request;

    @PostMapping("/registerUserSetPhoneAndPwd")
    public MessageResult<?> registerUser(String phone, String note, String pwd) throws Exception {
        User user = new User();
        user.setTelephoneNumber(phone);
        user.setPassword(pwd);
        MessageResult<?> regin = userServiceClient.regin(user, note);
        return regin;
    }

    @PostMapping("/sendNote")
    public MessageResult<?> sendNote(String phone) throws Exception {
        MessageResult<?> messageResult = userServiceClient.sendNote(phone);
        return messageResult;
    }

    @PostMapping("/capiatilOrPhoneAndPasswordLogin")
    public MessageResult<?> phoneAndPasswordLogin(String phone, String password) throws Exception {
        String regex = "[0-9]{11}";
        int phoneLen = 11;
        //是手机号就通过手机号和密码登录，否则通过资金账户和密码登录
        if (phone.matches(regex) && phone.length() == phoneLen) {
            User user = new User();
            user.setTelephoneNumber(phone);
            user.setPassword(password);
            System.out.println(session.getId());
            MessageResult<?> messageResult = userServiceClient.phoneLogin(user);
            System.out.println(session.getAttribute("user"));
            return messageResult;
        } else {
            Account account = new Account();
            account.setCapitalAccountNumber(phone);
            account.setCapitalPassword(password);
            MessageResult<?> messageResult = userServiceClient.capitalLogin(account);
            return messageResult;
        }
    }

    /**
     * 文件上传
     * @param upload
     * @return
     * @throws IOException
     */
    @PostMapping("/fileupload")
    public Map<String,Object> upload(@RequestParam("file") MultipartFile upload) throws IOException {
        System.out.println("springmvc方式文件上传。。。");
        //使用fileupload组件完成文件上传
//        String path = request.getSession().getServletContext().getRealPath("/uploads/");
//        System.out.println("获取项目发布的一个文件夹路径：" + path);
        String path = "H:\\img";
        //判断，该路径是否存在
        File file = new File(path);
        if (!file.exists()) {
            //创建文件夹
            file.mkdirs();
        }
        // 说明上传文件项
        // 获取上传文件的名称
        String filename = upload.getOriginalFilename();
        // 将文件的名称设置为唯一值,uuid
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        filename = uuid + "_" + filename;
        // 完成文件上传
        upload.transferTo(new File(path, filename));
        System.out.println("文件保存的路径：" + path + filename);
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "上传成功");
        map.put("data", path + "\\" + filename);
        map.put("count", 1);
        return map;
    }

    /**
     * 用户开户之，详细信息
     * produces = "application/json;charset=utf-8"
     */
    @RequestMapping(value = "/regPerson",method = RequestMethod.POST)
    public Person regPerson(Person person){
        return person;
    }

}
