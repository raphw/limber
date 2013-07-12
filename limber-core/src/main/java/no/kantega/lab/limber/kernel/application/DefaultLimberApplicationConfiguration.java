package no.kantega.lab.limber.kernel.application;

import no.kantega.lab.limber.kernel.container.IInstanceContainer;
import no.kantega.lab.limber.kernel.creator.IInstanceCreator;
import no.kantega.lab.limber.kernel.mapper.IRequestMapper;

import javax.annotation.Nonnull;
import java.util.Deque;

public class DefaultLimberApplicationConfiguration implements ILimberApplicationConfiguration {

    private final ILimberPageRegister limberPageRegister;
    private final Deque<IRequestMapper> requestInterpreters;
    private final IInstanceContainer topInstanceContainer;
    private final IInstanceCreator instanceCreator;

    public DefaultLimberApplicationConfiguration(@Nonnull ILimberPageRegister limberPageRegister, @Nonnull Deque<IRequestMapper> requestInterpreters,
                                                 @Nonnull IInstanceContainer topInstanceContainer, @Nonnull IInstanceCreator instanceCreator) {
        this.limberPageRegister = limberPageRegister;
        this.requestInterpreters = requestInterpreters;
        this.topInstanceContainer = topInstanceContainer;
        this.instanceCreator = instanceCreator;
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
    public IInstanceContainer getInstanceContainer() {
        return topInstanceContainer;
    }

    @Nonnull
    @Override
    public IInstanceCreator getInstanceCreator() {
        return instanceCreator;
    }
}
