package no.kantega.lab.limber.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheStats;
import com.google.common.collect.ImmutableMap;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

@SuppressWarnings("unchecked")
final class InvalidatedCache implements Cache {

    @Nullable
    @Override
    public Object getIfPresent(Object key) {
        throw new IllegalStateException();
    }

    @Override
    public Object get(Object key, Callable valueLoader) throws ExecutionException {
        throw new IllegalStateException();
    }

    @Override
    public ImmutableMap getAllPresent(Iterable keys) {
        throw new IllegalStateException();
    }

    @Override
    public void put(Object key, Object value) {
        throw new IllegalStateException();
    }

    @Override
    public void putAll(Map m) {
        throw new IllegalStateException();
    }

    @Override
    public void invalidate(Object key) {
        throw new IllegalStateException();
    }

    @Override
    public void invalidateAll(Iterable keys) {
        throw new IllegalStateException();
    }

    @Override
    public void invalidateAll() {
        throw new IllegalStateException();
    }

    @Override
    public long size() {
        throw new IllegalStateException();
    }

    @Override
    public CacheStats stats() {
        throw new IllegalStateException();
    }

    @Override
    public ConcurrentMap asMap() {
        throw new IllegalStateException();
    }

    @Override
    public void cleanUp() {
        throw new IllegalStateException();
    }
}
