package no.kantega.lab.limber.kernel.creator;

import javax.annotation.Nonnull;

public class ReflectionInstanceCreator implements IInstanceCreator {

    @Override
    public <U> U create(@Nonnull Class<? extends U> requestedClass) {
        try {
            return requestedClass.newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
