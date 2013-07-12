package no.kantega.lab.limber.exception;


public class LimberRequestMappingException extends RuntimeException {

    private final Class<?> clazz;

    public LimberRequestMappingException(Class clazz) {
        super("Class does not implement AbstractRenderable");
        this.clazz = clazz;
    }

    public Class<?> getConflictingClass() {
        return clazz;
    }
}