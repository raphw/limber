package no.kantega.lab.limber.exception;

import no.kantega.lab.limber.kernel.AbstractRenderable;

public class LimberInstanciationException extends RuntimeException {

    private final Class<? extends AbstractRenderable> clazz;

    public LimberInstanciationException(Class<? extends AbstractRenderable> clazz, Throwable cause) {
        super("Cannot instantiate class", cause);
        this.clazz = clazz;
    }

    public Class<? extends AbstractRenderable> getClazz() {
        return clazz;
    }
}
