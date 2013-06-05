package no.kantega.lab.limber.servlet.request.container;

import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.request.ILimberRequest;
import no.kantega.lab.limber.servlet.request.creator.IInstanceCreator;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface IInstanceContainer {

    IRenderable resolve(@Nonnull ILimberRequest limberRequest, @Nonnull IInstanceCreator instanceCreator);

    UUID store(@Nonnull ILimberRequest request, @Nonnull IRenderable renderable);

    IInstanceContainer getParent();

}
