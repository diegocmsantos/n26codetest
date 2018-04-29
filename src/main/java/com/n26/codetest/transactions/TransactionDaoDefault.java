package com.n26.codetest.transactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionDaoDefault {

    private long key;

    @Autowired
    private TransactionCache transactionCache;

    public void save(Transaction transaction) {
        transactionCache.add(String.valueOf(key++), transaction);
    }

    public List<Transaction> getTransactions() {
        return transactionCache.getCache();
    }

    public void clearTransactions() {
        transactionCache.clear();
    }
}
