package no.kantega.lab.limber.servlet.request.container;

import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.request.ILimberRequest;
import no.kantega.lab.limber.servlet.request.creator.IInstanceCreator;
import no.kantega.lab.limber.servlet.request.standard.InvalidRequestResponse;
import no.kantega.lab.limber.servlet.request.standard.RedirectionResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryContainer implements IInstanceContainer {

    private final Map<InstanceKey, IRenderable> memoryPageStore;
    private final IInstanceContainer parent;

    public InMemoryContainer() {
        this(null);
    }

    public InMemoryContainer(IInstanceContainer parent) {
        this.parent = parent;
        this.memoryPageStore = new HashMap<InstanceKey, IRenderable>();
    }

    @Override
    public IInstanceContainer getParent() {
        return parent;
    }

    @Override
    public IRenderable resolve(ILimberRequest limberRequest, IInstanceCreator instanceCreator) {

        // Request is not versioned.
        if (!limberRequest.isVersioned()) {
            IRenderable renderable = instanceCreator.create(limberRequest);
            UUID uuid = store(limberRequest, renderable);
            memoryPageStore.put(
                    new InstanceKey(limberRequest.getSessionId(), limberRequest.getRenderableClass(), uuid),
                    renderable);
            return new RedirectionResponse(limberRequest, uuid);
        }

        IRenderable renderable = memoryPageStore.get(new InstanceKey(
                limberRequest.getSessionId(),
                limberRequest.getRenderableClass(),
                limberRequest.getVersionId()));

        // Request was actually versioned.
        if (renderable != null) {
            return renderable;
        }

        return new InvalidRequestResponse();

    }

    @Override
    public UUID store(ILimberRequest limberRequest, IRenderable renderable) {
        UUID versionNumber = UUID.randomUUID();
        memoryPageStore.put(new InstanceKey(
                limberRequest.getSessionId(),
                renderable.getClass(),
                versionNumber), renderable);
        return versionNumber;
    }

    private class InstanceKey {
        private final String sessionId;
        private final Class<? extends IRenderable> renderableClass;
        private final UUID versionNumber;

        private InstanceKey(String sessionId, Class<? extends IRenderable> renderableClass, UUID versionNumber) {
            this.sessionId = sessionId;
            this.renderableClass = renderableClass;
            this.versionNumber = versionNumber;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            InstanceKey that = (InstanceKey) o;

            if (!renderableClass.equals(that.renderableClass)) return false;
            if (!sessionId.equals(that.sessionId)) return false;
            if (!versionNumber.equals(that.versionNumber)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = sessionId.hashCode();
            result = 31 * result + renderableClass.hashCode();
            result = 31 * result + versionNumber.hashCode();
            return result;
        }
    }

}