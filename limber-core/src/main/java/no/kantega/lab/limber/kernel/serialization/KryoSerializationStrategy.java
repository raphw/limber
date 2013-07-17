package no.kantega.lab.limber.kernel.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.objenesis.strategy.SerializingInstantiatorStrategy;

import javax.annotation.Nonnull;
import java.io.InputStream;
import java.io.OutputStream;

public class KryoSerializationStrategy implements ISerializationStrategy {

    private final Kryo kryo;

    public KryoSerializationStrategy() {
        this.kryo = new Kryo();
        kryo.setDefaultSerializer(FieldAnnotationDisregardingSerializer.class);
        kryo.setInstantiatorStrategy(new SerializingInstantiatorStrategy());
    }

    @Override
    public void serialize(@Nonnull OutputStream outputStream, @Nonnull Object value) {
        Output output = new Output(outputStream);
        try {
            kryo.writeClassAndObject(output, value);
        } finally {
            output.flush();
        }
    }

    @Override
    public Object deserialize(@Nonnull InputStream inputStream) {
        return kryo.readClassAndObject(new Input(inputStream));
    }
}
