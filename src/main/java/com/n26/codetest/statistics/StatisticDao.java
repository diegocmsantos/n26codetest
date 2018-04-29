package com.n26.codetest.statistics;

import com.n26.codetest.transactions.Transaction;

public interface StatisticDao {

    void updateStatistics(Transaction transaction);
    Statistic getStatistics();
    void removeStatistics(Transaction transaction, boolean cacheIsEmpty);

    void clear();
}
