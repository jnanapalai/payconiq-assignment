package com.payconiq.service.controller;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.jayway.jsonpath.JsonPath;

import com.payconiq.model.Stock;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Integration Test class
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Integration Test case for get all stocks
     *
     * @throws Exception throw exception if any
     */
    @Test
    @Order(1)
    public void getAllStock_Test() throws Exception {
      mockMvc.perform(get("/api/stocks"))
                .andExpect(status().isOk()).andExpect(jsonPath("$",hasSize(4)))
                         .andExpect(jsonPath("$.*",hasSize(4)))
              .andExpect(jsonPath("$..id", is(notNullValue())))
              .andExpect(jsonPath("$..name", is(notNullValue())))
              .andExpect(jsonPath("$..currentPrice", is(notNullValue())))
              .andExpect(jsonPath("$..lastUpdate", is(notNullValue())));
    }

    /**
     * Test case for creating stock
     *
     * @throws Exception
     */
    @Test
    @Order(2)
    public void createStock_Test() throws Exception {
        Stock s=new Stock();
        s.setCurrentPrice(456);
        s.setName("dummy");
        String response=mockMvc.perform(post("/api/stocks").
                contentType("application/json").content(new ObjectMapper().writeValueAsString(s))).
                andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        assertEquals("dummy",JsonPath.read(response,"$.name"));
        assertEquals(456.0,JsonPath.read(response,"$.currentPrice"));


    }

    /**
     *Test case for getting the details of a stock
     *
     * @throws Exception
     */
    @Test
    @Order(3)
    public void getDetails_Stock() throws Exception
    {
   String response=mockMvc.perform(get("/api/stocks/3")).
                andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();
        assertEquals(Integer.valueOf(3),JsonPath.read(response,"$.id"));
        assertEquals("DEMOSTOCK3",JsonPath.read(response,"$.name"));
        assertEquals(54.55,JsonPath.read(response,"$.currentPrice"));

    }

    /**
     * Test case for updating the stock
     *
     * @throws Exception
     */
    @Test
    @Order(4)
    public void updateDetails_Stock() throws Exception
    {
        Stock s=new Stock();
        s.setCurrentPrice(456);
        mockMvc.perform(put("/api/stocks/3")
                .contentType("application/json").content(new ObjectMapper().writeValueAsString(s))).andExpect(status().isOk());
    }

    /**
     * Test case for Deleting the stock
     *
     * @throws Exception
     */
    @Test
    @Order(5)
    public void deleteDetails_stock() throws Exception
    {
        mockMvc.perform(delete("/api/stocks/2")).andExpect(status().isNoContent());
    }
}
