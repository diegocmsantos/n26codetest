package com.n26.codetest.transactions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionDaoDefaultTest {

    @Autowired
    private TransactionDaoDefault transactionDaoDefault;

    @Autowired
    private Environment env;

    @Before
    public void after() {
        transactionDaoDefault.clearTransactions();
    }

    @Test
    public void saveTest() {

        Transaction transaction = new Transaction(new BigDecimal(10), System.currentTimeMillis());

        transactionDaoDefault.save(transaction);

        assertTrue(transactionDaoDefault.getTransactions().size() == 1);

    }

    @Test
    public void saveAndGetStatisticsAfterExpireTimeTest() throws InterruptedException {

        long fiftyEightSecFromNow = System.currentTimeMillis() - 58000;
        Transaction transaction = new Transaction(new BigDecimal(10), fiftyEightSecFromNow);

        transactionDaoDefault.save(transaction);

        Thread.sleep(3000);

        assertTrue(transactionDaoDefault.getTransactions().size() == 0);

    }

}
