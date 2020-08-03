package com.pd.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.Date;

/**
 * @author GTY
 * 用户基本信息
 */
@Data
@TableName("TB_USERS")
@KeySequence(value = "USERID_AUTO")
public class User implements Serializable {

    private static final long serialVersionUID = -8792374780414173902L;
    /**用户ID*/
    @TableId(value = "USER_ID",type = IdType.INPUT)
    private Integer userId;
    /**用户名*/
    private String userName;
    /**电话号码*/
    private String telephoneNumber;
    /**密码*/
    private String password;
    /**创建时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    /**实名状态*/
    private String rnaState;
    /**教育背景*/
    private String eductionState;
    /**账户所属地状态*/
    private String accountState;
    /**验证回访状态*/
    private String returnvisitState;
    /**开户状态*/
    private String openAccountState;
    /**三方存款状态*/
    private String tcsTate;
    /**提交状态*/
    private String submitState;
    /**管理员ID*/
    private String administratorId;
    /**开户表：Account.class  ,当数据库中没有该表，插件自动忽略*/
    @TableField(exist = false)
    private Account account;
}
