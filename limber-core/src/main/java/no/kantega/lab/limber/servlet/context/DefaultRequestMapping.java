package no.kantega.lab.limber.servlet.context;

import no.kantega.lab.limber.servlet.IRenderable;

import javax.annotation.Nonnull;
import java.util.UUID;

public class DefaultRequestMapping implements IRequestMapping {

    private final String sessionId;
    private final Class<? extends IRenderable> renderableClass;
    private final UUID versionId;
    private final UUID subroutineId;

    public DefaultRequestMapping(@Nonnull String sessionId, @Nonnull Class<? extends IRenderable> renderableClass, UUID versionId, UUID subroutineId) {
        this.sessionId = sessionId;
        this.renderableClass = renderableClass;
        this.versionId = versionId;
        this.subroutineId = subroutineId;
    }

    @Nonnull
    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Nonnull
    @Override
    public Class<? extends IRenderable> getRenderableClass() {
        return renderableClass;
    }

    @Override
    public UUID getVersionId() {
        return versionId;
    }

    @Override
    public boolean isVersioned() {
        return versionId != null;
    }

    @Override
    public UUID getSubroutineId() {
        return subroutineId;
    }

    @Override
    public boolean isSubroutine() {
        return subroutineId != null;
    }
}
