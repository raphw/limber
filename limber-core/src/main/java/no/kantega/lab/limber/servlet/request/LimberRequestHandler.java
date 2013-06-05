package no.kantega.lab.limber.servlet.request;

import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.IResponseContainer;
import no.kantega.lab.limber.servlet.request.container.IInstanceContainer;
import no.kantega.lab.limber.servlet.request.container.InMemoryContainer;
import no.kantega.lab.limber.servlet.request.creator.IInstanceCreator;
import no.kantega.lab.limber.servlet.request.creator.ReflectionInstanceCreator;
import no.kantega.lab.limber.servlet.request.interpreter.AnnotationRequestInterpreter;
import no.kantega.lab.limber.servlet.request.interpreter.IRequestInterpreter;
import no.kantega.lab.limber.servlet.request.interpreter.LimberRequestExpressionInterpreter;

import javax.annotation.Nonnull;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;

public class LimberRequestHandler {

    private final Deque<IRequestInterpreter> requestInterpreters;
    private final IInstanceCreator instanceCreator;
    private final List<IInstanceContainer> instanceContainers;

    private final RequestPacker requestPacker;

    public LimberRequestHandler(@Nonnull FilterConfig filterConfig) {

        // Set up request interpreters
        requestInterpreters = new LinkedList<IRequestInterpreter>();
        requestInterpreters.add(new LimberRequestExpressionInterpreter());
        String scanPackage = filterConfig.getInitParameter("scan-package");
        if (scanPackage != null) {
            requestInterpreters.add(new AnnotationRequestInterpreter(scanPackage));
        }

        // Set up instance creator
        instanceCreator = new ReflectionInstanceCreator();

        // Set up request containers
        instanceContainers = new LinkedList<IInstanceContainer>();
        instanceContainers.add(new InMemoryContainer());

        // Set up other utilities
        requestPacker = new RequestPacker();

    }

    public boolean proceedRequest(
            @Nonnull HttpServletRequest httpServletRequest,
            @Nonnull HttpServletResponse httpServletResponse)
            throws IOException {

        // Transform servlet request in raw request which is visible to API users.
        RawRequest rawRequest = requestPacker.pack(httpServletRequest);

        // Wrap request as ILimberRequest, if request can be mapped.
        ILimberRequest limberRequest = interpretRawRequest(rawRequest);
        if (limberRequest == null) {
            return false;
        }

        // Query container stack to handle the request.
        IRenderable renderable = findRenderableToRequest(limberRequest);
        return renderable != null && renderRequest(limberRequest, renderable, httpServletResponse);

    }

    private ILimberRequest interpretRawRequest(@Nonnull RawRequest rawRequest) throws IOException {
        for (IRequestInterpreter requestResolver : requestInterpreters) {
            ILimberRequest limberRequest = requestResolver.interpret(rawRequest);
            if (limberRequest != null) {
                return limberRequest;
            }
        }
        return null;
    }

    private IRenderable findRenderableToRequest(@Nonnull ILimberRequest limberRequest) {
        for (IInstanceContainer instanceContainer : instanceContainers) {
            IRenderable renderable = instanceContainer.resolve(limberRequest, instanceCreator);
            if (renderable != null) {
                return renderable;
            }
        }
        return null;
    }

    private boolean renderRequest(@Nonnull ILimberRequest limberRequest, @Nonnull IRenderable renderable,
                                  @Nonnull HttpServletResponse httpServletResponse) throws IOException {
        return renderable.render(httpServletResponse.getOutputStream(),
                new DefaultResponseContainer(limberRequest, httpServletResponse));
    }

    private class DefaultResponseContainer implements IResponseContainer {

        private final ILimberRequest limberRequest;
        private final HttpServletResponse httpServletResponse;

        public DefaultResponseContainer(ILimberRequest limberRequest, HttpServletResponse httpServletResponse) {
            this.limberRequest = limberRequest;
            this.httpServletResponse = httpServletResponse;
        }

        @Override
        public ILimberRequest getRequest() {
            return limberRequest;
        }

        @Override
        public void setStatusCode(int code) {
            httpServletResponse.setStatus(code);
        }

        @Override
        public void addHeader(@Nonnull String key, String value) {
            httpServletResponse.addHeader(key, value);
        }

        @Override
        public URI decodeLink(@Nonnull Class<? extends IRenderable> renderableClass, UUID versionId, UUID ajaxId) {
            // Reversely iterate over the list of request interpreters.
            Iterator<IRequestInterpreter> iterator = requestInterpreters.descendingIterator();
            while (iterator.hasNext()) {
                IRequestInterpreter interpreter = iterator.next();
                URI link = interpreter.resolve(renderableClass, versionId, ajaxId);
                if (link != null) {
                    return link;
                }
            }
            return null;
        }
    }

}
