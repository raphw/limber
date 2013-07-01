package no.kantega.lab.limber.servlet.request;

import no.kantega.lab.limber.servlet.AbstractRenderable;
import no.kantega.lab.limber.servlet.context.*;
import no.kantega.lab.limber.servlet.request.container.IInstanceContainer;
import no.kantega.lab.limber.servlet.request.container.InMemoryContainer;
import no.kantega.lab.limber.servlet.request.container.VersioningPseudoContainer;
import no.kantega.lab.limber.servlet.request.context.DefaultRenderContext;
import no.kantega.lab.limber.servlet.request.context.IRenderContext;
import no.kantega.lab.limber.servlet.request.creator.IInstanceCreator;
import no.kantega.lab.limber.servlet.request.creator.ReflectionInstanceCreator;
import no.kantega.lab.limber.servlet.request.interpreter.AnnotationRequestInterpreter;
import no.kantega.lab.limber.servlet.request.interpreter.IRequestInterpreter;
import no.kantega.lab.limber.servlet.request.interpreter.LimberRequestExpressionInterpreter;
import no.kantega.lab.limber.servlet.request.interpreter.RessourceRequestInterpreter;

import javax.annotation.Nonnull;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Deque;
import java.util.LinkedList;
import java.util.UUID;

public class LimberRequestHandler {

    private final ILimberApplicationContext limberApplicationContext;
    private final ILimberPageRegister limberPageRegister;

    private final Deque<IRequestInterpreter> requestInterpreters;
    private final IInstanceContainer topInstanceContainer;
    private final IInstanceCreator instanceCreator;

    public LimberRequestHandler(@Nonnull FilterConfig filterConfig, @Nonnull UUID filterId) {

        // Create application context
        limberApplicationContext = new DefaultLimberApplicationContext(filterConfig, filterId);

        // Set up request interpreters
        requestInterpreters = new LinkedList<IRequestInterpreter>();
        requestInterpreters.add(new LimberRequestExpressionInterpreter());
        requestInterpreters.add(new AnnotationRequestInterpreter(limberApplicationContext));
        requestInterpreters.add(new RessourceRequestInterpreter(limberApplicationContext));

        // Set up instance creator
        instanceCreator = new ReflectionInstanceCreator();

        // Set up request containers
        IInstanceContainer latest = new InMemoryContainer();
        latest = new VersioningPseudoContainer(latest);
        topInstanceContainer = latest;

        limberPageRegister = new DefaultLimberPageRegister(requestInterpreters);
    }

    public boolean proceedRequest(
            @Nonnull HttpServletRequest httpServletRequest,
            @Nonnull HttpServletResponse httpServletResponse)
            throws IOException {

        // Wrap servlet request and responses
        IHttpServletRequestWrapper httpServletRequestWrapper = new DefaultHttpServletRequestWrapper(httpServletRequest);

        // Wrap request as IRequestContainer, if request can be mapped.
        IRequestMapping requestMapping = mapRequest(httpServletRequestWrapper);
        if (requestMapping == null) {
            return false;
        }

        // Create a context object for this rendering attempt.
        IRenderContext renderContext = makeRenderContext(
                requestMapping,
                httpServletRequestWrapper,
                new DefaultHttpServletResponseWrapper(httpServletResponse));

        // Make this context accessible within internal Limber application components.
        RequestCycleRenderContext.setRenderContext(renderContext);

        // Query container stack to handle the request.
        AbstractRenderable renderable = findRenderableToRequest(requestMapping);
        return renderable != null && renderRequest(
                renderContext,
                renderable,
                httpServletResponse.getOutputStream());
    }

    private IRequestMapping mapRequest(@Nonnull IHttpServletRequestWrapper httpServletRequestWrapper) throws IOException {
        for (IRequestInterpreter requestResolver : requestInterpreters) {
            IRequestMapping requestMapping = requestResolver.interpret(httpServletRequestWrapper);
            if (requestMapping != null) {
                return requestMapping;
            }
        }
        return null;
    }

    private IRenderContext makeRenderContext(
            @Nonnull IRequestMapping requestMapping,
            @Nonnull IHttpServletRequestWrapper httpServletRequestWrapper,
            @Nonnull IHttpServletResponseWrapper httpServletResponseWrapper) {
        return new DefaultRenderContext(
                limberApplicationContext,
                limberPageRegister,
                requestMapping,
                httpServletRequestWrapper,
                httpServletResponseWrapper
        );
    }

    private AbstractRenderable findRenderableToRequest(@Nonnull IRequestMapping requestMapping) {
        return topInstanceContainer.resolve(requestMapping, instanceCreator);
    }

    private boolean renderRequest(@Nonnull IRenderContext renderContext,
                                  @Nonnull AbstractRenderable renderable,
                                  @Nonnull OutputStream outputStream) throws IOException {
        return renderable.render(outputStream, renderContext);
    }
}
