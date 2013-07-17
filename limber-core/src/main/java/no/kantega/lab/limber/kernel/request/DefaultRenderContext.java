package no.kantega.lab.limber.kernel.request;

import no.kantega.lab.limber.kernel.application.ILimberApplicationContext;
import no.kantega.lab.limber.kernel.application.ILimberPageRegister;
import no.kantega.lab.limber.kernel.response.IHttpServletResponseWrapper;
import no.kantega.lab.limber.kernel.store.DefaultStoreCollection;
import no.kantega.lab.limber.kernel.store.IStoreCollection;

import javax.annotation.Nonnull;

public class DefaultRenderContext implements IRenderContext {

    private final ILimberApplicationContext limberApplicationContext;

    private final ILimberPageRegister limberPageRegister;

    private final IRequestMapping requestMapping;

    private final IStoreCollection storeCollection;

    private final IHttpServletRequestWrapper httpServletRequestWrapper;

    private final IHttpServletResponseWrapper httpServletResponseWrapper;


    public DefaultRenderContext(@Nonnull ILimberApplicationContext applicationContext,
                                @Nonnull IRequestMapping requestMapping,
                                @Nonnull IHttpServletRequestWrapper httpServletRequestWrapper,
                                @Nonnull IHttpServletResponseWrapper httpServletResponseWrapper) {
        this.limberApplicationContext = applicationContext;
        this.limberPageRegister = applicationContext.getLimberApplicationConfiguration().getLimberPageRegister();
        this.requestMapping = requestMapping;
        this.storeCollection = DefaultStoreCollection.from(applicationContext, httpServletRequestWrapper);
        this.httpServletRequestWrapper = httpServletRequestWrapper;
        this.httpServletResponseWrapper = httpServletResponseWrapper;
    }

    @Nonnull
    @Override
    public ILimberApplicationContext getLimberApplicationContext() {
        return limberApplicationContext;
    }

    @Nonnull
    @Override
    public ILimberPageRegister getLimberPageRegister() {
        return limberPageRegister;
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
}
