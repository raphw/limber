package no.kantega.lab.limber.servlet;

import no.kantega.lab.limber.servlet.request.LimberRequestHandler;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class LimberFilter implements Filter {

    private LimberRequestHandler requestHelper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        UUID filterId = GlobalLimberFilterRegistry.findOrCreateUUID(this);
        requestHelper = new LimberRequestHandler(filterConfig, filterId);
        System.out.println(String.format("Limber[%s]: '%s' is servicing context '/%s'",
                filterId, filterConfig.getFilterName(), filterConfig.getServletContext().getContextPath()));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            requestHelper.proceedRequest(httpServletRequest, httpServletResponse);
        } catch (IOException e) {
            servletResponse.getOutputStream().print("Status 500: An error has occured");
        } finally {
            servletResponse.getOutputStream().flush();
            servletResponse.getOutputStream().close();
        }

    }

    @Override
    public void destroy() {
    }
}
