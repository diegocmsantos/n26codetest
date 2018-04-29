package com.n26.codetest.statistics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest({StatisticController.class})
public class StatisticControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatisticService service;

    @Test
    public void getStatisticsTest() throws Exception {

        Statistic statistic = new Statistic();
        statistic.setAvg(BigDecimal.TEN);
        statistic.setCount(BigDecimal.ONE);
        statistic.setMax(BigDecimal.TEN);
        statistic.setMin(BigDecimal.TEN);
        statistic.setSum(BigDecimal.TEN);

        when(service.getStatistic()).thenReturn(statistic);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/statistics")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        String responseJSON = "{\"sum\":10,\"avg\":10,\"max\":10,\"min\":10,\"count\":1}";

        assertEquals(responseJSON, response.getContentAsString());

        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    public void getStatisticsWhenThereIsNoTransactionsTest() throws Exception {

        Statistic statistic = new Statistic();

        when(service.getStatistic()).thenReturn(statistic);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/statistics")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        String responseJSON = "{\"sum\":0,\"avg\":0,\"max\":0,\"min\":0,\"count\":0}";

        assertEquals(responseJSON, response.getContentAsString());

        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

}
