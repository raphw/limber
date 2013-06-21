package no.kantega.lab.limber.servlet.request.interpreter;

import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.context.IHttpServletRequestWrapper;
import no.kantega.lab.limber.servlet.context.ILimberApplicationContext;
import no.kantega.lab.limber.servlet.context.IRequestMapping;

import javax.annotation.Nonnull;
import java.net.URI;
import java.util.UUID;

public class RessourceRequestInterpreter implements IRequestInterpreter {

    private final ILimberApplicationContext limberApplicationContext;

    public RessourceRequestInterpreter(ILimberApplicationContext limberApplicationContext) {
        this.limberApplicationContext = limberApplicationContext;
    }

    @Override
    public IRequestMapping interpret(@Nonnull IHttpServletRequestWrapper httpServletRequestWrapper) {

        if(limberApplicationContext.isLocalResource(httpServletRequestWrapper.getRequestUri()));

        return null;
    }

    @Override
    public URI resolve(@Nonnull Class<? extends IRenderable> renderableClass, UUID versionId, UUID ajaxId) {
        return null;
    }
}