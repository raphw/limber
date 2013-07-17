package no.kantega.lab.limber.kernel.container;

import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.creator.IInstanceCreatorCollection;
import no.kantega.lab.limber.kernel.request.IRequestMapping;
import no.kantega.lab.limber.kernel.store.IStoreCollection;

import javax.annotation.Nonnull;
import java.util.List;

public class DefaultInstanceContainerStack extends AbstractInstanceContainer implements IInstanceContainerStack {

    private final IInstanceCreatorCollection instanceCreatorCollection;

    public DefaultInstanceContainerStack(@Nonnull IInstanceCreatorCollection instanceCreatorCollection) {
        this.instanceCreatorCollection = instanceCreatorCollection;
    }

    @Override
    public AbstractRenderable resolve(@Nonnull IRequestMapping requestMapping, @Nonnull IStoreCollection storeCollection) {
        return resolve(requestMapping, instanceCreatorCollection, storeCollection);
    }

    @Override
    public AbstractRenderable storeBlockingIfAbsent(@Nonnull IRequestMapping requestMapping, @Nonnull IStoreCollection storeCollection) {
        return storeBlockingIfAbsent(requestMapping, instanceCreatorCollection, storeCollection);
    }

    @Override
    public void push(@Nonnull IInstanceContainer instanceContainer) {
        IInstanceContainer deepestParent = instanceContainer;
        while(deepestParent.getParent() != null) {
            deepestParent = deepestParent.getParent();
        }
        deepestParent.setParent(getParent());
        setParent(instanceContainer);
    }

    @Override
    public void pushAll(@Nonnull List<IInstanceContainer> instanceContainers) {
        for (IInstanceContainer instanceContainer : instanceContainers) {
            push(instanceContainer);
        }
    }

    @Override
    public IInstanceContainer pop() {
        IInstanceContainer instanceContainer = getParent();
        setParent(getParent().getParent());
        return instanceContainer;
    }

    @Override
    public IInstanceContainer peek() {
        return getParent();
    }
}
