package no.kantega.lab.limber.kernel.container;


import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.creator.IInstanceCreator;
import no.kantega.lab.limber.kernel.meta.PageVersioning;
import no.kantega.lab.limber.kernel.request.DefaultRequestMapping;
import no.kantega.lab.limber.kernel.request.IRequestMapping;

import javax.annotation.Nonnull;
import java.util.UUID;

public class VersioningPseudoContainer extends AbstractInstanceContainer {

    private final UUID pseudoVersionUUID;

    public VersioningPseudoContainer(@Nonnull IInstanceContainer parent) {
        super(parent);
        pseudoVersionUUID = UUID.randomUUID();
    }

    @Override
    public AbstractRenderable resolve(@Nonnull IRequestMapping requestMapping, @Nonnull IInstanceCreator instanceCreator) {

        PageVersioning pageVersioning = requestMapping.getRenderableClass().getAnnotation(PageVersioning.class);

        switch (pageVersioning.value()) {
            case NONE:
                return instanceCreator.create(requestMapping);
            case SINGLE:
                requestMapping = new DefaultRequestMapping(
                        requestMapping.getSessionId(),
                        requestMapping.getRenderableClass(),
                        pseudoVersionUUID,
                        requestMapping.getSubroutineId());
                break;
        }

        return getParent().resolve(requestMapping, instanceCreator);
    }
}
