package com.n26.codetest.statistics;

public interface StatisticDao {

    void updateStatistics();
    Statistic getStatistics();

    void clear();
}
