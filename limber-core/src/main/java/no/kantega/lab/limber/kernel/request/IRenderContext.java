package no.kantega.lab.limber.kernel.request;


import no.kantega.lab.limber.kernel.application.ILimberApplicationContext;
import no.kantega.lab.limber.kernel.application.ILimberPageRegister;
import no.kantega.lab.limber.kernel.response.IHttpServletResponseWrapper;

public interface IRenderContext {

    ILimberApplicationContext getLimberApplicationContext();

    ILimberPageRegister getLimberPageRegister();

    IRequestMapping getRequestMapping();

    IHttpServletRequestWrapper getHttpServletRequestWrapper();

    IHttpServletResponseWrapper getHttpServletResponseWrapper();
}
