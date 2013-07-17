package no.kantega.lab.limber.kernel.application;

import no.kantega.lab.limber.kernel.clone.ICloningStrategy;
import no.kantega.lab.limber.kernel.container.IInstanceContainerStack;
import no.kantega.lab.limber.kernel.creator.IInstanceCreatorCollection;
import no.kantega.lab.limber.kernel.mapper.IRequestMapperDeque;
import no.kantega.lab.limber.kernel.serialization.ISerializationStrategy;

import javax.annotation.Nonnull;
import java.io.File;

public interface ILimberApplicationConfiguration {

    @Nonnull
    IRequestMapperDeque getRequestMapperDeque();

    @Nonnull
    IInstanceContainerStack getInstanceContainerStack();

    @Nonnull
    IInstanceCreatorCollection getInstanceCreatorCollection();

    ISerializationStrategy getSerializationStrategy();

    ISerializationStrategy setSerializationStrategy(ISerializationStrategy serializationStrategy);

    ICloningStrategy getCloningStrategy();

    ICloningStrategy setCloningStrategy(ICloningStrategy cloningStrategy);

    File getApplicationFolder();

    File setApplicationFolder(File applicationFolder);

    String getApplicationKeyWord();

    String setApplicationKeyWord(CharSequence keyWord);

    @Nonnull
    ILimberApplicationConfiguration validate(boolean lock);
}
