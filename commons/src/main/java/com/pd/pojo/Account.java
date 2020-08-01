package com.pd.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author GTY
 * 用户资金账户表
 */
@Data
@TableName("TB_ACCOUNT")
@KeySequence("ACCOUNTIDAUTO")
public class Account implements Serializable {
    private static final long serialVersionUID = -7452752663272788223L;
    /**账户id*/
    @TableId(value = "account_id",type = IdType.INPUT)
    private int accountId;
    /**用户ID*/
    private int userId;
    /**沪地址*/
    private String accountAddress;
    /**开户状态*/
    private String isAccount;
    /**交易账户*/
    private String tradingAccount;
    /**交易密码*/
    private String tradingPassword;
    /**资金账户*/
    private String capitalAccountNumber;
    /**资金账户密码*/
    private String capitalPassword;
    /**营业厅*/
    private String salesDepartment;
    /**资金账户审核状态*/
    private String capitalAccountState;
    /**风险评估等级*/
    private String eal;
}
