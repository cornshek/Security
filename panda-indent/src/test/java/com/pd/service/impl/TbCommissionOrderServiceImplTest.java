package com.pd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pd.pojo.TbCommissionOrder;
import com.pd.service.ITbCommissionOrderService;
import com.pd.util.JsonStrUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TbCommissionOrderServiceImplTest {
    @Autowired
    ITbCommissionOrderService tbCommissionOrderService;

    @Test
    public void test01() {
        QueryWrapper<TbCommissionOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, true, "COMMISSION_ORDER_NO");


        System.out.println(tbCommissionOrderService.page(new Page<>(1, 10), queryWrapper));
    }
}