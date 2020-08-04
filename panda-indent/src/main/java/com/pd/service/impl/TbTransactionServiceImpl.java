package com.pd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pd.pojo.TbCommissionOrder;
import com.pd.pojo.TbTransaction;
import com.pd.mapper.TbTransactionMapper;
import com.pd.service.ITbTransactionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shek
 * @since 2020-07-28
 */
@Service
public class TbTransactionServiceImpl extends ServiceImpl<TbTransactionMapper, TbTransaction> implements ITbTransactionService {

    @Autowired
    TbTransactionMapper mapper;

    @Override
    public boolean save(TbTransaction entity) {
        LocalDateTime dateTimeNow = LocalDateTime.now();

        //将当前时间 + 自增长数字(0000~9999) 作为commissionOrderNo
        String transactionNoPrefix = dateTimeNow.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS"));
        String transactionNoSuffix = "0000";
        String transactionNo;
        int existingQuantity = 0;

        String transactionDate = dateTimeNow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


        /*
         * 同一时间插入的订单 transactionNo 会重复
         * 拼接数字解决重复
         * 第一个No默认为 当前时间+0000
         *
         * 1、查询数据库中是否存在同一时间插入的订单
         *      根据 transactionNoPrefix 模糊查询
         * 2、若存在，根据模糊查询返回的数量将 transactionNoSuffix + 1
         *
         */

        //根据CommissionOrderNoPrefix模糊查询数量
        QueryWrapper<TbTransaction> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("TRANSACTION_NO", transactionNoPrefix);
        existingQuantity = mapper.selectCount(queryWrapper);

        //根据existingQuantity设置 transactionNoSuffix
        if (existingQuantity != 0) {
            transactionNoSuffix = String.format("%04d", existingQuantity);
        }

        transactionNo = transactionNoPrefix + transactionNoSuffix;

        entity.setTransactionNo(transactionNo);
        entity.setTransactionTime(transactionDate);

        mapper.insert(entity);
        return true;
    }
}
