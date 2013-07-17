package no.kantega.lab.limber.kernel.application.configuration;

import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.container.AbstractInstanceContainer;
import no.kantega.lab.limber.kernel.container.IInstanceContainer;
import no.kantega.lab.limber.kernel.request.IRequestMapping;

import javax.annotation.Nonnull;
import java.util.List;

public class DefaultInstanceContainerStack extends AbstractInstanceContainer implements IInstanceContainerStack {

    private final IInstanceCreatorCollection instanceCreatorCollection;

    public DefaultInstanceContainerStack(@Nonnull IInstanceCreatorCollection instanceCreatorCollection) {
        this.instanceCreatorCollection = instanceCreatorCollection;
    }

    @Override
    public AbstractRenderable resolve(@Nonnull IRequestMapping requestMapping) {
        return resolve(requestMapping, instanceCreatorCollection);
    }

    @Override
    public AbstractRenderable storeBlockingIfAbsent(@Nonnull IRequestMapping requestMapping) {
        return storeBlockingIfAbsent(requestMapping, instanceCreatorCollection);
    }

    @Override
    public void push(@Nonnull IInstanceContainer instanceContainer) {
        instanceContainer.setParent(getParent());
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
