package no.kantega.lab.limber.kernel.container;

import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.request.IRequestMapping;
import no.kantega.lab.limber.kernel.store.IStoreCollection;
import no.kantega.lab.limber.util.IStack;

import javax.annotation.Nonnull;

public interface IInstanceContainerStack extends IInstanceContainer, IStack<IInstanceContainer> {

    AbstractRenderable resolve(@Nonnull IRequestMapping requestMapping, @Nonnull IStoreCollection storeCollection);

    AbstractRenderable storeBlockingIfAbsent(@Nonnull IRequestMapping requestMapping, @Nonnull IStoreCollection storeCollection);

}
