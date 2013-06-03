package no.kantega.lab.limber.servlet;

import no.kantega.lab.limber.servlet.request.LimberRequestHandler;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class LimberFilter implements Filter {

    private UUID filterId;

    private LimberRequestHandler requestHelper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        filterId = GlobalLimberFilterRegistry.findOrCreateUUID(this);
        requestHelper = new LimberRequestHandler(filterConfig);
        System.out.println(String.format("Limber[%s]: '%s' is ready", filterId, filterConfig.getFilterName()));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // TODO: Add support for resolving different sides (maybe by configuration class which
        // is referred by a web.xml entry as in Wicket)

        // TODO: Add filtering mechanism that allows to respond to different requests in different
        // ways. Some requests might refer to plain files, some might be resources on the file system,
        // some might be downloads etc.

        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            requestHelper.proceedRequest(httpServletRequest, httpServletResponse);
        } catch (IOException e) {
            servletResponse.getOutputStream().print("Status 500: Fatal exception");
        } finally {
            servletResponse.getOutputStream().flush();
            servletResponse.getOutputStream().close();
        }

    }

    @Override
    public void destroy() {
    }
}
