package no.kantega.lab.limber.servlet.request.container;

import no.kantega.lab.limber.servlet.AbstractRenderable;
import no.kantega.lab.limber.servlet.context.IRequestMapping;
import no.kantega.lab.limber.servlet.request.creator.IInstanceCreator;
import no.kantega.lab.limber.servlet.request.responses.RedirectionResponse;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryContainer extends AbstractInstanceContainer {

    private final Map<InstanceKey, AbstractRenderable> memoryPageStore;

    public InMemoryContainer() {
        this(null);
    }

    public InMemoryContainer(IInstanceContainer parent) {
        super(parent);
        this.memoryPageStore = new HashMap<InstanceKey, AbstractRenderable>();
    }

    @Override
    public AbstractRenderable resolve(@Nonnull IRequestMapping requestMapping, @Nonnull IInstanceCreator instanceCreator) {

        // Request is not versioned.
        if (!requestMapping.isVersioned()) {
            AbstractRenderable renderable = instanceCreator.create(requestMapping);
            UUID uuid = store(requestMapping, renderable);
            return new RedirectionResponse(requestMapping.getRenderableClass(), uuid);
        }

        AbstractRenderable renderable = memoryPageStore.get(new InstanceKey(
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
    public UUID store(@Nonnull IRequestMapping requestMapping, @Nonnull AbstractRenderable renderable) {
        UUID versionNumber = UUID.randomUUID();
        memoryPageStore.put(new InstanceKey(
                requestMapping.getSessionId(),
                renderable.getClass(),
                versionNumber), renderable);
        return versionNumber;
    }

}