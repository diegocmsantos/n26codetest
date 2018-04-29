package com.n26.codetest.statistics;

import com.n26.codetest.transactions.Transaction;
import com.n26.codetest.transactions.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class StatisticServiceDefault implements StatisticService {

    @Autowired
    private StatisticDao statisticDao;

    @Override
    public void updateStatistic(Transaction transaction) {
        statisticDao.updateStatistics(transaction);
    }

    @Override
    public void removeStatistic(Transaction transaction, boolean cacheIsEmpty) {
        statisticDao.removeStatistics(transaction, cacheIsEmpty);
    }

    @Override
    public Statistic getStatistic() {
        return statisticDao.getStatistics();
    }

    @Override
    public void clear() {
        statisticDao.clear();
    }

}
