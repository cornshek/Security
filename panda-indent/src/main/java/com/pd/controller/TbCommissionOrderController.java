package com.pd.controller;

import com.pd.pojo.TbCommissionOrder;
import com.pd.service.ITbCommissionOrderService;
import com.pd.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class TbCommissionOrderController {

    @Autowired
    ITbCommissionOrderService tbCommissionOrderService;

    /**
     * 添加订单
     * POST
     * 将当前时间设置为订单ID yyyy-MM-dd hh:mm:ss:SS
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

}
