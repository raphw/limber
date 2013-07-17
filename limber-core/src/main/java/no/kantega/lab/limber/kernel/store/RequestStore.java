package no.kantega.lab.limber.kernel.store;

import no.kantega.lab.limber.util.MonitorPool;

import javax.annotation.Nonnull;
import javax.servlet.ServletRequest;
import java.util.Arrays;
import java.util.UUID;

public class RequestStore extends AbstractStore {

    private static class RequestStoreAccess implements IStoreAccess {

        private final ServletRequest servletRequest;

        private RequestStoreAccess(@Nonnull ServletRequest servletRequest) {
            this.servletRequest = servletRequest;
        }

        @Override
        public Object get(@Nonnull String key) {
            return servletRequest.getAttribute(key);
        }

        @Override
        public void put(@Nonnull String key, Object value) {
            servletRequest.setAttribute(key, value);
        }

        @Override
        public void remove(@Nonnull String key) {
            servletRequest.removeAttribute(key);
        }
    }

    private static final MonitorPool<Object> MONITOR_POOL = new MonitorPool<Object>();

    private final ServletRequest servletRequest;

    public RequestStore(@Nonnull UUID applicationId, @Nonnull ServletRequest servletRequest) {
        super(applicationId, new RequestStoreAccess(servletRequest));
        this.servletRequest = servletRequest;
    }

    @Override
    protected MonitorPool<Object> getMonitorPool() {
        return MONITOR_POOL;
    }

    @Nonnull
    @Override
    protected Object makeMonitorKey() {
        return Arrays.asList(getApplicationId(), servletRequest);
    }
}
