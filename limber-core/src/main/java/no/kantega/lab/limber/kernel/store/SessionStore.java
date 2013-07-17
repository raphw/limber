package no.kantega.lab.limber.kernel.store;

import no.kantega.lab.limber.util.MonitorPool;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.UUID;

public class SessionStore extends AbstractStore {

    private static class SessionStoreAccess implements IStoreAccess {

        private final HttpSession httpSession;

        private SessionStoreAccess(@Nonnull HttpSession httpSession) {
            this.httpSession = httpSession;
        }

        @Override
        public Object get(@Nonnull String key) {
            return httpSession.getAttribute(key);
        }

        @Override
        public void put(@Nonnull String key, Object value) {
            httpSession.setAttribute(key, value);
        }

        @Override
        public void remove(@Nonnull String key) {
            httpSession.removeAttribute(key);
        }
    }

    private static final MonitorPool<Object> MONITOR_POOL = new MonitorPool<Object>();

    private final String sessionId;

    public SessionStore(@Nonnull UUID applicationId, @Nonnull HttpSession httpSession) {
        super(applicationId, new SessionStoreAccess(httpSession));
        this.sessionId = httpSession.getId();
    }

    @Override
    protected MonitorPool<Object> getMonitorPool() {
        return MONITOR_POOL;
    }

    @Nonnull
    @Override
    protected Object makeMonitorKey() {
        return Arrays.asList(getApplicationId(), sessionId);
    }
}
