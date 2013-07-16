package no.kantega.lab.limber.kernel.container;

import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.creator.IInstanceCreationMapper;
import no.kantega.lab.limber.kernel.request.IRequestMapping;
import no.kantega.lab.limber.util.Stack;

import javax.annotation.Nonnull;

public class DefaultInstanceContainerStack extends Stack<IInstanceContainer> implements IInstanceContainerStack {

    private final IInstanceCreationMapper instanceCreationMapper;

    public DefaultInstanceContainerStack(@Nonnull IInstanceCreationMapper instanceCreationMapper) {
        this.instanceCreationMapper = instanceCreationMapper;
    }

    @Override
    public AbstractRenderable resolve(@Nonnull IRequestMapping requestMapping) {
        return peek().resolve(requestMapping, instanceCreationMapper);
    }
}
