package no.kantega.lab.limber.kernel.request;

import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.application.ILimberApplicationConfiguration;
import no.kantega.lab.limber.kernel.application.ILimberApplicationContext;
import no.kantega.lab.limber.kernel.application.LimberApplicationHandler;
import no.kantega.lab.limber.kernel.meta.PageRenderSynchronization;
import no.kantega.lab.limber.kernel.response.DefaultHttpServletResponseWrapper;
import no.kantega.lab.limber.kernel.response.IHttpServletResponseWrapper;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class LimberRequestHandler {

    private static final LimberRequestHandler INSTANCE = new LimberRequestHandler();

    public static LimberRequestHandler getInstance() {
        return INSTANCE;
    }

    public boolean proceedRequest(
            @Nonnull UUID applicationId,
            @Nonnull HttpServletRequest httpServletRequest,
            @Nonnull HttpServletResponse httpServletResponse)
            throws IOException {

        ILimberApplicationContext applicationContext = LimberApplicationHandler.getInstance().getApplication(applicationId);
        ILimberApplicationConfiguration applicationConfiguration = applicationContext.getLimberApplicationConfiguration();

        // Wrap servlet request and responses
        IHttpServletRequestWrapper httpServletRequestWrapper = new DefaultHttpServletRequestWrapper(httpServletRequest);

        // Wrap request as IRequestContainer, if request can be mapped.
        IRequestMapping requestMapping = applicationConfiguration.getRequestMapperDeque().map(httpServletRequestWrapper);
        if (requestMapping == null) {
            return false;
        }

        // Create a context object for this rendering attempt.
        IRenderContext renderContext = makeRenderContext(
                applicationContext,
                requestMapping,
                httpServletRequestWrapper,
                new DefaultHttpServletResponseWrapper(httpServletResponse));

        // Make this context accessible within internal Limber application components.
        RequestCycleRenderContext.setRenderContext(renderContext);

        // Query container stack to handle the request.
        AbstractRenderable renderable = applicationConfiguration.getInstanceContainerStack().resolve(requestMapping);
        return renderable != null && renderRequest(
                renderContext,
                renderable,
                httpServletResponse.getOutputStream());
    }

    private IRenderContext makeRenderContext(
            @Nonnull ILimberApplicationContext applicationContext,
            @Nonnull IRequestMapping requestMapping,
            @Nonnull IHttpServletRequestWrapper httpServletRequestWrapper,
            @Nonnull IHttpServletResponseWrapper httpServletResponseWrapper) {
        return new DefaultRenderContext(
                applicationContext,
                requestMapping,
                httpServletRequestWrapper,
                httpServletResponseWrapper
        );
    }

    private boolean renderRequest(@Nonnull IRenderContext renderContext,
                                  @Nonnull AbstractRenderable renderable,
                                  @Nonnull OutputStream outputStream) throws IOException {
        if (renderable.getClass().getAnnotation(PageRenderSynchronization.class).value()) {
            synchronized (renderable) {
                return renderable.render(outputStream, renderContext);
            }
        } else {
            return renderable.render(outputStream, renderContext);
        }
    }
}
