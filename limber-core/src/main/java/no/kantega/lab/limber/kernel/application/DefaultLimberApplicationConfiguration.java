package no.kantega.lab.limber.kernel.application;

import no.kantega.lab.limber.kernel.container.DefaultInstanceContainerStack;
import no.kantega.lab.limber.kernel.container.IInstanceContainerStack;
import no.kantega.lab.limber.kernel.creator.DefaultInstanceCreationMapper;
import no.kantega.lab.limber.kernel.creator.IInstanceCreationMapper;
import no.kantega.lab.limber.kernel.mapper.IRequestMapper;

import javax.annotation.Nonnull;
import java.util.Deque;
import java.util.LinkedList;

public class DefaultLimberApplicationConfiguration implements ILimberApplicationConfiguration {

    private final Deque<IRequestMapper> requestInterpreters;
    private final IInstanceContainerStack instanceContainerStack;
    private final IInstanceCreationMapper instanceCreationMapper;

    private final ILimberPageRegister limberPageRegister;

    public DefaultLimberApplicationConfiguration() {
        this.requestInterpreters = new LinkedList<IRequestMapper>();
        this.instanceCreationMapper = new DefaultInstanceCreationMapper();
        this.instanceContainerStack = new DefaultInstanceContainerStack(instanceCreationMapper);
        this.limberPageRegister = new DefaultLimberPageRegister(requestInterpreters);
    }

    @Nonnull
    @Override
    public ILimberPageRegister getLimberPageRegister() {
        return limberPageRegister;
    }

    @Nonnull
    @Override
    public Deque<IRequestMapper> getRequestInterpreters() {
        return requestInterpreters;
    }

    @Nonnull
    @Override
    public IInstanceContainerStack getInstanceContainerStack() {
        return instanceContainerStack;
    }

    @Nonnull
    @Override
    public IInstanceCreationMapper getInstanceCreationMapper() {
        return instanceCreationMapper;
    }

    @Nonnull
    @Override
    public ILimberApplicationConfiguration validate() {
        if (requestInterpreters.size() == 0
                || instanceContainerStack.peek() == null
                || instanceCreationMapper.size() == 0) {
            throw new IllegalStateException();
        }
        return this;
    }
}
