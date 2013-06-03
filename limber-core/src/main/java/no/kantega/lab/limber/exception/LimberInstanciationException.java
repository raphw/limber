package no.kantega.lab.limber.exception;

import no.kantega.lab.limber.servlet.IRenderable;

public class LimberInstanciationException extends RuntimeException {

    private final Class<? extends IRenderable> clazz;

    public LimberInstanciationException(Class<? extends IRenderable> clazz, Throwable cause) {
        super("Cannot instantiate class", cause);
        this.clazz = clazz;
    }

    public Class<? extends IRenderable> getClazz() {
        return clazz;
    }
}
