package com.pd.controller;

import com.github.pagehelper.PageInfo;
import com.pd.pojo.Account;
import com.pd.pojo.User;
import com.pd.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对用户的增、删、查、改层
 *
 * @author GTY
 */
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private IUserService userService;

    /**
     * 管理员审核
     *
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public User verify(@RequestBody User user) throws Exception {
        return userService.verify(user);
    }

    /**
     * 管理员查看所有用户
     *
     * @param page,limit
     * @return list
     * @throws Exception
     */
    @RequestMapping(value = "/checkUsers", method = RequestMethod.GET)
    public Map<String, Object> checkUserList(Integer page, Integer pageSize) throws Exception {
        PageInfo<User> checkUserList = userService.checkUserList(page, pageSize);
        List<User> list = checkUserList.getList();
        Map<String, Object> map1 = new HashMap<>(10);
        map1.put("code", 0);
        map1.put("msg", "执行成功！");
        map1.put("data", list);
        map1.put("count", checkUserList.getTotal());
        return map1;
    }

    /**
     * 管理员查看单个用户具体信息
     *
     * @param id
     * @return User
     * @throws Exception
     */
    @RequestMapping(value = "/checkUser", method = RequestMethod.POST)
    public User checkUsers(Integer id) throws Exception {
        User user = userService.checkUser(id);
        return user;
    }

    /**
     * 管理员修改用户账号状态
     *
     * @param account
     * @throws Exception
     */
    @RequestMapping(value = "/modifyStatus", method = RequestMethod.POST)
    public int modifyStatus(Account account) throws Exception {
        int flag2 = userService.modifyStatus(account);
        return flag2;
    }
}
