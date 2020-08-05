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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
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
    @ResponseBody
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
    @ResponseBody
    public JsonResult<IPage<TbTransaction>> listTransaction(HttpSession session, Integer pageNum, Integer pageSize, @RequestParam(required = false) String orderType, @RequestParam(required = false) String orderField) {
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

    /**
     * 根据股票代码、资金账户查询成交记录
     * 跳转 transactionTimeline 页面
     * @return
     */
    @RequestMapping(value = "transactionByStockCode", method = GET, produces = "application/json")
    public String listTransactionByStockCode(HttpServletRequest request, String stockCode, String capitalAccountNumber) {
        List<TbTransaction> result = new ArrayList<>();
        QueryWrapper<TbTransaction> queryWrapper = new QueryWrapper<>();

        //首次交易时间和最后一次交易时间
        String FirstTransactionTime;
        String LastTransactionTime;
        //交易总额
        double allTransactionAmount = 0;
        //手续费总额
        double allTransactionFee = 0;

        queryWrapper
                .eq("STOCK_CODE", stockCode)
                //根据成交时间降序
                .orderBy(true, false, "TRANSACTION_TIME");

        //仅为测试时使用
        if (capitalAccountNumber.equals("undefined")) {
            queryWrapper.isNull("CAPITAL_ACCOUNT_NUMBER");
        } else {
            queryWrapper.eq("CAPITAL_ACCOUNT_NUMBER", capitalAccountNumber);
        }

        System.out.println(stockCode);
        System.out.println(capitalAccountNumber);

        result = (tbTransactionService.list(queryWrapper));
//        System.out.println(result.getDataList());

        FirstTransactionTime = result.get(0).getTransactionTime();
        LastTransactionTime = result.get(result.size() - 1).getTransactionTime();

        //计算成交总额和手续费总额
        for (TbTransaction tbTransaction : result) {
            if (tbTransaction.getBusiness().equals("卖出")) {
                allTransactionAmount += Double.parseDouble(tbTransaction.getTransactionAmount());
                System.out.println("卖出：" + allTransactionAmount);
                allTransactionFee += Double.parseDouble(tbTransaction.getTransactionFee());
                System.out.println("手续费: " + allTransactionFee);
            } else if (tbTransaction.getBusiness().equals("买入")) {
                allTransactionAmount -= Double.parseDouble(tbTransaction.getTransactionAmount());
                System.out.println("买入：" + allTransactionAmount);
                allTransactionFee += Double.parseDouble(tbTransaction.getTransactionFee());
                System.out.println("手续费: " + allTransactionFee);
            }
        }

        request.setAttribute("dataList", result);
        request.setAttribute("stockCode", stockCode);
        request.setAttribute("dataListSize", result.size());
        request.setAttribute("timespan",LastTransactionTime + " - " + FirstTransactionTime);
        request.setAttribute("allTransactionAmount", allTransactionAmount);
        request.setAttribute("allTransactionFee", allTransactionFee);

        return "transactionTimeline";
    }
}
