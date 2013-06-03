package no.kantega.lab.limber.util;

public interface IStack<T> {

    void push(T object);
    T pop();
    T peek();

}
