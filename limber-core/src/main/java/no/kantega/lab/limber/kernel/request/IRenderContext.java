package no.kantega.lab.limber.kernel.request;


import no.kantega.lab.limber.kernel.application.ILimberApplicationContext;
import no.kantega.lab.limber.kernel.application.ILimberPageRegister;
import no.kantega.lab.limber.kernel.response.IHttpServletResponseWrapper;
import no.kantega.lab.limber.kernel.store.IStoreCollection;

import javax.annotation.Nonnull;

public interface IRenderContext {

    @Nonnull
    ILimberApplicationContext getLimberApplicationContext();

    @Nonnull
    ILimberPageRegister getLimberPageRegister();

    @Nonnull
    IRequestMapping getRequestMapping();

    @Nonnull
    IStoreCollection getStoreCollection();

    @Nonnull
    IHttpServletRequestWrapper getHttpServletRequestWrapper();

    @Nonnull
    IHttpServletResponseWrapper getHttpServletResponseWrapper();
}
