package no.kantega.lab.limber.kernel.session;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class LimberSessionHandler implements HttpSessionListener {

    private final UUID applicationId;

    private final Set<ILimberSessionListener> sessionListeners;

    public LimberSessionHandler(@Nonnull UUID applicationId) {
        sessionListeners = new HashSet<ILimberSessionListener>();
        this.applicationId = applicationId;
    }

    @Override
    public void sessionCreated(@Nonnull HttpSessionEvent httpSessionEvent) {
        for (ILimberSessionListener sessionListener : sessionListeners) {
            sessionListener.onSessionCreate(httpSessionEvent.getSession().getId());
        }
    }

    @Override
    public void sessionDestroyed(@Nonnull HttpSessionEvent httpSessionEvent) {
        for (ILimberSessionListener sessionListener : sessionListeners) {
            sessionListener.onSessionDestroy(httpSessionEvent.getSession().getId());
        }
    }
}
