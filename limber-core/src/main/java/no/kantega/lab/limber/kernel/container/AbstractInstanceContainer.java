package no.kantega.lab.limber.kernel.container;

import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.creator.IInstanceCreator;
import no.kantega.lab.limber.kernel.request.IRequestMapping;
import no.kantega.lab.limber.kernel.store.IStoreCollection;

import javax.annotation.Nonnull;
import java.util.UUID;

public abstract class AbstractInstanceContainer implements IInstanceContainer {

    private IInstanceContainer parent;

    protected AbstractInstanceContainer() {
        this(null);
    }

    protected AbstractInstanceContainer(IInstanceContainer parent) {
        this.parent = parent;
    }

    @Override
    public UUID store(@Nonnull String sessionId, @Nonnull AbstractRenderable renderable, @Nonnull IStoreCollection storeCollection) {
        if (parent == null) throw new IllegalStateException();
        return parent.store(sessionId, renderable, storeCollection);
    }

    @Override
    public AbstractRenderable storeBlockingIfAbsent(@Nonnull IRequestMapping requestMapping, @Nonnull IInstanceCreator instanceCreator, @Nonnull IStoreCollection storeCollection) {
        if (parent == null) throw new IllegalStateException();
        return parent.storeBlockingIfAbsent(requestMapping, instanceCreator, storeCollection);
    }

    @Override
    public AbstractRenderable remove(@Nonnull IRequestMapping requestMapping, @Nonnull IStoreCollection storeCollection) {
        if (parent == null) throw new IllegalStateException();
        return parent.remove(requestMapping, storeCollection);
    }

    @Override
    public AbstractRenderable resolve(@Nonnull IRequestMapping requestMapping, @Nonnull IInstanceCreator instanceCreator, @Nonnull IStoreCollection storeCollection) {
        if (parent == null) throw new IllegalStateException();
        return parent.resolve(requestMapping, instanceCreator, storeCollection);
    }

    @Override
    public IInstanceContainer getParent() {
        return parent;
    }

    @Nonnull
    @Override
    public IInstanceContainer setParent(IInstanceContainer parent) {
        this.parent = parent;
        return this;
    }
}
