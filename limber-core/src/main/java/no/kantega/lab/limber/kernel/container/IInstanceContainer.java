package no.kantega.lab.limber.kernel.container;

import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.creator.IInstanceCreator;
import no.kantega.lab.limber.kernel.request.IRequestMapping;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface IInstanceContainer {

    AbstractRenderable resolve(@Nonnull IRequestMapping requestMapping, @Nonnull IInstanceCreator instanceCreator);

    UUID store(@Nonnull String sessionId, @Nonnull AbstractRenderable renderable);

    AbstractRenderable storeBlockingIfAbsent(@Nonnull IRequestMapping requestMapping, @Nonnull IInstanceCreator instanceCreator);

    AbstractRenderable remove(@Nonnull IRequestMapping requestMapping);

    IInstanceContainer getParent();

    IInstanceContainer setParent(IInstanceContainer parent);
}
