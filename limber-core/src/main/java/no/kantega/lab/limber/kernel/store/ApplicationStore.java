package no.kantega.lab.limber.kernel.store;

import no.kantega.lab.limber.util.MonitorPool;

import javax.annotation.Nonnull;
import javax.servlet.ServletContext;
import java.util.UUID;

public class ApplicationStore extends AbstractStore {

    private static class ApplicationStoreAccess implements IStoreAccess {

        private final ServletContext servletContext;

        private ApplicationStoreAccess(@Nonnull ServletContext servletContext) {
            this.servletContext = servletContext;
        }

        @Override
        public Object get(@Nonnull String key) {
            return servletContext.getAttribute(key);
        }

        @Override
        public void put(@Nonnull String key, Object value) {
            servletContext.setAttribute(key, value);
        }

        @Override
        public void remove(@Nonnull String key) {
            servletContext.removeAttribute(key);
        }
    }

    private static final MonitorPool<Object> MONITOR_POOL = new MonitorPool<Object>();

    public ApplicationStore(@Nonnull UUID applicationId, @Nonnull ServletContext servletContext) {
        super(applicationId, new ApplicationStoreAccess(servletContext));
    }

    @Override
    protected MonitorPool<Object> getMonitorPool() {
        return MONITOR_POOL;
    }

    @Nonnull
    @Override
    protected Object makeMonitorKey() {
        return getApplicationId();
    }
}
