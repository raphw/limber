package no.kantega.lab.limber.util;

import javax.annotation.Nonnull;
import java.util.List;

public interface IStack<T> {

    void push(T element);

    void pushAll(@Nonnull List<T> elements);

    T pop();

    T peek();
}
