package no.kantega.lab.limber.servlet.request.context;

import no.kantega.lab.limber.servlet.context.*;

import javax.annotation.Nonnull;

public class DefaultRenderContext implements IRenderContext {

    private final ILimberApplicationContext limberApplicationContext;

    private final ILimberPageRegister limberPageRegister;

    private final IRequestMapping requestMapping;

    private final IHttpServletRequestWrapper httpServletRequestWrapper;

    private final IHttpServletResponseWrapper httpServletResponseWrapper;

//    private final IHttpSessionWrapper httpSessionWrapper;

    public DefaultRenderContext(@Nonnull ILimberApplicationContext limberApplicationContext,
                                @Nonnull ILimberPageRegister limberPageRegister,
                                @Nonnull IRequestMapping requestMapping,
                                @Nonnull IHttpServletRequestWrapper httpServletRequestWrapper,
                                @Nonnull IHttpServletResponseWrapper httpServletResponseWrapper) {
        this.limberApplicationContext = limberApplicationContext;
        this.limberPageRegister = limberPageRegister;
        this.requestMapping = requestMapping;
        this.httpServletRequestWrapper = httpServletRequestWrapper;
        this.httpServletResponseWrapper = httpServletResponseWrapper;
//        this.httpSessionWrapper = new DefaultHttpSessionWrapper();
    }

    @Override
    public ILimberApplicationContext getLimberApplicationContext() {
        return limberApplicationContext;
    }

    @Override
    public ILimberPageRegister getLimberPageRegister() {
        return limberPageRegister;
    }

    @Override
    public IRequestMapping getRequestMapping() {
        return requestMapping;
    }

    @Override
    public IHttpServletRequestWrapper getHttpServletRequestWrapper() {
        return httpServletRequestWrapper;
    }

    @Override
    public IHttpServletResponseWrapper getHttpServletResponseWrapper() {
        return httpServletResponseWrapper;
    }

//    @Override
//    public IHttpSessionWrapper getHttpSessionWrapper() {
//        return httpSessionWrapper;
//    }
}
