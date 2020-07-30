package com.pd.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TbCommissionOrderServiceImplTest {

    @Test
    public void test01() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
        System.out.println(simpleDateFormat.format(date));
    }
}