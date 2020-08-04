package com.pd.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pd.pojo.TbAccount;
import com.pd.pojo.TbCommissionOrder;
import com.pd.pojo.TbTransaction;
import com.pd.service.ITbAccountService;
import com.pd.service.ITbCommissionOrderService;
import com.pd.service.ITbTransactionService;
import com.pd.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class TbCommissionOrderController {

    @Autowired
    ITbCommissionOrderService tbCommissionOrderService;
    @Autowired
    ITbTransactionService tbTransactionService;
    @Autowired
    ITbAccountService tbAccountService;

    /**
     * 添加订单
     * POST
     *
     * 添加委托订单后为其发起成交请求，并添加成交记录 -- 此处略过真实交易中”委托成交价格“的流程，只允许按照当前单价交易
     *
     * @param tbCommissionOrder
     * @return
     */
    @RequestMapping(value = "order", method = POST, produces = "application/json")
    public JsonResult<?> addOrder(@RequestBody TbCommissionOrder tbCommissionOrder) {
        JsonResult<?> result = new JsonResult<>();
        boolean saveState = tbCommissionOrderService.save(tbCommissionOrder);
        if (saveState) {
            result.setStateCode(JsonResult.success);
//            result.setMessage("添加订单成功");

            //TODO 此处应发起订单交易请求

            /*
             * 若交易成功
             * 将订单交易状态更新为 ”已成交“
             * 添加成交记录
             */
            int transactionAmount = Integer.parseInt(tbCommissionOrder.getOrderPrice()) * Integer.parseInt(tbCommissionOrder.getStocks());
            TbTransaction tbTransaction = new TbTransaction();
            tbTransaction.setCapitalAccountNumber(tbCommissionOrder.getCapitalAccountNumber());
            tbTransaction.setStockCode(tbCommissionOrder.getStockCode());
            tbTransaction.setStockName(tbCommissionOrder.getStockName());
            tbTransaction.setStockPrice(tbCommissionOrder.getOrderPrice());
            tbTransaction.setStocks(tbCommissionOrder.getStocks());
            tbTransaction.setTransactionAmount(Integer.toString(transactionAmount));
            tbTransaction.setBusiness(tbCommissionOrder.getBusiness());
            tbTransaction.setTransactionFee("交易请求完成后返回");
            tbTransaction.setRate("交易请求完成后返回");

            if (tbTransactionService.save(tbTransaction)) {
                //修改订单交易状态为 ”已成交“
                updateState(tbCommissionOrder, "completion");
                result.setMessage("添加订单成功，订单已成交");
            }

        } else {
            result.setStateCode(JsonResult.error);
            result.setMessage("添加订单失败");
        }
        return result;
    }

    /**
     * 分页查询订单
     * TODO 根据资金账号查询
     * GET
     *
     * 不显示已撤回的委托订单 commissionOrderState = "0"
     *
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "order", method = GET, produces = "application/json")
    public JsonResult<IPage<TbCommissionOrder>> listOrder(HttpSession session, Integer pageNum, Integer pageSize, @RequestParam(required = false) String orderType, @RequestParam(required = false) String orderField) {
        JsonResult<IPage<TbCommissionOrder>> result = new JsonResult<>();

        QueryWrapper<TbCommissionOrder> tbCommissionOrderQueryWrapper = new QueryWrapper<>();
        QueryWrapper<TbAccount> tbAccountQueryWrapper = new QueryWrapper<>();
        boolean orderTypeFlag = true;

        long userId;
        TbAccount tbAccount = new TbAccount();

        /*
         *TODO 通过session获取用户id
         * 通过用户id查询用户资金账号
         */
//        userId = session.getAttribute("");
//        tbAccountQueryWrapper.eq("USER_ID", userId);
//        tbAccount = tbAccountService.getOne(tbAccountQueryWrapper);

        tbCommissionOrderQueryWrapper
                //不显示已撤回的订单
                .ne("COMMISSION_ORDER_STATE", "0");
                //只显示当前用户的订单
//                .eq("CAPITAL_ACCOUNT_NUMBER", "测试资金账户");

        //根据前端传递的orderType orderField设置条件构造器
        //若前端没有传递orderType orderField 的值，则使用默认排序(default分支)
        if (orderType == null) {
            orderType = "asc";
            orderField = "default";
        }
        if (orderType.equals("desc")) {
            orderTypeFlag = false;
        }
        switch (orderField) {
            case "commissionOrderState":
                tbCommissionOrderQueryWrapper.orderBy(true, orderTypeFlag, "COMMISSION_ORDER_STATE");
                break;
            case "orderDate":
                tbCommissionOrderQueryWrapper.orderBy(true, orderTypeFlag, "ORDER_DATE");
                break;
            case "stockCode":
                tbCommissionOrderQueryWrapper.orderBy(true, orderTypeFlag, "STOCK_CODE");
                break;
            case "orderPrice":
                tbCommissionOrderQueryWrapper.orderBy(true, orderTypeFlag, "ORDER_PRICE");
            default:
                //默认根据 COMMISSION_ORDER_NO 升序
                tbCommissionOrderQueryWrapper.orderBy(true, orderTypeFlag, "COMMISSION_ORDER_NO");
        }

        IPage<TbCommissionOrder> page = tbCommissionOrderService.page(new Page<>(pageNum, pageSize), tbCommissionOrderQueryWrapper);

        result.setData(page);
        result.setStateCode(JsonResult.success);
        result.setMessage("分页查询成功 当前为第" + page.getCurrent() + "页");

        return result;
    }

    /**
     * 更新委托订单交易状态
     * ”已撤回“ 用数字0表示已撤回
     * ”已成交“
     * @return
     */
    @RequestMapping(value = "order", method = PUT, produces = "application/json")
    public JsonResult<?> updateState(@RequestBody TbCommissionOrder tbCommissionOrder, String action) {
        JsonResult<?> result = new JsonResult<>();
        System.out.println(action);

        QueryWrapper<TbCommissionOrder> updateWrapper = new QueryWrapper<>();
        updateWrapper.eq("COMMISSION_ORDER_NO", tbCommissionOrder.getCommissionOrderNo());

        //根据参数 action 的值设置 commissionOrderState
        if (action.equals("withdraw")) {
            tbCommissionOrder.setCommissionOrderState("0");
        } else if (action.equals("completion")) {
            tbCommissionOrder.setCommissionOrderState("已成交");
        }

        if (tbCommissionOrderService.update(tbCommissionOrder, updateWrapper)) {
            result.setStateCode(JsonResult.success);
            result.setMessage("订单状态更新操作成功");
            return result;
        }

        result.setStateCode(JsonResult.error);
        result.setMessage("订单状态更新操作失败");

        return result;
    }
}
