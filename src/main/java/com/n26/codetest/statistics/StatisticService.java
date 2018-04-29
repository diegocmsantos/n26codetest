package com.n26.codetest.statistics;

import com.n26.codetest.transactions.Transaction;

public interface StatisticService {

    void updateStatistic(Transaction transaction);

    void removeStatistic(Transaction transaction, boolean cacheIsEmpty);

    Statistic getStatistic();

    void clear();
}
