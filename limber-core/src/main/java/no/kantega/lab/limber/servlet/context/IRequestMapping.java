package no.kantega.lab.limber.servlet.context;

import no.kantega.lab.limber.servlet.AbstractRenderable;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface IRequestMapping {

    @Nonnull
    String getSessionId();

    @Nonnull
    Class<? extends AbstractRenderable> getRenderableClass();

    UUID getVersionId();

    boolean isVersioned();

    UUID getSubroutineId();

    boolean isSubroutine();
}
