package no.kantega.lab.limber.kernel.container;

import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.serialization.ISerializationStrategy;
import no.kantega.lab.limber.util.PersistingCache;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class PagePersistingCache extends PersistingCache<InstanceKey, AbstractRenderable> {

    private static final String DEFAULT_KEY = "default";

    private final ISerializationStrategy serializationStrategy;

    public PagePersistingCache(@Nonnull File directory, @Nonnull ISerializationStrategy serializationStrategy) {
        super(directory);
        this.serializationStrategy = serializationStrategy;
    }

    @Nonnull
    @Override
    protected List<String> directoryFor(@Nonnull InstanceKey key) {
        return Arrays.asList(key.getSessionId(), key.getRenderableClass().getCanonicalName(),
                key.getVersionNumber() == null ? DEFAULT_KEY : key.getVersionNumber().toString());
    }

    @Override
    protected void persist(@Nonnull InstanceKey key, @Nonnull AbstractRenderable value, @Nonnull OutputStream outputStream) throws IOException {
        serializationStrategy.serialize(outputStream, value);
    }

    @Nonnull
    @Override
    protected AbstractRenderable readPersisted(@Nonnull InstanceKey key, @Nonnull InputStream inputStream) throws IOException {
        return (AbstractRenderable) serializationStrategy.deserialize(inputStream);
    }

    @Override
    protected boolean isPersist(@Nonnull InstanceKey key) {
        return Serializable.class.isAssignableFrom(key.getRenderableClass());
    }
}
