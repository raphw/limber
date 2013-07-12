package no.kantega.lab.limber.kernel.response;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class DefaultHttpServletResponseWrapper implements IHttpServletResponseWrapper {

    private final HttpServletResponse httpServletResponse;

    public DefaultHttpServletResponseWrapper(@Nonnull HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    @Nonnull
    @Override
    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }

    @Nonnull
    @Override
    public IHttpServletResponseWrapper setStatusCode(int statusCode) {
        httpServletResponse.setStatus(statusCode);
        return this;
    }

    @Nonnull
    @Override
    public IHttpServletResponseWrapper putHeaderValue(@Nonnull CharSequence key, CharSequence value) {
        httpServletResponse.setHeader(key.toString(), value.toString());
        return this;
    }

    @Nonnull
    @Override
    public IHttpServletResponseWrapper putHeaderValue(@Nonnull CharSequence key, @Nonnull Date value) {
        httpServletResponse.setDateHeader(key.toString(), value.getTime());
        return this;
    }

    @Nonnull
    @Override
    public IHttpServletResponseWrapper putHeaderValue(@Nonnull CharSequence key, int value) {
        httpServletResponse.setDateHeader(key.toString(), value);
        return this;
    }

    @Override
    public boolean containsHeader(@Nonnull CharSequence key) {
        return httpServletResponse.containsHeader(key.toString());
    }
}
