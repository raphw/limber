package no.kantega.lab.limber.servlet.request.container;

import no.kantega.lab.limber.servlet.AbstractRenderable;
import no.kantega.lab.limber.servlet.context.IRequestMapping;
import no.kantega.lab.limber.servlet.request.creator.IInstanceCreator;

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
    public UUID store(@Nonnull IRequestMapping requestMapping, @Nonnull AbstractRenderable renderable) {
        if (parent == null) throw new IllegalStateException();
        return parent.store(requestMapping, renderable);
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
