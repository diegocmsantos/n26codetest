package com.n26.codetest.statistics;

import com.n26.codetest.transactions.Transaction;
import com.n26.codetest.transactions.TransactionDaoDefault;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticDaoTest {

    @Autowired
    private StatisticDao statisticDao;

    @Autowired
    private TransactionDaoDefault transactionDao;

    @Before
    public void clearTransactions() {
        transactionDao.clearTransactions();
    }

    @Test
    public void statisticsTest() {

        Transaction transaction = new Transaction(new BigDecimal(10), System.currentTimeMillis());
        transactionDao.save(transaction);

        transaction = new Transaction(new BigDecimal(11), System.currentTimeMillis());
        transactionDao.save(transaction);

        transaction = new Transaction(new BigDecimal(12), System.currentTimeMillis());
        transactionDao.save(transaction);

        Statistic statistic = statisticDao.getStatistics();

        assertTrue(statistic.getCount().compareTo(new BigDecimal(3)) == 0);
        assertTrue(statistic.getMax().compareTo(new BigDecimal(12)) == 0);
        assertTrue(statistic.getMin().compareTo(BigDecimal.TEN) == 0);
        assertTrue(statistic.getSum().compareTo(new BigDecimal(33)) == 0);
        assertTrue(statistic.getAvg().compareTo(new BigDecimal(11)) == 0);

    }

    @Test
    public void saveTwoTransactionsWaitSaveNewOneAndGetStatisticsTest() throws InterruptedException {

        // This transaction will expire in 5 seconds
        Transaction transaction = new Transaction(new BigDecimal(10), System.currentTimeMillis() - 55000);
        transactionDao.save(transaction);

        Statistic statistic = statisticDao.getStatistics();

        // Confirming that transaction still valid, that means in cache yet
        assertEquals(1, transactionDao.getTransactions().size());
        assertEquals(new BigDecimal(1), statistic.getCount());
        assertTrue(statistic.getMax().compareTo(new BigDecimal(10)) == 0);
        assertEquals(BigDecimal.TEN, statistic.getMin());
        assertTrue(statistic.getSum().compareTo(new BigDecimal(10)) == 0);
        assertTrue(statistic.getAvg().compareTo(new BigDecimal(10)) == 0);

        // Waiting 2 seconds
        Thread.sleep(2000);

        // Transaction should still valid, again, in cache yet
        statistic = statisticDao.getStatistics();
        assertEquals(1, transactionDao.getTransactions().size());
        assertEquals(new BigDecimal(1), statistic.getCount());
        assertTrue(statistic.getMax().compareTo(new BigDecimal(10)) == 0);
        assertTrue(statistic.getMin().compareTo(BigDecimal.TEN) == 0);
        assertEquals(new BigDecimal(10), statistic.getSum());
        assertTrue(statistic.getAvg().compareTo(new BigDecimal(10)) == 0);

        // Waiting 3,5 more seconds completes 60,5 seconds
        // Transaction should be expired and no longer exists in cache
        Thread.sleep(3500);
        statistic = statisticDao.getStatistics();
        assertEquals(0, transactionDao.getTransactions().size());
        assertEquals(new BigDecimal(0), statistic.getCount());
        assertTrue(statistic.getMax().compareTo(new BigDecimal(0)) == 0);
        assertTrue(statistic.getMin().compareTo(new BigDecimal(0)) == 0);
        assertTrue(statistic.getSum().compareTo(new BigDecimal(0)) == 0);
        assertTrue(statistic.getAvg().compareTo(new BigDecimal(0)) == 0);


        // Create two new transactions

        // The first one has 20 seconds left to expires
        transaction = new Transaction(new BigDecimal(11), System.currentTimeMillis() - 40000);
        transactionDao.save(transaction);
        // And the second one has 2 seconds left to expires
        transaction = new Transaction(new BigDecimal(12), System.currentTimeMillis() - 58000);
        transactionDao.save(transaction);

        // Waiting 5 seconds
        Thread.sleep(5000);

        // The second transaction should be expired and the first should be in cache
        statistic = statisticDao.getStatistics();
        assertEquals(1, transactionDao.getTransactions().size());
        assertTrue(statistic.getCount().compareTo(new BigDecimal(1)) == 0);
        assertTrue(statistic.getMax().compareTo(new BigDecimal(11)) == 0);
        assertTrue(statistic.getMin().compareTo(new BigDecimal(11)) == 0);
        assertTrue(statistic.getSum().compareTo(new BigDecimal(11)) == 0);
        assertTrue(statistic.getAvg().compareTo(new BigDecimal(11)) == 0);

    }

    @Test
    public void creatingAOldTransaction() {

        // This transaction has 61 seconds of life
        Transaction transaction = new Transaction(new BigDecimal(10), System.currentTimeMillis() - 61000);
        transactionDao.save(transaction);

        Statistic statistic = statisticDao.getStatistics();

        // Confirming that transaction is not in cache
        assertEquals(0, transactionDao.getTransactions().size());
        assertTrue(statistic.getCount().compareTo(new BigDecimal(0)) == 0);
        assertTrue(statistic.getMax().compareTo(new BigDecimal(0)) == 0);
        assertTrue(statistic.getMin().compareTo(BigDecimal.ZERO) == 0);
        assertTrue(statistic.getSum().compareTo(new BigDecimal(0)) == 0);
        assertTrue(statistic.getAvg().compareTo(new BigDecimal(0)) == 0);

    }

}
