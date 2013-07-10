package no.kantega.lab.limber.servlet.context;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpSession;

public class DefaultHttpSessionWrapper implements IHttpSessionWrapper {

    private final HttpSession httpSession;

    public DefaultHttpSessionWrapper(@Nonnull HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @Nonnull
    @Override
    public HttpSession getHttpSession() {
        return httpSession;
    }
}
