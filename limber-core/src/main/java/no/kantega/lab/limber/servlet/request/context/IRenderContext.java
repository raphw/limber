package no.kantega.lab.limber.servlet.request.context;


import no.kantega.lab.limber.servlet.context.*;

public interface IRenderContext {

    ILimberApplicationContext getLimberApplicationContext();

    ILimberPageRegister getLimberPageRegister();

    IRequestMapping getRequestMapping();

    IHttpServletRequestWrapper getHttpServletRequestWrapper();

    IHttpServletResponseWrapper getHttpServletResponseWrapper();

//    IHttpSessionWrapper getHttpSessionWrapper();
}
