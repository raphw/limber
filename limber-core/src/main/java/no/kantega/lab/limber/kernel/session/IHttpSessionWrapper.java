package no.kantega.lab.limber.kernel.session;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpSession;

public interface IHttpSessionWrapper {

    @Nonnull
    HttpSession getHttpSession();
}
