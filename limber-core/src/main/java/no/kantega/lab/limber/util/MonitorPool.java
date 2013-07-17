package no.kantega.lab.limber.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import javax.annotation.Nonnull;

public class MonitorPool<T> {

    private final LoadingCache<T, T> pool;

    public MonitorPool() {
        this.pool = CacheBuilder.newBuilder().weakValues().build(new CacheLoader<T, T>() {
            @Override
            public T load(T key) throws Exception {
                return key;
            }
        });
    }

    @Nonnull
    public T getMonitorBoundTo(@Nonnull T key) {
        return pool.getUnchecked(key);
    }
}
