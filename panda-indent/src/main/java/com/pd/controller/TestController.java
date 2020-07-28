package com.pd.controller;

import com.pd.pojo.TbCommissionOrder;
import com.pd.service.ITbCommissionOrderService;
import com.pd.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class TestController {

    @Autowired
    ITbCommissionOrderService tbCommissionOrderService;

    @RequestMapping(value = "/test/{id}", method = GET, produces = "application/json")
    @ResponseBody
    public JsonResult<TbCommissionOrder> test(@PathVariable Integer id) {
        JsonResult<TbCommissionOrder> result = new JsonResult<>();
        TbCommissionOrder tbCommissionOrder = tbCommissionOrderService.getById(id);
        if (tbCommissionOrder == null) {
            result.setStateCode(400);
            result.setMessage("操作失败");
        } else {
            result.setStateCode(200);
            result.setData(tbCommissionOrder);
            result.setMessage("操作成功");
        }

        return result;
    }

    @RequestMapping(value = "/test", method = POST, produces = "application/json")
    @ResponseBody
    public JsonResult<?> testPost(TbCommissionOrder tbCommissionOrder) {
        JsonResult<?> result = new JsonResult<>();
        tbCommissionOrderService.save(tbCommissionOrder);
        result.setStateCode(200);
        result.setMessage("POST操作成功");
        return result;
    }
}
