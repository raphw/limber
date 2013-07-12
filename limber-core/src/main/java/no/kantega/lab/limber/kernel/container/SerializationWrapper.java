package no.kantega.lab.limber.kernel.container;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.objenesis.strategy.SerializingInstantiatorStrategy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

final class SerializationWrapper implements Serializable {

    private static final Kryo KRYO = new Kryo();

    static {
        KRYO.setDefaultSerializer(FieldAnnotationDisregardingSerializer.class);
        KRYO.setInstantiatorStrategy(new SerializingInstantiatorStrategy());
    }

    public static Object wrap(Object object) {
        if (object instanceof Serializable) {
            return new SerializationWrapper(object);
        } else {
            return object;
        }
    }

    public static Object unwrap(Object object) {
        if (object instanceof SerializationWrapper) {
            return ((SerializationWrapper) object).getObject();
        } else {
            return object;
        }
    }

    private Object object;

    public SerializationWrapper(Object object) {
        this.object = object;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        Input input = new Input(objectInputStream);
        object = KRYO.readClassAndObject(input);
        input.close();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        Output output = new Output(objectOutputStream);
        KRYO.writeClassAndObject(output, object);
        output.close();
    }

    public Object getObject() {
        return object;
    }

    @Override
    public String toString() {
        return object == null ? "null" : object.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SerializationWrapper that = (SerializationWrapper) o;

        return !(object != null ? !object.equals(that.object) : that.object != null);

    }

    @Override
    public int hashCode() {
        return object != null ? object.hashCode() : 0;
    }
}
