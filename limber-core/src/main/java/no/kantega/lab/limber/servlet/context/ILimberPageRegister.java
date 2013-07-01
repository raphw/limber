package no.kantega.lab.limber.servlet.context;

import no.kantega.lab.limber.servlet.AbstractRenderable;

import javax.annotation.Nonnull;
import java.net.URI;
import java.util.UUID;

public interface ILimberPageRegister {

    URI decodeLink(@Nonnull AbstractRenderable renderable);

    URI decodeLink(@Nonnull UUID subroutineId);

    URI decodeLink(@Nonnull Class<? extends AbstractRenderable> renderable);

    URI decodeLink(@Nonnull Class<? extends AbstractRenderable> renderable, UUID versionId);

    URI decodeLink(@Nonnull Class<? extends AbstractRenderable> renderable, UUID versionId, UUID subroutineId);

    @Nonnull
    UUID register(@Nonnull AbstractRenderable renderable);

    @Nonnull
    UUID register(@Nonnull Class<? extends AbstractRenderable> renderable);
}
