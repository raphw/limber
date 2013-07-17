package no.kantega.lab.limber.kernel.store;

import javax.annotation.Nonnull;
import java.util.concurrent.Callable;

public interface IStore {

    @SuppressWarnings("unused")
    static final class Marker<T> {
        /* empty, only used to allow a generic cast of the return type */
    }

    <T> T get(String key, Callable<T> valueMaker);

    <T> T get(String key, Marker<T> marker);

    Object get(String key);

    @Nonnull
    IStore release();
}
