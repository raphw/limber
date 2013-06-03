package no.kantega.lab.limber.servlet.request.container;

import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.request.ILimberRequest;
import no.kantega.lab.limber.servlet.request.creator.IInstanceCreator;

import java.util.UUID;

public interface IInstanceContainer {

    IRenderable resolve(ILimberRequest limberRequest, IInstanceCreator instanceCreator);

    UUID store(ILimberRequest request, IRenderable renderable);

    IInstanceContainer getParent();

}
