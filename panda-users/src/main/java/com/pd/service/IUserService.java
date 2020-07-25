package com.pd.service;

import com.pd.pojo.Account;
import com.pd.pojo.User;
import com.pd.result.MessageResult;

/**
 * @author GTY
 */
public interface IUserService {

    /**
     * 新增用户
     * @return
     * @throws Exception
     */
    MessageResult<?> insertUser(User user,String note)throws Exception;

    /**
     * 发送短信
     * @param telephone_number
     * @return
     * @throws Exception
     */
    MessageResult<?> getNote(String telephone_number)throws Exception;

    /**
     * 验证用户输入的电话和验证码是否正确
     * @param phone
     * @param note
     * @return
     * @throws Exception
     */
    MessageResult<?> noteMobileLogin(String phone, String note)throws Exception;

    /**
     * 根据资金账号和密码进行登录
     * @param account
     * @return
     * @throws Exception
     */
    MessageResult<?> findCapitalAndPasswordLogin(Account account)throws Exception;

    /**
     * 根基电话和密码进行登录
     * @param user
     * @return
     * @throws Exception
     */
    MessageResult<?> findPhoneAndPasswordLogin(User user)throws Exception;

}
