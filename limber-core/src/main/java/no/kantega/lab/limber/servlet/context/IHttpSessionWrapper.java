package no.kantega.lab.limber.servlet.context;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpSession;

public interface IHttpSessionWrapper {

    @Nonnull
    HttpSession getHttpSession();
}
