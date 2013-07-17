package no.kantega.lab.limber.kernel.store;

import no.kantega.lab.limber.kernel.application.ILimberApplicationContext;
import no.kantega.lab.limber.kernel.request.IHttpServletRequestWrapper;

import javax.annotation.Nonnull;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

public class DefaultStoreCollection implements IStoreCollection {

    public static IStoreCollection from(@Nonnull ILimberApplicationContext applicationContext,
                                        @Nonnull IHttpServletRequestWrapper httpServletRequestWrapper) {
        return new DefaultStoreCollection(
                applicationContext.getFilterId(),
                applicationContext.getServletContext(),
                httpServletRequestWrapper.getHttpServletRequest().getSession(true),
                httpServletRequestWrapper.getHttpServletRequest()
        );
    }

    private final IStore applicationStore, sessionStore, requestStore;

    public DefaultStoreCollection(@Nonnull UUID applicationId, @Nonnull ServletContext servletContext,
                                  @Nonnull HttpSession httpSession, @Nonnull ServletRequest servletRequest) {
        this.applicationStore = new ApplicationStore(applicationId, servletContext);
        this.sessionStore = new SessionStore(applicationId, httpSession);
        this.requestStore = new RequestStore(applicationId, servletRequest);
    }

    @Nonnull
    @Override
    public IStore getApplicationStore() {
        return applicationStore;
    }

    @Nonnull
    @Override
    public IStore getSessionStore() {
        return sessionStore;
    }

    @Nonnull
    @Override
    public IStore getRequestStore() {
        return requestStore;
    }
}
