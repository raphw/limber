package no.kantega.lab.limber.servlet.context;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public interface IHttpServletRequestWrapper {

    @Nonnull
    HttpServletRequest getHttpServletRequest();

    @Nonnull
    String getSessionId();

    @Nonnull
    String getRequestUri();

    String getQueryArgument(@Nonnull CharSequence key);

    @Nonnull
    Set<String> getQueryArgumentKeys();

    String getHeadArgument(@Nonnull CharSequence key);

    @Nonnull
    Set<String> getHeadArgumentKeys();

    @Nonnull
    String getScheme();

    @Nonnull
    String getHost();

    int getPort();

    @Nonnull
    String getMethod();

    @Nonnull
    String getServerName();
}
