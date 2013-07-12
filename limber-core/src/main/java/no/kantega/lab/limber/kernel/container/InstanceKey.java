package no.kantega.lab.limber.kernel.container;

import no.kantega.lab.limber.kernel.AbstractRenderable;

import javax.annotation.Nonnull;
import java.util.UUID;

class InstanceKey {

    private final String sessionId;
    private final Class<? extends AbstractRenderable> renderableClass;
    private final UUID versionNumber;

    InstanceKey(
            @Nonnull String sessionId,
            @Nonnull Class<? extends AbstractRenderable> renderableClass,
            UUID versionNumber) {
        this.sessionId = sessionId;
        this.renderableClass = renderableClass;
        this.versionNumber = versionNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InstanceKey that = (InstanceKey) o;

        return renderableClass.equals(that.renderableClass)
                && sessionId.equals(that.sessionId)
                && versionNumber.equals(that.versionNumber);
    }

    @Override
    public int hashCode() {
        int result = sessionId.hashCode();
        result = 31 * result + renderableClass.hashCode();
        result = 31 * result + versionNumber.hashCode();
        return result;
    }

    String getSessionId() {
        return sessionId;
    }

    Class<? extends AbstractRenderable> getRenderableClass() {
        return renderableClass;
    }

    UUID getVersionNumber() {
        return versionNumber;
    }
}
