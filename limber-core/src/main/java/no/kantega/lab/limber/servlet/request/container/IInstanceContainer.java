package no.kantega.lab.limber.servlet.request.container;

import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.context.IRequestMapping;
import no.kantega.lab.limber.servlet.request.creator.IInstanceCreator;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface IInstanceContainer {

    IRenderable resolve(@Nonnull IRequestMapping requestMapping, @Nonnull IInstanceCreator instanceCreator);

    UUID store(@Nonnull IRequestMapping requestMapping, @Nonnull IRenderable renderable);

    IRenderable remove(@Nonnull IRequestMapping requestMapping);

    IInstanceContainer getParent();
}
