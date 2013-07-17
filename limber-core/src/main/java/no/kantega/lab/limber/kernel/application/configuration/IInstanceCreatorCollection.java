package no.kantega.lab.limber.kernel.application.configuration;

import no.kantega.lab.limber.kernel.creator.IInstanceCreator;

import javax.annotation.Nonnull;

public interface IInstanceCreatorCollection extends IInstanceCreator {

    @Nonnull
    IInstanceCreatorCollection add(@Nonnull Class<?> minimumTargetClass,
                                   @Nonnull IInstanceCreator instanceCreator);

    boolean remove(@Nonnull IInstanceCreator instanceCreator);

    boolean isObjectCreatable();

}
