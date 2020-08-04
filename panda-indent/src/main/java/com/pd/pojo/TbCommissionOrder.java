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
 * @since 2020-07-28
 */
@TableName("TB_COMMISSION_ORDER")
public class TbCommissionOrder implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 委托交易流水号
     */
    @TableId("COMMISSION_ORDER_NO")
    private String commissionOrderNo;

    /**
     * 委托交易状态
     */
    @TableField("COMMISSION_ORDER_STATE")
    private String commissionOrderState;

    /**
     * 资金账户
     */
    @TableField("CAPITAL_ACCOUNT_NUMBER")
    private String capitalAccountNumber;

    /**
     * 下单时间
     */
    @TableField("ORDER_DATE")
    private String orderDate;

    /**
     * 股票代码
     */
    @TableField("STOCK_CODE")
    private String stockCode;

    /**
     * 股票名字
     */
    @TableField("STOCK_NAME")
    private String stockName;

    /**
     * 业务名称
     */
    @TableField("BUSINESS")
    private String business;

    /**
     * 股票数量
     */
    @TableField("STOCKS")
    private String stocks;

    /**
     * 下单价格
     */
    @TableField("ORDER_PRICE")
    private String orderPrice;


    public String getCommissionOrderNo() {
        return commissionOrderNo;
    }

    public void setCommissionOrderNo(String commissionOrderNo) {
        this.commissionOrderNo = commissionOrderNo;
    }

    public String getCommissionOrderState() {
        return commissionOrderState;
    }

    public void setCommissionOrderState(String commissionOrderState) {
        this.commissionOrderState = commissionOrderState;
    }

    public String getCapitalAccountNumber() {
        return capitalAccountNumber;
    }

    public void setCapitalAccountNumber(String capitalAccountNumber) {
        this.capitalAccountNumber = capitalAccountNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
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

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getStocks() {
        return stocks;
    }

    public void setStocks(String stocks) {
        this.stocks = stocks;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    @Override
    public String toString() {
        return "TbCommissionOrder{" +
        "commissionOrderNo=" + commissionOrderNo +
        ", commissionOrderState=" + commissionOrderState +
        ", capitalAccountNumber=" + capitalAccountNumber +
        ", orderDate=" + orderDate +
        ", stockCode=" + stockCode +
        ", stockName=" + stockName +
        ", business=" + business +
        ", stocks=" + stocks +
        ", orderPrice=" + orderPrice +
        "}";
    }
}
