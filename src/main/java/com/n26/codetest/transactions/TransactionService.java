package com.n26.codetest.transactions;

import java.util.List;

public interface TransactionService {
    void save(Transaction transaction);

    List<Transaction> findAll();

}
