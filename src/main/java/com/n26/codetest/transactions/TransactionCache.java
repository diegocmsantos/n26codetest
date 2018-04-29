package com.n26.codetest.transactions;

import java.util.List;

public interface TransactionCache {

    void add(String key, Transaction value);

    void remove(String key);

    Object get(String key);

    void clear();

    long size();

    List<Transaction> getCache();

    void setEXPIRE_TIME(long EXPIRE_TIME);
}
