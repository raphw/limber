package no.kantega.lab.limber.kernel.mapper;

import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.application.ILimberApplicationContext;
import no.kantega.lab.limber.kernel.request.DefaultRequestMapping;
import no.kantega.lab.limber.kernel.request.IHttpServletRequestWrapper;
import no.kantega.lab.limber.kernel.request.IRequestMapping;
import no.kantega.lab.limber.kernel.response.LocalResourceResponse;

import javax.annotation.Nonnull;
import java.net.URI;
import java.util.UUID;

public class RessourceRequestMapper implements IRequestMapper {

    private final ILimberApplicationContext limberApplicationContext;

    public RessourceRequestMapper(ILimberApplicationContext limberApplicationContext) {
        this.limberApplicationContext = limberApplicationContext;
    }

    @Override
    public IRequestMapping map(@Nonnull IHttpServletRequestWrapper httpServletRequestWrapper) {

        if (limberApplicationContext.isLocalReadableFile(httpServletRequestWrapper.getRequestUri())) {
            return new DefaultRequestMapping(
                    httpServletRequestWrapper.getSessionId(), LocalResourceResponse.class, null, null);
        } else {
            return null;
        }
    }

    @Override
    public URI resolve(@Nonnull Class<? extends AbstractRenderable> renderableClass, UUID versionId, UUID ajaxId) {
        return null;
    }
}