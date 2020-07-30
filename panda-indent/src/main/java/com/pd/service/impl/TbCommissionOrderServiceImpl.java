package com.pd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pd.pojo.TbCommissionOrder;
import com.pd.mapper.TbCommissionOrderMapper;
import com.pd.service.ITbCommissionOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shek
 * @since 2020-07-28
 */
@Service
public class TbCommissionOrderServiceImpl extends ServiceImpl<TbCommissionOrderMapper, TbCommissionOrder> implements ITbCommissionOrderService {

    @Autowired
    TbCommissionOrderMapper mapper;

    @Override
    public boolean save(TbCommissionOrder entity) {
        LocalDateTime dateTimeNow = LocalDateTime.now();

        //将当前时间 + 自增长数字(0000~9999) 作为commissionOrderNo
        String commissionOrderNoPrefix = dateTimeNow.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS"));
        String commissionOrderNoSuffix = "0000";
        String commissionOrderNo;
        int existingQuantity = 0;

        String OrderDate = dateTimeNow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


        /*
         * 同一时间插入的订单CommissionOrderNo会重复
         * 拼接数字解决重复
         * 第一个No默认为 当前时间+0000
         *
         * 1、查询数据库中是否存在同一时间插入的订单
         *      根据CommissionOrderNoPrefix模糊查询
         * 2、若存在，根据模糊查询返回的数量将 commissionOrderNoSuffix + 1
         *
         */

        //根据CommissionOrderNoPrefix模糊查询数量
        QueryWrapper<TbCommissionOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("COMMISSION_ORDER_NO", commissionOrderNoPrefix);
        existingQuantity = mapper.selectCount(queryWrapper);

        //根据existingQuantity设置 commissionOrderNoSuffix
        if (existingQuantity != 0) {
            commissionOrderNoSuffix = String.format("%04d", existingQuantity);
        }

        commissionOrderNo = commissionOrderNoPrefix + commissionOrderNoSuffix;

        entity.setCommissionOrderNo(commissionOrderNo);
        entity.setOrderDate(OrderDate);

        mapper.insert(entity);
        return true;
    }
}
