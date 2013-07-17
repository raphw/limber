package no.kantega.lab.limber.kernel.container;

import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.creator.IInstanceCreator;
import no.kantega.lab.limber.kernel.request.IRequestMapping;
import no.kantega.lab.limber.kernel.store.IStoreCollection;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface IInstanceContainer {

    AbstractRenderable resolve(@Nonnull IRequestMapping requestMapping, @Nonnull IInstanceCreator instanceCreator, @Nonnull IStoreCollection storeCollection);

    UUID store(@Nonnull String sessionId, @Nonnull AbstractRenderable renderable, @Nonnull IStoreCollection storeCollection);

    AbstractRenderable storeBlockingIfAbsent(@Nonnull IRequestMapping requestMapping, @Nonnull IInstanceCreator instanceCreator, @Nonnull IStoreCollection storeCollection);

    AbstractRenderable remove(@Nonnull IRequestMapping requestMapping, @Nonnull IStoreCollection storeCollection);

    IInstanceContainer getParent();

    IInstanceContainer setParent(IInstanceContainer parent);
}
