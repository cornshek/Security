package com.pd.controller;

import com.google.gson.Gson;
import com.pd.pojo.TbCommissionOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TbCommissionOrderControllerTest {

    @Autowired
    TbCommissionOrderController tbCommissionOrderController;

    private MockMvc mockMvc;

    @Before
    public void setMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(tbCommissionOrderController).build();
    }

    @Test
    public void testNumber() {

    }

    /**
     * 测试添加订单功能
     * 同一毫秒内插入订单，改变后缀(0000~9999)
     * @throws Exception
     */
    @Test
    public void testForPost() throws Exception {
        TbCommissionOrder tbCommissionOrder = new TbCommissionOrder();
        tbCommissionOrder.setBusiness("测试");


        Gson gson = new Gson();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/order")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(gson.toJson(tbCommissionOrder));
        for (int i = 0; i < 200; i++) {
            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            System.out.println(result.getResponse().getContentAsString() );
        }

    }
}