package com.pd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pd.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author GTY
 */
@Mapper
public interface IUserMapper extends BaseMapper<User> {
}
