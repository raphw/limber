package no.kantega.lab.limber.page.context;

import no.kantega.lab.limber.page.IEventTriggerable;

import javax.annotation.Nonnull;

public interface ISubroutineRegister {

    @Nonnull
    ISubroutineRegister registerSubroutine(@Nonnull IEventTriggerable eventTriggerable);
}
