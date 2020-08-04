package com.pd.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class TbTransactionController {

    @Autowired
    ITbTransactionService tbTransactionService;
    @Autowired
    ITbAccountService tbAccountService;

    /**
     * 添加成交记录
     * POST
     *
     * @param tbTransaction
     * @return
     */
    @RequestMapping(value = "transaction", method = POST, produces = "application/json")
    public JsonResult<?> addOrder(@RequestBody TbTransaction tbTransaction) {
        JsonResult<?> result = new JsonResult<>();
        boolean saveState = tbTransactionService.save(tbTransaction);
        if (saveState) {
            result.setStateCode(JsonResult.success);
            result.setMessage("添加成交记录成功");
        } else {
            result.setStateCode(JsonResult.error);
            result.setMessage("添加成交记录失败");
        }
        return result;
    }

    /**
     * 分页查询成交记录
     * TODO 根据资金账号查询
     * GET
     *
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "transaction", method = GET, produces = "application/json")
    public JsonResult<IPage<TbTransaction>> listOrder(HttpSession session, Integer pageNum, Integer pageSize, @RequestParam(required = false) String orderType, @RequestParam(required = false) String orderField) {
        JsonResult<IPage<TbTransaction>> result = new JsonResult<>();

        QueryWrapper<TbTransaction> tbTransactionQueryWrapper = new QueryWrapper<>();
        QueryWrapper<TbAccount> tbAccountQueryWrapper = new QueryWrapper<>();
        boolean orderTypeFlag = true;

        long userId;
        TbAccount tbAccount = new TbAccount();

        /*
         *TODO 通过session获取用户id
         * 通过用户id查询用户资金账号
         */
//        userId  = session.getAttribute("");
//        tbAccountQueryWrapper.eq("USER_ID", userId);
//        tbAccount = tbAccountService.getOne(tbAccountQueryWrapper);
//        tbTransactionQueryWrapper.eq("CAPITAL_ACCOUNT_NUMBER", tbAccount.getCapitalAccountNumber());

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
            case "stockPrice":
                tbTransactionQueryWrapper.orderBy(true, orderTypeFlag, "STOCK_PRICE");
                break;
            case "transactionTime":
                tbTransactionQueryWrapper.orderBy(true, orderTypeFlag, "TRANSACTION_TIME");
                break;
            case "stockCode":
                tbTransactionQueryWrapper.orderBy(true, orderTypeFlag, "STOCK_CODE");
                break;
            case "transactionAmount":
                tbTransactionQueryWrapper.orderBy(true, orderTypeFlag, "TRANSACTION_AMOUNT");
            case "transactionFee":
                tbTransactionQueryWrapper.orderBy(true, orderTypeFlag, "TRANSACTION_FEE");
            default:
                //默认根据 TRANSACTION_NO 升序
                tbTransactionQueryWrapper.orderBy(true, orderTypeFlag, "TRANSACTION_NO");
        }

        IPage<TbTransaction> page = tbTransactionService.page(new Page<>(pageNum, pageSize), tbTransactionQueryWrapper);

        result.setData(page);
        result.setStateCode(JsonResult.success);
        result.setMessage("分页查询成功 当前为第" + page.getCurrent() + "页");

        return result;
    }

//    /**
//     * 将委托订单状态更新为 “已撤回”
//     * @return
//     */
//    @RequestMapping(value = "order", method = PUT, produces = "application/json")
//    public JsonResult<?> withdrawOrder(@RequestBody TbCommissionOrder tbCommissionOrder) {
//        System.out.println(tbCommissionOrder);
//        JsonResult<?> result = new JsonResult<>();
//
//        QueryWrapper<TbCommissionOrder> updateWrapper = new QueryWrapper<>();
//        updateWrapper.eq("COMMISSION_ORDER_NO", tbCommissionOrder.getCommissionOrderNo());
//
//        tbCommissionOrder.setCommissionOrderState("0");
//
//        if (tbCommissionOrderService.update(tbCommissionOrder, updateWrapper)) {
//            result.setStateCode(JsonResult.success);
//            result.setMessage("订单撤回操作成功");
//            return result;
//        }
//
//        result.setStateCode(JsonResult.error);
//        result.setMessage("订单撤回操作失败");
//
//        return result;
//    }
}
