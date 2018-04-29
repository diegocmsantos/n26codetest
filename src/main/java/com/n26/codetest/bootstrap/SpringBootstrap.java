package com.n26.codetest.bootstrap;

import com.n26.codetest.transactions.Transaction;
import com.n26.codetest.transactions.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

//@Component
public class SpringBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private TransactionService transactionService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        Transaction transaction;
//        for (int i = 0; i < 5; i++) {
//            transaction = new Transaction();
//            transaction.setAmount(new BigDecimal(new Random().nextInt(50)));
//            transaction.setTimestamp(System.currentTimeMillis() - (i * 8000));
//            transactionService.save(transaction);
//        }

        transaction = new Transaction();
        transaction.setAmount(new BigDecimal(5));
        transaction.setTimestamp(System.currentTimeMillis() - 55000);
        transactionService.save(transaction);

        transaction = new Transaction();
        transaction.setAmount(new BigDecimal(40));
        transaction.setTimestamp(System.currentTimeMillis() - 40000);
        transactionService.save(transaction);

        transaction = new Transaction();
        transaction.setAmount(new BigDecimal(24));
        transaction.setTimestamp(System.currentTimeMillis() - 30000);
        transactionService.save(transaction);

        transaction = new Transaction();
        transaction.setAmount(new BigDecimal(8));
        transaction.setTimestamp(System.currentTimeMillis() - 35000);
        transactionService.save(transaction);

    }

}
