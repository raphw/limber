package no.kantega.lab.limber.kernel.application.configuration;

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

    File getApplicationFolder();

    File setApplicationFolder(File applicationFolder);

    String getApplicationKeyWord();

    String setApplicationKeyWord(CharSequence keyWord);

    @Nonnull
    ILimberApplicationConfiguration validate(boolean lock);
}
