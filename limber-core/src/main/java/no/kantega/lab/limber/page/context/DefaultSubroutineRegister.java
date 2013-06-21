package no.kantega.lab.limber.page.context;

import no.kantega.lab.limber.page.IEventTriggerable;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.UUID;

public class DefaultSubroutineRegister implements ISubroutineRegister {

    private final Map<UUID, IEventTriggerable> subroutineRegister;

    public DefaultSubroutineRegister(@Nonnull Map<UUID, IEventTriggerable> subroutineRegister) {
        this.subroutineRegister = subroutineRegister;
    }

    @Nonnull
    @Override
    public ISubroutineRegister registerSubroutine(@Nonnull IEventTriggerable eventTriggerable) {
        subroutineRegister.put(eventTriggerable.getUUID(), eventTriggerable);
        return this;
    }
}
