package no.kantega.lab.limber.kernel.clone;

public interface ICloningStrategy {

    <T> T deepClone(T object);
}
