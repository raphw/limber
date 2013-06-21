package no.kantega.lab.limber.servlet.request.container;

import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.context.IRequestMapping;
import no.kantega.lab.limber.servlet.request.creator.IInstanceCreator;
import no.kantega.lab.limber.servlet.request.standard.RedirectionResponse;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryContainer extends AbstractInstanceContainer {

    private final Map<InstanceKey, IRenderable> memoryPageStore;

    public InMemoryContainer() {
        this(null);
    }

    public InMemoryContainer(IInstanceContainer parent) {
        super(parent);
        this.memoryPageStore = new HashMap<InstanceKey, IRenderable>();
    }

    @Override
    public IRenderable resolve(@Nonnull IRequestMapping requestMapping, @Nonnull IInstanceCreator instanceCreator) {

        // Request is not versioned.
        if (!requestMapping.isVersioned()) {
            IRenderable renderable = instanceCreator.create(requestMapping);
            UUID uuid = store(requestMapping, renderable);
            return new RedirectionResponse(requestMapping.getRenderableClass(), uuid);
        }

        IRenderable renderable = memoryPageStore.get(new InstanceKey(
                requestMapping.getSessionId(),
                requestMapping.getRenderableClass(),
                requestMapping.getVersionId()));

        // Request was actually versioned.
        if (renderable != null) {
            return renderable;
        } else {
            return super.resolve(requestMapping, instanceCreator);
        }
    }

    @Override
    public UUID store(@Nonnull IRequestMapping requestMapping, @Nonnull IRenderable renderable) {
        UUID versionNumber = UUID.randomUUID();
        memoryPageStore.put(new InstanceKey(
                requestMapping.getSessionId(),
                renderable.getClass(),
                versionNumber), renderable);
        return versionNumber;
    }

}