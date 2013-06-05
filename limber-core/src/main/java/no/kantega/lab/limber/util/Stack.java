package no.kantega.lab.limber.util;

import javax.annotation.Nonnull;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Stack<T> implements IStack<T> {

    private final Deque<T> deque = new ArrayDeque<T>();

    @Override
    public void push(T element) {
        deque.addFirst(element);
    }

    @Override
    public void pushAll(@Nonnull List<T> elements) {
        for (T element : elements) {
            deque.addFirst(element);
        }
    }

    @Override
    public T pop() {
        return deque.removeFirst();
    }

    @Override
    public T peek() {
        return deque.getFirst();
    }
}
