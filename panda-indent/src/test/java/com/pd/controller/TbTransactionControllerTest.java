package com.pd.controller;

import com.google.gson.Gson;
import com.pd.pojo.TbCommissionOrder;
import com.pd.pojo.TbTransaction;
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

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TbTransactionControllerTest {

    @Autowired
    TbTransactionController tbTransactionController;

    private MockMvc mockMvc;

    @Before
    public void setMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(tbTransactionController).build();
    }

    /**
     * 测试添加成交记录功能
     * 同一毫秒内插入订单，改变后缀(0000~9999)
     *
     * @throws Exception
     */
    @Test
    public void testForPost() throws Exception {
        TbTransaction tbTransaction = new TbTransaction();
        tbTransaction.setStockCode("111111");
        tbTransaction.setStockName("我是股票名称");
        tbTransaction.setStockPrice("我是价格");
        tbTransaction.setStocks("我是数量");
        tbTransaction.setTransactionAmount("十个亿");
        tbTransaction.setBusiness("买入");
        tbTransaction.setTransactionFee("九个亿");
        tbTransaction.setRate("200%");

        Gson gson = new Gson();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/transaction")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(gson.toJson(tbTransaction));
        for (int i = 0; i < 200; i++) {
            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            System.out.println(result.getResponse().getContentAsString() );
        }
    }

}