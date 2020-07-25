package com.pd.client;

import com.pd.pojo.Account;
import com.pd.pojo.User;
import com.pd.result.MessageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author GTY
 * 与用户服务进行连接
 */
@FeignClient(value = "panda-users",path = "/user")
public interface UserServiceClient {

    /**
     * 发送短信
     * @param phone
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sendNote", method = RequestMethod.POST)
    MessageResult<?> sendNote(@RequestParam("phone") String phone) throws Exception;

    /**
     * 通过短信进行验证登录
     * @param phone
     * @param note
     * @exception :全部异常
     * @return
     * @throws Exception
     */
    @PostMapping("/noteMobileLogin")
    MessageResult<?> noteMobileLogin(@RequestParam("phone") String phone, @RequestParam("note") String note) throws Exception;

    /**
     * 通过电话和密码行登录
     * @param user
     * @exception :全部异常
     * @return
     */
    @RequestMapping(value = "/phoneLogin",method = RequestMethod.POST)
    MessageResult<?> phoneLogin(@RequestBody User user)throws Exception;

    /**
     * 根据资金账户和密码登录
     * @param account
     * @exception :全部异常
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/capitalLogin",method = RequestMethod.POST)
    MessageResult<?> capitalLogin(@RequestBody Account account)throws Exception;

    /**
     * 通过电话和密码注册一个用户
     * @param user
     * @param note
     * @return
     * @throws Exception
     */
    @PostMapping("/register")
    MessageResult<?> regin(@RequestBody User user, @RequestParam("note") String note)throws Exception;

    }
