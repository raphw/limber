package no.kantega.lab.limber.kernel.session;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;
import java.util.Set;

public class LimberSessionHandler implements HttpSessionListener {

    private static final LimberSessionHandler INSTANCE = new LimberSessionHandler();

    public static LimberSessionHandler getInstance() {
        return INSTANCE;
    }

    private final Set<ILimberSessionListener> sessionListeners;

    public LimberSessionHandler() {
        sessionListeners = new HashSet<ILimberSessionListener>();
    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
    }
}
