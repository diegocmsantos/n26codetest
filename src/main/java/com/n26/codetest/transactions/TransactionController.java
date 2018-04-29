package com.n26.codetest.transactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody Transaction transaction) {

        LocalDateTime date =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(transaction.getTimestamp()), ZoneId.systemDefault());
        System.out.println(date);

        transactionService.save(transaction);
    }

    @GetMapping
    public List<Transaction> getAll() {
        return transactionService.findAll();
    }

}
