package no.kantega.lab.limber.servlet;

import no.kantega.lab.limber.page.WebPage;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LimberFilter implements Filter {

    private Class<? extends WebPage> startPageClass;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        startPageClass = resolveStartWebPage(filterConfig.getInitParameter("start-page"));

    }

    @SuppressWarnings("unchecked")
    private Class<? extends WebPage> resolveStartWebPage(String parameter) {

        try {
            Class<?> startPage = Class.forName(parameter);
            if (!WebPage.class.isAssignableFrom(startPage)) {
                throw new RuntimeException("Argument is no Limber WebPage");
            }
            return (Class<? extends WebPage>) startPage;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Start page not found");
        }

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        if (!(servletRequest instanceof HttpServletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        try {
            startPageClass.newInstance().writePageToStream(servletResponse.getOutputStream());
        } catch (InstantiationException e) {
            throw new RuntimeException("Default constructor not found");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access default constructor");
        }

        servletResponse.getOutputStream().flush();
        servletResponse.getOutputStream().close();

    }

    @Override
    public void destroy() {
    }
}
