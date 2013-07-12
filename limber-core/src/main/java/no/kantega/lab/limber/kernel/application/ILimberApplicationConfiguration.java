package no.kantega.lab.limber.kernel.application;

import no.kantega.lab.limber.kernel.container.IInstanceContainer;
import no.kantega.lab.limber.kernel.creator.IInstanceCreator;
import no.kantega.lab.limber.kernel.mapper.IRequestMapper;

import java.util.Deque;

public interface ILimberApplicationConfiguration {

    ILimberPageRegister getLimberPageRegister();

    Deque<IRequestMapper> getRequestInterpreters();

    IInstanceContainer getInstanceContainer();

    IInstanceCreator getInstanceCreator();
}
