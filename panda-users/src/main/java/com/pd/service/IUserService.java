package com.pd.service;

import com.github.pagehelper.PageInfo;
import com.pd.pojo.Account;
import com.pd.pojo.Person;
import com.pd.pojo.User;
import com.pd.result.MessageResult;

/**
 * @author GTY
 */
public interface IUserService {

    /**
     * 新增用户
     *
     * @param user
     * @param note
     * @return
     * @throws Exception
     */
    MessageResult<?> insertUser(User user, String note) throws Exception;

    /**
     * 发送短信
     *
     * @param telephone_number
     * @return
     * @throws Exception
     */
    MessageResult<?> getNote(String telephone_number) throws Exception;

    /**
     * 验证用户输入的电话和验证码是否正确
     *
     * @param phone
     * @param note
     * @return
     * @throws Exception
     */
    MessageResult<?> noteMobileLogin(String phone, String note) throws Exception;

    /**
     * 根据资金账号和密码进行登录
     *
     * @param account
     * @return
     * @throws Exception
     */
    MessageResult<?> findCapitalAndPasswordLogin(Account account) throws Exception;

    /**
     * 根据电话和密码进行登录
     *
     * @param user
     * @return
     * @throws Exception
     */
    MessageResult<?> findPhoneAndPasswordLogin(User user) throws Exception;

    /**
     * 管理员审核
     *
     * @param user
     * @return user
     * @throws Exception
     */
    User verify(User user) throws Exception;

    /**
     * 管理员查看所有用户
     *
     * @param page
     * @param pageSize
     * @return list
     * @throws Exception
     */
    PageInfo<User> checkUserList(Integer page, Integer pageSize) throws Exception;

    /**
     * 管理员查看单个用户具体信息
     *
     * @param userId
     * @return
     * @throws Exception
     */
    User checkUser(Integer userId) throws Exception;

    /**
     * 管理员修改用户账号状态
     * 更新账户表（TB_ACCOUNT）的IS_ACCOUNT状态字段
     *
     * @param account
     * @return int
     * @throws Exception
     */
    int modifyStatus(Account account) throws Exception;


    /**
     * 银行卡信息核实
     * @param idcard
     * @param name
     * @param bankcard
     * @param mobile
     * @throws Exception
     * @return
     */
    int checkIdCard(String idcard,String name,String bankcard,String mobile) throws Exception;

    /**
     * 身份证图片认证
     * @param cardImagePath  身份证的国徽面图片路径
     * @param humanFaceImagePath 身份证的人脸面图片路径
     * @return
     * @throws Exception
     */
    String checkIdCardImage(String cardImagePath,String humanFaceImagePath)throws Exception;

    /**
     * 添加用户
     * @param person
     * @return
     * @throws Exception
     */
    MessageResult<?> insertPerson(Person person)throws Exception;

    /**
     * 添加账户信息，开户卡
     * @param account
     * @return
     * @throws Exception
     */
    Account insertAccount(Account account)throws Exception;
}
