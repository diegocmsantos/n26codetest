package com.n26.codetest.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticServiceDefault implements StatisticService {

    @Autowired
    private StatisticDao statisticDao;

    @Override
    public void updateStatistic() {
        statisticDao.updateStatistics();
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
