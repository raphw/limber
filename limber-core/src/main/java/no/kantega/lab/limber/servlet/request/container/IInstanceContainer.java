package no.kantega.lab.limber.servlet.request.container;

import no.kantega.lab.limber.servlet.AbstractRenderable;
import no.kantega.lab.limber.servlet.context.IRequestMapping;
import no.kantega.lab.limber.servlet.request.creator.IInstanceCreator;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface IInstanceContainer {

    AbstractRenderable resolve(@Nonnull IRequestMapping requestMapping, @Nonnull IInstanceCreator instanceCreator);

    UUID store(@Nonnull String sessionId, @Nonnull AbstractRenderable renderable);

    AbstractRenderable storeBlockingIfAbsent(@Nonnull IRequestMapping requestMapping, @Nonnull IInstanceCreator instanceCreator);

    AbstractRenderable remove(@Nonnull IRequestMapping requestMapping);

    IInstanceContainer getParent();
}
