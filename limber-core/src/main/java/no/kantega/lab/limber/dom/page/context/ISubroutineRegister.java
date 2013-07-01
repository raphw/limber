package no.kantega.lab.limber.dom.page.context;

import no.kantega.lab.limber.dom.page.IEventTriggerable;

import javax.annotation.Nonnull;

public interface ISubroutineRegister {

    @Nonnull
    ISubroutineRegister registerSubroutine(@Nonnull IEventTriggerable eventTriggerable);
}
