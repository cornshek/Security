package com.pd.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pd.pojo.TbCommissionOrder;
import com.pd.service.ITbCommissionOrderService;
import com.pd.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class TbCommissionOrderController {

    @Autowired
    ITbCommissionOrderService tbCommissionOrderService;

    /**
     * 添加订单
     * POST
     *
     * @param tbCommissionOrder
     * @return
     */
    @RequestMapping(value = "/order", method = POST, produces = "application/json")
    public JsonResult<?> addOrder(@RequestBody TbCommissionOrder tbCommissionOrder) {
        JsonResult<?> result = new JsonResult<>();
        boolean saveState = tbCommissionOrderService.save(tbCommissionOrder);
        if (saveState) {
            result.setStateCode(JsonResult.success);
            result.setMessage("添加订单成功");
        } else {
            result.setStateCode(JsonResult.error);
            result.setMessage("添加订单失败");
        }
        return result;
    }

    /**
     * 分页查询订单
     * GET
     *
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "order", method = GET, produces = "application/json")
    public JsonResult<IPage<TbCommissionOrder>> listOrder(Integer pageNum, Integer pageSize, @RequestParam(required = false) String orderType, @RequestParam(required = false) String orderField) {
        JsonResult<IPage<TbCommissionOrder>> result = new JsonResult<>();
        QueryWrapper<TbCommissionOrder> queryWrapper = new QueryWrapper<>();
        boolean orderTypeFlag = true;

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
                queryWrapper.orderBy(true, orderTypeFlag, "COMMISSION_ORDER_STATE");
                break;
            case "orderDate":
                queryWrapper.orderBy(true, orderTypeFlag, "ORDER_DATE");
                break;
            case "stockCode":
                queryWrapper.orderBy(true, orderTypeFlag, "STOCK_CODE");
                break;
            default:
                //默认根据 COMMISSION_ORDER_NO 升序
                queryWrapper.orderBy(true, orderTypeFlag, "COMMISSION_ORDER_NO");
        }

        IPage<TbCommissionOrder> page = tbCommissionOrderService.page(new Page<>(pageNum, pageSize), queryWrapper);

        result.setData(page);
        result.setStateCode(JsonResult.success);
        result.setMessage("分页查询成功 当前为第" + page.getCurrent() + "页");

        return result;
    }

}
