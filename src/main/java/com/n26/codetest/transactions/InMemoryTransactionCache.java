package com.n26.codetest.transactions;

import com.google.common.primitives.Longs;
import com.n26.codetest.statistics.StatisticService;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Scope("singleton")
public class InMemoryTransactionCache implements TransactionCache {

    @Value("${application_expire_time}")
    private long EXPIRE_TIME;

    private final ConcurrentHashMap<String, SoftReference<Transaction>> cache = new ConcurrentHashMap<>();
    private final DelayQueue<DelayedCacheObject> cleaningUpQueue = new DelayQueue<>();

    @Autowired
    private StatisticService statisticService;

    public InMemoryTransactionCache() {
        Thread cleanerThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    DelayedCacheObject delayedCacheObject = cleaningUpQueue.take();
                    cache.remove(delayedCacheObject.getKey(), delayedCacheObject.getReference());
                    statisticService.updateStatistic();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        cleanerThread.setDaemon(true);
        cleanerThread.start();
    }

    @Override
    public void add(String key, Transaction value) {
        if (key == null) {
            return;
        }
        if (value == null) {
            cache.remove(key);
        } else {
            long expiryTime = value.getTimestamp() + EXPIRE_TIME;

            if (System.currentTimeMillis() >= expiryTime) {
                return;
            }

            SoftReference<Transaction> reference = new SoftReference<>(value);
            cache.put(key, reference);
            cleaningUpQueue.put(new DelayedCacheObject(key, reference, expiryTime));
            statisticService.updateStatistic();
        }
    }

    @Override
    public void remove(String key) {
        cache.remove(key);
    }

    @Override
    public Object get(String key) {
        return Optional.ofNullable(cache.get(key)).map(SoftReference::get).orElse(null);
    }

    @Override
    public void clear() {
        cache.clear();
        statisticService.clear();
    }

    @Override
    public long size() {
        return cache.size();
    }

    @Override
    public List<Transaction> getCache() {
        return cache.values().stream().map(Reference::get).collect(Collectors.toList());
    }

    @Override
    public void setEXPIRE_TIME(long EXPIRE_TIME) {
        this.EXPIRE_TIME = EXPIRE_TIME;
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    private static class DelayedCacheObject implements Delayed {

        @Getter
        private final String key;
        @Getter
        private final SoftReference<Transaction> reference;
        private final long expiryTime;

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(expiryTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return Longs.compare(expiryTime, ((DelayedCacheObject) o).expiryTime);
        }
    }

}
