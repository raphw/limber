package no.kantega.lab.limber.kernel.application.configuration;

import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.container.IInstanceContainer;
import no.kantega.lab.limber.kernel.request.IRequestMapping;
import no.kantega.lab.limber.util.IStack;

import javax.annotation.Nonnull;

public interface IInstanceContainerStack extends IInstanceContainer, IStack<IInstanceContainer> {

    AbstractRenderable resolve(@Nonnull IRequestMapping requestMapping);

    AbstractRenderable storeBlockingIfAbsent(@Nonnull IRequestMapping requestMapping);

}
