package no.kantega.lab.limber.kernel.container;

import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.creator.IInstanceCreationMapper;
import no.kantega.lab.limber.kernel.request.IRequestMapping;

import javax.annotation.Nonnull;
import java.util.UUID;

class CreatorInstanceKey extends InstanceKey {

    private final IInstanceCreationMapper instanceCreationMapper;

    private final IRequestMapping requestMapping;

    CreatorInstanceKey(@Nonnull IInstanceCreationMapper instanceCreationMapper,
                       @Nonnull IRequestMapping requestMapping, @Nonnull String sessionId,
                       @Nonnull Class<? extends AbstractRenderable> renderableClass, UUID versionNumber) {
        super(sessionId, renderableClass, versionNumber);
        this.instanceCreationMapper = instanceCreationMapper;
        this.requestMapping = requestMapping;
    }

    @Nonnull
    IInstanceCreationMapper getInstanceCreationMapper() {
        return instanceCreationMapper;
    }

    @Nonnull
    IRequestMapping getRequestMapping() {
        return requestMapping;
    }
}
