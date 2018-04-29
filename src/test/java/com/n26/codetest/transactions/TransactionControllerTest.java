package com.n26.codetest.transactions;

import com.n26.codetest.transactions.TransactionController;
import com.n26.codetest.transactions.TransactionService;
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

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest({ TransactionController.class })
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService service;

    @Test
    public void saveTransactionYoungerThan60SecTest() throws Exception {

        long thirtySecFromNow = System.currentTimeMillis() - 30000;

        MvcResult result = performPostTransaction(thirtySecFromNow);

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }

    @Test
    public void saveTransaction59SecTest() throws Exception {

        long fifthNineSecFromNow = System.currentTimeMillis() - 59000;

        MvcResult result = performPostTransaction(fifthNineSecFromNow);

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }

    @Test
    public void saveTransaction60SecTest() throws Exception {

        long sixtySecFromNow = System.currentTimeMillis() - 60000;

        MvcResult result = performPostTransaction(sixtySecFromNow);

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());

    }

    @Test
    public void saveTransactionOlderThan60SecTest() throws Exception {

        long sixtyOneSecFromNow = System.currentTimeMillis() - 61000;

        MvcResult result = performPostTransaction(sixtyOneSecFromNow);

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());

    }

    @Test
    public void saveTransactionAmountLessThanZeroTest() throws Exception {

        long thirtyOneSecFromNow = System.currentTimeMillis() - 30000;

        String transactionJson = "{\"amount\": -10, \"timestamp\": " + thirtyOneSecFromNow + "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/transactions")
                .accept(MediaType.APPLICATION_JSON)
                .content(transactionJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());

    }

    private MvcResult performPostTransaction(long timestamp) throws Exception {

        String transactionJson = "{\"amount\": 12.3, \"timestamp\": " + timestamp + "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/transactions")
                .accept(MediaType.APPLICATION_JSON)
                .content(transactionJson)
                .contentType(MediaType.APPLICATION_JSON);

        return mockMvc.perform(requestBuilder).andReturn();

    }

}
