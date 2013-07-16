package no.kantega.lab.limber.kernel.container;

import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.request.IRequestMapping;
import no.kantega.lab.limber.util.IStack;

import javax.annotation.Nonnull;

public interface IInstanceContainerStack extends IStack<IInstanceContainer> {

    AbstractRenderable resolve(@Nonnull IRequestMapping requestMapping);
}
