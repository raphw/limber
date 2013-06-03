package no.kantega.lab.limber.util;

import java.util.ArrayDeque;
import java.util.Deque;

public class Stack<T> implements IStack<T> {

    private final Deque<T> deque = new ArrayDeque<T>();

    @Override
    public void push(T object) {
        deque.addFirst(object);
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
