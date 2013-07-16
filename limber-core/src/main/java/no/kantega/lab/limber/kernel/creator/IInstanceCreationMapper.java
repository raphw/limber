package no.kantega.lab.limber.kernel.creator;

import no.kantega.lab.limber.kernel.AbstractRenderable;

import javax.annotation.Nonnull;

public interface IInstanceCreationMapper extends IInstanceCreator {

    @Nonnull
    IInstanceCreationMapper addCreator(@Nonnull Class<? extends AbstractRenderable> createdClass,
                                       @Nonnull IInstanceCreator instanceCreator);

    @Nonnull
    IInstanceCreationMapper removeCreator(@Nonnull IInstanceCreator instanceCreator);

    int size();
}
