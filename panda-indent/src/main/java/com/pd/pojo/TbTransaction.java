package com.pd.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author shek
 * @since 2020-07-28
 */
@TableName("TB_TRANSACTION")
public class TbTransaction implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 成交记录流水号
     */
    @TableId("TRANSACTION_NO")
    private String transactionNo;

    /**
     * 资金账号
     */
    @TableField("CAPITAL_ACCOUNT_NUMBER")
    private String capitalAccountNumber;

    /**
     * 股票代码
     */
    @TableField("STOCK_CODE")
    private String stockCode;

    /**
     * 股票代码
     */
    @TableField("STOCK_NAME")
    private String stockName;

    /**
     * 成交时间
     */
    @TableField("TRANSACTION_TIME")
    private LocalDateTime transactionTime;

    /**
     * 成交价格
     */
    @TableField("STOCK_PRICE")
    private String stockPrice;

    /**
     * 成交数量
     */
    @TableField("STOCKS")
    private String stocks;

    /**
     * 成交额
     */
    @TableField("TRANSACTION_AMOUNT")
    private String transactionAmount;

    /**
     * 业务名称
     */
    @TableField("BUSINESS")
    private String business;

    /**
     * 股票交易手续费
     */
    @TableField("TRANSACTION_FEE")
    private String transactionFee;

    /**
     * 费率
     */
    @TableField("RATE")
    private String rate;


    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getCapitalAccountNumber() {
        return capitalAccountNumber;
    }

    public void setCapitalAccountNumber(String capitalAccountNumber) {
        this.capitalAccountNumber = capitalAccountNumber;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(String stockPrice) {
        this.stockPrice = stockPrice;
    }

    public String getStocks() {
        return stocks;
    }

    public void setStocks(String stocks) {
        this.stocks = stocks;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(String transactionFee) {
        this.transactionFee = transactionFee;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "TbTransaction{" +
        "transactionNo=" + transactionNo +
        ", capitalAccountNumber=" + capitalAccountNumber +
        ", stockCode=" + stockCode +
        ", stockName=" + stockName +
        ", transactionTime=" + transactionTime +
        ", stockPrice=" + stockPrice +
        ", stocks=" + stocks +
        ", transactionAmount=" + transactionAmount +
        ", business=" + business +
        ", transactionFee=" + transactionFee +
        ", rate=" + rate +
        "}";
    }
}
