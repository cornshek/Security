package com.pd.client;

import com.pd.pojo.Account;
import com.pd.pojo.Person;
import com.pd.pojo.User;
import com.pd.result.MessageResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author GTY
 * 与用户服务进行连接
 */
@FeignClient(value = "panda-users", path = "/user")
public interface UserServiceClient {

    /**
     * 发送短信
     *
     * @param phone
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sendNote", method = RequestMethod.POST)
    MessageResult<?> sendNote(@RequestParam("phone") String phone) throws Exception;

    /**
     * 通过短信进行验证登录
     *
     * @param phone
     * @param note
     * @return
     * @throws :全部异常
     * @throws Exception
     */
    @PostMapping("/noteMobileLogin")
    MessageResult<?> noteMobileLogin(@RequestParam("phone") String phone, @RequestParam("note") String note) throws Exception;

    /**
     * 通过电话和密码行登录
     *
     * @param user
     * @return
     * @throws :全部异常
     */
    @RequestMapping(value = "/phoneLogin", method = RequestMethod.POST)
    MessageResult<?> phoneLogin(@RequestBody User user) throws Exception;

    /**
     * 根据资金账户和密码登录
     *
     * @param account
     * @return
     * @throws :全部异常
     * @throws Exception
     */
    @RequestMapping(value = "/capitalLogin", method = RequestMethod.POST)
    MessageResult<?> capitalLogin(@RequestBody Account account) throws Exception;

    /**
     * 通过电话和密码注册一个用户
     *
     * @param user
     * @param note
     * @return
     * @throws Exception
     */
    @PostMapping("/register")
    MessageResult<?> regin(@RequestBody User user, @RequestParam("note") String note) throws Exception;

    /**
     * 开户：客户的详细信息
     *
     * @param person
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/regPerson", method = RequestMethod.POST)
    MessageResult<?> insertPerson(@RequestBody Person person) throws Exception;

    /**
     * 开户：客户的卡号
     *
     * @param account
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/regAccount", method = RequestMethod.POST)
    Account insertAccount(@RequestBody Account account) throws Exception;

    /**
     * 校验银行卡信息
     * @param idCardNo
     * @param realName
     * @param tradingAccount
     * @param phone
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/regIdCard", method = RequestMethod.POST)
    int regIdCard(@RequestParam("idCardNo") String idCardNo, @RequestParam("realName") String realName, @RequestParam("tradingAccount") String tradingAccount, @RequestParam("phone") String phone) throws Exception;

}
