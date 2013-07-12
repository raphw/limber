package no.kantega.lab.limber.kernel.container;

import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.creator.IInstanceCreator;
import no.kantega.lab.limber.kernel.request.IRequestMapping;

import javax.annotation.Nonnull;
import java.util.UUID;

public abstract class AbstractInstanceContainer implements IInstanceContainer {

    private final IInstanceContainer parent;

    protected AbstractInstanceContainer() {
        this(null);
    }

    protected AbstractInstanceContainer(IInstanceContainer parent) {
        this.parent = parent;
    }

    @Override
    public UUID store(@Nonnull String sessionId, @Nonnull AbstractRenderable renderable) {
        if (parent == null) throw new IllegalStateException();
        return parent.store(sessionId, renderable);
    }

    @Override
    public AbstractRenderable storeBlockingIfAbsent(@Nonnull IRequestMapping requestMapping, @Nonnull IInstanceCreator instanceCreator) {
        if (parent == null) throw new IllegalStateException();
        return parent.storeBlockingIfAbsent(requestMapping, instanceCreator);
    }

    @Override
    public AbstractRenderable remove(@Nonnull IRequestMapping requestMapping) {
        if (parent == null) throw new IllegalStateException();
        return parent.remove(requestMapping);
    }

    @Override
    public AbstractRenderable resolve(@Nonnull IRequestMapping requestMapping, @Nonnull IInstanceCreator instanceCreator) {
        if (parent == null) throw new IllegalStateException();
        return parent.remove(requestMapping);
    }

    @Override
    public IInstanceContainer getParent() {
        return parent;
    }
}
