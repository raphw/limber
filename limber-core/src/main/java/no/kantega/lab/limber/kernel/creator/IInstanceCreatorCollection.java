package no.kantega.lab.limber.kernel.creator;

import javax.annotation.Nonnull;

public interface IInstanceCreatorCollection extends IInstanceCreator {

    @Nonnull
    IInstanceCreatorCollection add(@Nonnull Class<?> minimumTargetClass,
                                   @Nonnull IInstanceCreator instanceCreator);

    boolean remove(@Nonnull IInstanceCreator instanceCreator);

    boolean isObjectCreatable();

}
