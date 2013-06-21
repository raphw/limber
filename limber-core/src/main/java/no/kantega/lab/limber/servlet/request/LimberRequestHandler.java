package no.kantega.lab.limber.servlet.request;

import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.context.*;
import no.kantega.lab.limber.servlet.request.container.IInstanceContainer;
import no.kantega.lab.limber.servlet.request.container.InMemoryContainer;
import no.kantega.lab.limber.servlet.request.context.DefaultRenderContext;
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
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class LimberRequestHandler {

    private final ILimberApplicationContext limberApplicationContext;
    private final ILimberPageRegister limberPageRegister;

    private final Deque<IRequestInterpreter> requestInterpreters;
    private final IInstanceCreator instanceCreator;
    private final List<IInstanceContainer> instanceContainers;

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
        instanceContainers = new LinkedList<IInstanceContainer>();
        instanceContainers.add(new InMemoryContainer());

        limberPageRegister = new DefaultLimberPageRegister(requestInterpreters);

    }

    public boolean proceedRequest(
            @Nonnull HttpServletRequest httpServletRequest,
            @Nonnull HttpServletResponse httpServletResponse)
            throws IOException {

        // Wrap servlet request and response
        IHttpServletRequestWrapper httpServletRequestWrapper = new DefaultHttpServletRequestWrapper(httpServletRequest);

        // Wrap request as IRequestContainer, if request can be mapped.
        IRequestMapping requestMapping = interpretRawRequest(httpServletRequestWrapper);
        if (requestMapping == null) {
            return false;
        }

        // Query container stack to handle the request.
        IRenderable renderable = findRenderableToRequest(requestMapping);
        return renderable != null && renderRequest(
                requestMapping,
                renderable,
                httpServletRequestWrapper,
                new DefaultHttpServletResponseWrapper(httpServletResponse));
    }

    private IRequestMapping interpretRawRequest(@Nonnull IHttpServletRequestWrapper httpServletRequestWrapper) throws IOException {
        for (IRequestInterpreter requestResolver : requestInterpreters) {
            IRequestMapping requestMapping = requestResolver.interpret(httpServletRequestWrapper);
            if (requestMapping != null) {
                return requestMapping;
            }
        }
        return null;
    }

    private IRenderable findRenderableToRequest(@Nonnull IRequestMapping requestMapping) {
        for (IInstanceContainer instanceContainer : instanceContainers) {
            IRenderable renderable = instanceContainer.resolve(requestMapping, instanceCreator);
            if (renderable != null) {
                return renderable;
            }
        }
        return null;
    }

    private boolean renderRequest(@Nonnull IRequestMapping requestMapping,
                                  @Nonnull IRenderable renderable,
                                  @Nonnull IHttpServletRequestWrapper httpServletRequestWrapper,
                                  @Nonnull IHttpServletResponseWrapper httpServletResponseWrapper) throws IOException {
        return renderable.render(
                httpServletResponseWrapper.getHttpServletResponse().getOutputStream(),
                new DefaultRenderContext(
                        limberApplicationContext,
                        limberPageRegister,
                        requestMapping,
                        httpServletRequestWrapper,
                        httpServletResponseWrapper
                ));
    }
}
