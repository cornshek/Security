package com.pd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pd.pojo.TbCommissionOrder;
import com.pd.util.JsonStrUtil;
import net.minidev.json.JSONUtil;
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

import java.io.UnsupportedEncodingException;
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
     * 测试分页查询功能
     *
     * @throws Exception
     */
    @Test
    public void pageTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/order?pageNum=1&pageSize=10")
                .accept(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String resultStr = result.getResponse().getContentAsString();

        System.out.println(JsonStrUtil.toPrettyFormat(resultStr));
    }

    /**
     * 测试添加订单功能
     * 同一毫秒内插入订单，改变后缀(0000~9999)
     *
     * @throws Exception
     */
    @Test
    public void testForPost() throws Exception {
        TbCommissionOrder tbCommissionOrder = new TbCommissionOrder();
        tbCommissionOrder.setBusiness("测试");
        tbCommissionOrder.setCommissionOrderState("未成交");
        tbCommissionOrder.setOrderPrice("20000元");
        tbCommissionOrder.setStockCode("000000");
        tbCommissionOrder.setStockName("我是股票");
        tbCommissionOrder.setStocks("10");

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