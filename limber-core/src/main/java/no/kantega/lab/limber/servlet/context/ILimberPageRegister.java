package no.kantega.lab.limber.servlet.context;

import no.kantega.lab.limber.servlet.IRenderable;

import javax.annotation.Nonnull;
import java.net.URI;
import java.util.UUID;

public interface ILimberPageRegister {

    URI decodeLink(@Nonnull IRenderable renderable);

    URI decodeLink(@Nonnull UUID subroutineId);

    URI decodeLink(@Nonnull Class<? extends IRenderable> renderable);

    URI decodeLink(@Nonnull Class<? extends IRenderable> renderable, UUID versionId);

    URI decodeLink(@Nonnull Class<? extends IRenderable> renderable, UUID versionId, UUID subroutineId);

    @Nonnull
    UUID register(@Nonnull IRenderable renderable);

    @Nonnull
    UUID register(@Nonnull Class<? extends IRenderable> renderable);
}
