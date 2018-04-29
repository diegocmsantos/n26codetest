package com.n26.codetest.transactions;

import com.n26.codetest.transactions.validation.OlderThan;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static java.time.temporal.ChronoUnit.SECONDS;

public class Transaction {

    @NotNull
    @Min(0)
    private BigDecimal amount;

    @OlderThan(duration = 60, unit = SECONDS)
    private long timestamp;

    public Transaction() {}

    public Transaction(BigDecimal amount, long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
