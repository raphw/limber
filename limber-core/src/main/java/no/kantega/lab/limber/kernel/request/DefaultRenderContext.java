package no.kantega.lab.limber.kernel.request;

import no.kantega.lab.limber.kernel.application.ILimberApplicationContext;
import no.kantega.lab.limber.kernel.response.IHttpServletResponseWrapper;
import no.kantega.lab.limber.kernel.store.DefaultStoreCollection;
import no.kantega.lab.limber.kernel.store.IStoreCollection;

import javax.annotation.Nonnull;

public class DefaultRenderContext implements IRenderContext {

    private final ILimberApplicationContext limberApplicationContext;

    private final IRequestMapping requestMapping;

    private final IStoreCollection storeCollection;

    private final IHttpServletRequestWrapper httpServletRequestWrapper;

    private final IHttpServletResponseWrapper httpServletResponseWrapper;

    private final IPageContext pageContext;

    public DefaultRenderContext(@Nonnull ILimberApplicationContext applicationContext,
                                @Nonnull IRequestMapping requestMapping,
                                @Nonnull IHttpServletRequestWrapper httpServletRequestWrapper,
                                @Nonnull IHttpServletResponseWrapper httpServletResponseWrapper) {
        this.limberApplicationContext = applicationContext;
        this.requestMapping = requestMapping;
        this.storeCollection = DefaultStoreCollection.from(applicationContext, httpServletRequestWrapper);
        this.httpServletRequestWrapper = httpServletRequestWrapper;
        this.httpServletResponseWrapper = httpServletResponseWrapper;
        this.pageContext = new DefaultPageContext(applicationContext);
    }

    @Nonnull
    @Override
    public ILimberApplicationContext getLimberApplicationContext() {
        return limberApplicationContext;
    }

    @Nonnull
    @Override
    public IRequestMapping getRequestMapping() {
        return requestMapping;
    }

    @Nonnull
    @Override
    public IStoreCollection getStoreCollection() {
        return storeCollection;
    }

    @Nonnull
    @Override
    public IHttpServletRequestWrapper getHttpServletRequestWrapper() {
        return httpServletRequestWrapper;
    }

    @Nonnull
    @Override
    public IHttpServletResponseWrapper getHttpServletResponseWrapper() {
        return httpServletResponseWrapper;
    }

    @Override
    public IPageContext getPageContext() {
        return pageContext;
    }
}
