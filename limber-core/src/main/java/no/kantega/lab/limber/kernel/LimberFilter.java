package no.kantega.lab.limber.kernel;

import no.kantega.lab.limber.kernel.application.LimberApplicationHandler;
import no.kantega.lab.limber.kernel.request.LimberRequestHandler;
import no.kantega.lab.limber.kernel.request.RequestCycleRenderContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class LimberFilter implements Filter {

    private final static Logger LOGGER = LoggerFactory.getLogger(LimberFilter.class);

    private UUID applicationId;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        applicationId = LimberApplicationHandler.getInstance().registerApplication(filterConfig);
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

                LimberRequestHandler.getInstance().proceedRequest(applicationId, httpServletRequest, httpServletResponse);
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
        LimberApplicationHandler.getInstance().deregisterApplication(applicationId);
    }
}
