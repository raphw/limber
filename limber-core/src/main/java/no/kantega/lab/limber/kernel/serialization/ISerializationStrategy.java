package no.kantega.lab.limber.kernel.serialization;

import javax.annotation.Nonnull;
import java.io.InputStream;
import java.io.OutputStream;

public interface ISerializationStrategy {

    void serialize(@Nonnull OutputStream outputStream, @Nonnull Object value);

    Object deserialize(@Nonnull InputStream inputStream);
}
