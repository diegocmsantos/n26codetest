package com.n26.codetest.transactions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionCacheTest {

    @Autowired
    private TransactionCache transactionCache;

    @Before
    public void before() {
        transactionCache.clear();
    }

    @Test
    public void addTransactionTest() {
        long thirtySecFromNow = System.currentTimeMillis() - 30000;
        Transaction transaction = new Transaction(BigDecimal.valueOf(10), thirtySecFromNow);

        transactionCache.add("1", transaction);

        assertEquals(1, transactionCache.getCache().size());

    }

    @Test
    public void addTransactionAndWait4SecondsTest() throws InterruptedException {
        long thirtySecFromNow = System.currentTimeMillis() - 50000;
        Transaction transaction = new Transaction(BigDecimal.valueOf(10), thirtySecFromNow);

        transactionCache.add("1", transaction);

        assertEquals(1, transactionCache.getCache().size());

        Thread.sleep(4000);

        assertEquals(1, transactionCache.getCache().size());

    }

    @Test
    public void addTransactionAndWait6SecondsTest() throws InterruptedException {
        long thirtySecFromNow = System.currentTimeMillis() - 55000;
        Transaction transaction = new Transaction(BigDecimal.valueOf(10), thirtySecFromNow);

        transactionCache.add("1", transaction);

        assertEquals(1, transactionCache.getCache().size());

        Thread.sleep(6000);

        assertEquals(0, transactionCache.getCache().size());

    }

    @Test
    public void addOldTransactionTest() {
        long thirtySecFromNow = System.currentTimeMillis() - 60000;
        Transaction transaction = new Transaction(BigDecimal.valueOf(10), thirtySecFromNow);
        transactionCache.add("1", transaction);
        assertEquals(0, transactionCache.getCache().size());

    }

    @Test
    public void addThreeTransactionsTest() {
        long now = System.currentTimeMillis();
        Transaction transaction = new Transaction(BigDecimal.valueOf(10), now);
        transactionCache.add("1", transaction);

        long thirtySecFromNow = System.currentTimeMillis() - 30000;
        transaction = new Transaction(BigDecimal.valueOf(10), thirtySecFromNow);
        transactionCache.add("2", transaction);

        long fiftyFiveSecFromNow = System.currentTimeMillis() - 55000;
        transaction = new Transaction(BigDecimal.valueOf(10), fiftyFiveSecFromNow);
        transactionCache.add("3", transaction);

        assertEquals(3, transactionCache.getCache().size());

    }

}
