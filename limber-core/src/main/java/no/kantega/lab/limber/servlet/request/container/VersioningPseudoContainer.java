package no.kantega.lab.limber.servlet.request.container;

import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.context.DefaultRequestMapping;
import no.kantega.lab.limber.servlet.context.IRequestMapping;
import no.kantega.lab.limber.servlet.meta.PageVersioning;
import no.kantega.lab.limber.servlet.request.creator.IInstanceCreator;

import javax.annotation.Nonnull;
import java.util.UUID;

public class VersioningPseudoContainer extends AbstractInstanceContainer {

    private final UUID pseudoVersionUUID;

    public VersioningPseudoContainer(@Nonnull IInstanceContainer parent) {
        super(parent);
        pseudoVersionUUID = UUID.randomUUID();
    }

    @Override
    public IRenderable resolve(@Nonnull IRequestMapping requestMapping, @Nonnull IInstanceCreator instanceCreator) {

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
