package no.kantega.lab.limber.kernel.application;

import no.kantega.lab.limber.kernel.clone.ICloningStrategy;
import no.kantega.lab.limber.kernel.container.DefaultInstanceContainerStack;
import no.kantega.lab.limber.kernel.container.IInstanceContainerStack;
import no.kantega.lab.limber.kernel.creator.DefaultInstanceCreatorCollection;
import no.kantega.lab.limber.kernel.creator.IInstanceCreatorCollection;
import no.kantega.lab.limber.kernel.mapper.DefaultRequestMapperDeque;
import no.kantega.lab.limber.kernel.mapper.IRequestMapperDeque;
import no.kantega.lab.limber.kernel.serialization.ISerializationStrategy;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.UUID;

public class DefaultLimberApplicationConfiguration implements ILimberApplicationConfiguration {

    private final String applicationId;

    private final IRequestMapperDeque requestMappers;
    private final IInstanceCreatorCollection instanceCreatorCollection;
    private final IInstanceContainerStack containerStack;

    private ISerializationStrategy serializationStrategy;
    private ICloningStrategy cloningStrategy;

    private File applicationFolder;
    private String applicationKeyWord;

    private boolean locked;

    public DefaultLimberApplicationConfiguration(@Nonnull UUID applicationId) {
        this.requestMappers = new DefaultRequestMapperDeque();
        this.instanceCreatorCollection = new DefaultInstanceCreatorCollection();
        this.containerStack = new DefaultInstanceContainerStack(instanceCreatorCollection);
        this.locked = false;
        this.applicationId = applicationId.toString();
    }

    @Nonnull
    @Override
    public IRequestMapperDeque getRequestMapperDeque() {
        return requestMappers;
    }

    @Nonnull
    @Override
    public IInstanceContainerStack getInstanceContainerStack() {
        return containerStack;
    }

    @Nonnull
    @Override
    public IInstanceCreatorCollection getInstanceCreatorCollection() {
        return instanceCreatorCollection;
    }

    @Override
    public ISerializationStrategy getSerializationStrategy() {
        return serializationStrategy;
    }

    @Override
    public ISerializationStrategy setSerializationStrategy(ISerializationStrategy serializationStrategy) {
        try {
            return serializationStrategy;
        } finally {
            this.serializationStrategy = checkLock(serializationStrategy);
        }
    }

    @Override
    public ICloningStrategy getCloningStrategy() {
        return cloningStrategy;
    }

    @Override
    public ICloningStrategy setCloningStrategy(ICloningStrategy cloningStrategy) {
        try {
            return cloningStrategy;
        } finally {
            this.cloningStrategy = checkLock(cloningStrategy);
        }
    }

    @Override
    public File getApplicationFolder() {
        return applicationFolder == null ? null : new File(applicationFolder, applicationId);
    }

    @Override
    public File setApplicationFolder(File applicationFolder) {
        try {
            return applicationFolder;
        } finally {
            this.applicationFolder = checkLock(applicationFolder);
        }
    }

    @Override
    public String getApplicationKeyWord() {
        return applicationKeyWord;
    }

    @Override
    public String setApplicationKeyWord(CharSequence keyWord) {
        try {
            return applicationKeyWord;
        } finally {
            this.applicationKeyWord = checkLock(keyWord == null ? null : keyWord.toString());
        }
    }

    private <T> T checkLock(T value) {
        if (locked) throw new IllegalStateException();
        return value;
    }

    @Nonnull
    @Override
    public ILimberApplicationConfiguration validate(boolean lock) {
        if (lock) locked = true;
        if (requestMappers.size() == 0
                || containerStack.peek() == null
                || !instanceCreatorCollection.isObjectCreatable()
                || serializationStrategy == null
                || cloningStrategy == null
                || !makeAndCheck(getApplicationFolder())
                || isInvalidName(applicationKeyWord)) {
            throw new IllegalStateException();
        }
        return this;
    }

    private boolean makeAndCheck(File applicationFolder) {
        return applicationFolder != null
                && applicationFolder.mkdirs()
                && applicationFolder.exists()
                && applicationFolder.isDirectory()
                && applicationFolder.canRead()
                && applicationFolder.canWrite();
    }

    private boolean isInvalidName(String name) {
        if (StringUtils.isBlank(name)) return true;
        if (name.length() > 30) return true;
        if (!StringUtils.isAlphanumeric(name)) return true;
        char firstLetter = name.charAt(0);
        return firstLetter < 'a' || firstLetter > 'Z';
    }
}
