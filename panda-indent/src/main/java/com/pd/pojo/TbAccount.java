package com.pd.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author shek
 * @since 2020-08-04
 */
@TableName("TB_ACCOUNT")
public class TbAccount implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户ID
     */
    @TableField("USER_ID")
    private Long userId;

    /**
     * 上海/深圳/创业版

     */
    @TableField("ACCOUNT_ADDRESS")
    private String accountAddress;

    /**
     * 是否开户
     */
    @TableField("IS_ACCOUNT")
    private String isAccount;

    /**
     * 交易账户
     */
    @TableField("TRADING_ACCOUNT")
    private String tradingAccount;

    /**
     * 交易密码
     */
    @TableField("TRADING_PASSWORD")
    private String tradingPassword;

    /**
     * 资金账户
     */
    @TableField("CAPITAL_ACCOUNT_NUMBER")
    private String capitalAccountNumber;

    /**
     * 资金账户密码
     */
    @TableField("CAPITAL_PASSWORD")
    private String capitalPassword;

    /**
     * 营业厅
     */
    @TableField("SALES_DEPARTMENT")
    private String salesDepartment;

    /**
     * 资金账号状态
     */
    @TableField("CAPITAL_ACCOUNT_STATE")
    private String capitalAccountState;

    /**
     * 账户ID
     */
    @TableId("ACCOUNT_ID")
    private Long accountId;

    /**
     * 风险评估等级
     */
    @TableField("EAL")
    private String eal;

    /**
     * 姓名
     */
    @TableField("NAME")
    private String name;

    /**
     * 卡类型
     */
    @TableField("CARD_TYPE")
    private String cardType;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress;
    }

    public String getIsAccount() {
        return isAccount;
    }

    public void setIsAccount(String isAccount) {
        this.isAccount = isAccount;
    }

    public String getTradingAccount() {
        return tradingAccount;
    }

    public void setTradingAccount(String tradingAccount) {
        this.tradingAccount = tradingAccount;
    }

    public String getTradingPassword() {
        return tradingPassword;
    }

    public void setTradingPassword(String tradingPassword) {
        this.tradingPassword = tradingPassword;
    }

    public String getCapitalAccountNumber() {
        return capitalAccountNumber;
    }

    public void setCapitalAccountNumber(String capitalAccountNumber) {
        this.capitalAccountNumber = capitalAccountNumber;
    }

    public String getCapitalPassword() {
        return capitalPassword;
    }

    public void setCapitalPassword(String capitalPassword) {
        this.capitalPassword = capitalPassword;
    }

    public String getSalesDepartment() {
        return salesDepartment;
    }

    public void setSalesDepartment(String salesDepartment) {
        this.salesDepartment = salesDepartment;
    }

    public String getCapitalAccountState() {
        return capitalAccountState;
    }

    public void setCapitalAccountState(String capitalAccountState) {
        this.capitalAccountState = capitalAccountState;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getEal() {
        return eal;
    }

    public void setEal(String eal) {
        this.eal = eal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        return "TbAccount{" +
        "userId=" + userId +
        ", accountAddress=" + accountAddress +
        ", isAccount=" + isAccount +
        ", tradingAccount=" + tradingAccount +
        ", tradingPassword=" + tradingPassword +
        ", capitalAccountNumber=" + capitalAccountNumber +
        ", capitalPassword=" + capitalPassword +
        ", salesDepartment=" + salesDepartment +
        ", capitalAccountState=" + capitalAccountState +
        ", accountId=" + accountId +
        ", eal=" + eal +
        ", name=" + name +
        ", cardType=" + cardType +
        "}";
    }
}
