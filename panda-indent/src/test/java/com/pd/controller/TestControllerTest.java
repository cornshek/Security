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
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestControllerTest {

    @Autowired
    TestController testController;

    private MockMvc mockMvc;

    @Before
    public void setMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(testController).build();
    }

    @Test
    public void testForPost() throws Exception {
        TbCommissionOrder tbCommissionOrder = new TbCommissionOrder();
        tbCommissionOrder.setBusiness("测试");


        Gson gson = new Gson();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/test")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(gson.toJson(tbCommissionOrder));
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString() );
    }
}