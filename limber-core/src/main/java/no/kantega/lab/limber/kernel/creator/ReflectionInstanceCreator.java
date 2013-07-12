package no.kantega.lab.limber.kernel.creator;

import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.request.IRequestMapping;

import javax.annotation.Nonnull;

public class ReflectionInstanceCreator implements IInstanceCreator {

    @Nonnull
    @Override
    public AbstractRenderable create(@Nonnull IRequestMapping requestMapping) {
        try {
            return requestMapping.getRenderableClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
