package no.kantega.lab.limber.exception;

public class LimberRequestPathConflictException extends RuntimeException {

    private final Class<?> clazz;
    private final Class<?> mappedClass;

    public LimberRequestPathConflictException(String path, Class clazz, Class mappedClass) {
        super(String.format("%s is already mapped to %s", mappedClass.getCanonicalName(), path));
        this.clazz = clazz;
        this.mappedClass = mappedClass;
    }

    public Class<?> getConflictingClass() {
        return clazz;
    }

    public Class<?> getMappedClass() {
        return mappedClass;
    }
}
