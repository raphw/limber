package no.kantega.lab.limber.kernel.store;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import no.kantega.lab.limber.util.MonitorPool;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public abstract class AbstractStore implements IStore {

    public static interface IStoreAccess {

        Object get(@Nonnull String key);

        void put(@Nonnull String key, Object value);

        void remove(@Nonnull String key);
    }

    private final String applicationId;
    private final IStoreAccess storeAccess;

    protected AbstractStore(@Nonnull UUID applicationId, @Nonnull IStoreAccess storeAccess) {
        this.storeAccess = storeAccess;
        this.applicationId = applicationId.toString();
    }

    @Nonnull
    private Cache<String, Object> makeCache() {
        return CacheBuilder.newBuilder().build();
    }

    protected abstract MonitorPool<Object> getMonitorPool();

    @Nonnull
    private Object getStoreMap() {
        Object storeMap = storeAccess.get(applicationId);
        if (storeMap != null) return storeMap;
        synchronized (getMonitorPool().getMonitorBoundTo(makeMonitorKey())) {
            storeMap = storeAccess.get(applicationId);
            if (storeMap != null) return storeMap;
            storeMap = makeCache();
            storeAccess.put(applicationId, storeMap);
            return storeMap;
        }
    }

    @Nonnull
    protected abstract Object makeMonitorKey();

    @Nonnull
    @SuppressWarnings("unchecked")
    private Cache<String, Object> getStoreMapCasted() {
        return (Cache<String, Object>) getStoreMap();
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Callable<T> valueMaker) {
        try {
            return (T) getStoreMapCasted().get(key, valueMaker);
        } catch (ExecutionException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Marker<T> marker) {
        return (T) getStoreMapCasted().getIfPresent(key);
    }

    @Override
    public Object get(String key) {
        return getStoreMapCasted().getIfPresent(key);
    }

    @Nonnull
    @Override
    public IStore release() {
        storeAccess.remove(applicationId);
        return this;
    }

    protected String getApplicationId() {
        return applicationId;
    }
}
