package com.pd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pd.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * @author GTY
 */
@Mapper
public interface IUserMapper extends BaseMapper<User> {


    /**
     * 管理员查看所有用户
     * @return
     * @throws Exception
     * @exception :异常
     */
    List<User> checkUserList()throws Exception;

    /**
     * 管理员查看单个用户具体信息
     * @param id
     * @return
     * @throws Exception
     * @exception :异常
     */
    User selectByIdUser(Integer id)throws Exception;
}
