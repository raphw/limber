package no.kantega.lab.limber.servlet.context;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public interface IHttpServletResponseWrapper {

    @Nonnull
    HttpServletResponse getHttpServletResponse();

    @Nonnull
    IHttpServletResponseWrapper setStatusCode(int statusCode);

    @Nonnull
    IHttpServletResponseWrapper putHeaderValue(@Nonnull CharSequence key, CharSequence value);

    @Nonnull
    IHttpServletResponseWrapper putHeaderValue(@Nonnull CharSequence key, @Nonnull Date value);

    @Nonnull
    IHttpServletResponseWrapper putHeaderValue(@Nonnull CharSequence key, int value);

    boolean containsHeader(@Nonnull CharSequence key);
}
