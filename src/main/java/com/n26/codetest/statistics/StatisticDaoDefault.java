package com.n26.codetest.statistics;

import com.n26.codetest.transactions.Transaction;
import com.n26.codetest.transactions.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Repository
public class StatisticDaoDefault implements StatisticDao {

    private final Statistic statistic = new Statistic();

    @Autowired
    private TransactionService transactionService;

    public void updateStatistics() {

        clear();

        List<Transaction> transactionList = transactionService.findAll();

        statistic.setCount(new BigDecimal(transactionList.size()));

        transactionList.forEach(transaction -> {
            // Sum
            statistic.setSum(statistic.getSum().add(transaction.getAmount()));

            // Max
            statistic.setMax(new BigDecimal(Math.max(statistic.getMax().doubleValue(),
                    transaction.getAmount().doubleValue())));

            // Min
            statistic.setMin(statistic.getMin().compareTo(BigDecimal.ZERO) == 0 ? transaction.getAmount() :
                    new BigDecimal(Math.min(statistic.getMin().doubleValue(),
                            transaction.getAmount().doubleValue())));

        });

        if (statistic.getCount().compareTo(BigDecimal.ZERO) > 0) {
            statistic.setAvg(statistic.getSum().divide(statistic.getCount(), 2, RoundingMode.HALF_DOWN));
        }

    }

    @Override
    public void clear() {
        statistic.setMax(BigDecimal.ZERO);
        statistic.setMin(BigDecimal.ZERO);
        statistic.setSum(BigDecimal.ZERO);
        statistic.setCount(BigDecimal.ZERO);
        statistic.setAvg(BigDecimal.ZERO);

    }

    @Override
    public Statistic getStatistics() {
        return statistic;
    }
}
