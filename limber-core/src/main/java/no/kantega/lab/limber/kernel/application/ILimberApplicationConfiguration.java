package no.kantega.lab.limber.kernel.application;

import no.kantega.lab.limber.kernel.container.IInstanceContainerStack;
import no.kantega.lab.limber.kernel.creator.IInstanceCreationMapper;
import no.kantega.lab.limber.kernel.mapper.IRequestMapper;

import javax.annotation.Nonnull;
import java.util.Deque;

public interface ILimberApplicationConfiguration {

    @Nonnull
    ILimberPageRegister getLimberPageRegister();

    @Nonnull
    Deque<IRequestMapper> getRequestInterpreters();

    @Nonnull
    IInstanceContainerStack getInstanceContainerStack();

    @Nonnull
    IInstanceCreationMapper getInstanceCreationMapper();

    @Nonnull
    ILimberApplicationConfiguration validate();
}
