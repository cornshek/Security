package com.pd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pd.pojo.Account;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author GTY
 */
@Mapper
public interface IAccountMapper extends BaseMapper<Account> {
}
