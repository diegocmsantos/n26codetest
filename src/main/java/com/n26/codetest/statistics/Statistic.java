package com.n26.codetest.statistics;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Statistic {

    private BigDecimal sum = BigDecimal.ZERO;
    private BigDecimal avg = BigDecimal.ZERO;
    private BigDecimal max = BigDecimal.ZERO;
    private BigDecimal min = BigDecimal.ZERO;
    private BigDecimal count = BigDecimal.ZERO;

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getAvg() {
        return avg;
    }

    public void setAvg(BigDecimal avg) {
        this.avg = avg;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }
}
