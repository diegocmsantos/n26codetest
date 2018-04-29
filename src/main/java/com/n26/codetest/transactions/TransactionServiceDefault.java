package com.n26.codetest.transactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceDefault implements TransactionService {

    @Autowired
    private TransactionDaoDefault transactionDaoDefault;

    @Override
    public void save(Transaction transaction) {
        transactionDaoDefault.save(transaction);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionDaoDefault.getTransactions();
    }

}
