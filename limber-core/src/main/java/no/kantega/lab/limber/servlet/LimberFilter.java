package no.kantega.lab.limber.servlet;


import net.sf.ehcache.CacheManager;
import no.kantega.lab.limber.servlet.context.RequestCycleRenderContext;
import no.kantega.lab.limber.servlet.request.LimberRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class LimberFilter implements Filter {

    private final static Logger LOGGER = LoggerFactory.getLogger(LimberFilter.class);

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
            LOGGER.info("Ignoring non-HTTP request and delegate to underlying filters instead.");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        try {
            try {
                HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
                HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
                requestHelper.proceedRequest(httpServletRequest, httpServletResponse);
            } catch (IOException e) {
                LOGGER.error("An IO exception occurred. Could not process request.", e);
                servletResponse.getOutputStream().print("Status 500: An error has occured");
            } finally {
                servletResponse.getOutputStream().flush();
                servletResponse.getOutputStream().close();
            }
        } finally {
            RequestCycleRenderContext.clear();
        }

    }

    @Override
    public void destroy() {
        CacheManager.getInstance().shutdown();
        System.out.println(String.format("Limber[%s]: '%s' is shut down for context '/%s'",
                requestHelper.getLimberApplicationContext().getFilterId(),
                requestHelper.getLimberApplicationContext().getContextPath(),
                requestHelper.getLimberApplicationContext().getFilterName()));
    }
}
