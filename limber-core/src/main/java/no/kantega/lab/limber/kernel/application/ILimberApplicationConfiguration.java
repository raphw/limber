package no.kantega.lab.limber.kernel.application;

import no.kantega.lab.limber.kernel.creator.IInstanceCreationMapper;
import no.kantega.lab.limber.kernel.mapper.IRequestMapper;
import no.kantega.lab.limber.kernel.serialization.ISerializationStrategy;

import javax.annotation.Nonnull;
import java.util.Deque;

public interface ILimberApplicationConfiguration {

    @Nonnull
    ILimberPageRegister getLimberPageRegister();

    @Nonnull
    Deque<IRequestMapper> getRequestInterpreters();

    @Nonnull
    IInstanceCreationMapper getInstanceCreationMapper();

    ISerializationStrategy getSerializationStrategy();

    @Nonnull
    ILimberApplicationConfiguration setSerializationStrategy(ISerializationStrategy serializationStrategy);

    @Nonnull
    ILimberApplicationConfiguration validate();
}
