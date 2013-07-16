package no.kantega.lab.limber.kernel.container;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.util.PersistingCache;
import org.objenesis.strategy.SerializingInstantiatorStrategy;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class PagePersistingCache extends PersistingCache<InstanceKey, AbstractRenderable> {

    private static final Kryo KRYO = new Kryo();

    private static final String DEFAULT_KEY = "default";

    static {
        KRYO.setDefaultSerializer(FieldAnnotationDisregardingSerializer.class);
        KRYO.setInstantiatorStrategy(new SerializingInstantiatorStrategy());
    }

    public PagePersistingCache(@Nonnull File directory) {
        super(directory);
    }

    @Nonnull
    @Override
    protected List<String> directoryFor(@Nonnull InstanceKey key) {
        return Arrays.asList(key.getSessionId(), key.getRenderableClass().getCanonicalName(),
                key.getVersionNumber() == null ? DEFAULT_KEY : key.getVersionNumber().toString());
    }

    @Override
    protected void persist(@Nonnull InstanceKey key, @Nonnull AbstractRenderable value, @Nonnull OutputStream outputStream) throws IOException {
        Output output = new Output(outputStream);
        try {
            KRYO.writeClassAndObject(output, value);
        } finally {
            output.flush();
        }
    }

    @Nonnull
    @Override
    protected AbstractRenderable readPersisted(@Nonnull InstanceKey key, @Nonnull InputStream inputStream) throws IOException {
        Input input = new Input(inputStream);
        return (AbstractRenderable) KRYO.readClassAndObject(input);
    }

    @Override
    protected boolean isPersist(@Nonnull InstanceKey key) {
        return Serializable.class.isAssignableFrom(key.getRenderableClass());
    }
}
