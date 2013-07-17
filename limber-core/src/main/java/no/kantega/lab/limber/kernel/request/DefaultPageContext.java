package no.kantega.lab.limber.kernel.request;

import no.kantega.lab.limber.exception.NotYetImplementedException;
import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.application.ILimberApplicationContext;

import javax.annotation.Nonnull;
import java.net.URI;
import java.util.UUID;

public class DefaultPageContext implements IPageContext {

    private final ILimberApplicationContext applicationContext;

    public DefaultPageContext(@Nonnull ILimberApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public URI decodeLink(@Nonnull AbstractRenderable renderable) {
        throw new NotYetImplementedException();
    }

    @Override
    public URI decodeLink(@Nonnull UUID subroutineId) {
        throw new NotYetImplementedException();
    }

    @Override
    public URI decodeLink(@Nonnull Class<? extends AbstractRenderable> renderable) {
        return decodeLink(renderable, null);
    }

    @Override
    public URI decodeLink(@Nonnull Class<? extends AbstractRenderable> renderable, UUID versionId) {
        return decodeLink(renderable, versionId, null);
    }

    @Override
    public URI decodeLink(@Nonnull Class<? extends AbstractRenderable> renderable, UUID versionId, UUID subroutineId) {
        return applicationContext.getLimberApplicationConfiguration().getRequestMapperDeque().resolve(
                renderable, versionId, subroutineId);
    }

    @Nonnull
    @Override
    public UUID register(@Nonnull AbstractRenderable renderable) {
        throw new NotYetImplementedException();
    }

    @Nonnull
    @Override
    public UUID register(@Nonnull Class<? extends AbstractRenderable> renderable) {
        throw new NotYetImplementedException();
    }
}
