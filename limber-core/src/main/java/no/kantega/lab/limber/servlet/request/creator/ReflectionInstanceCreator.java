package no.kantega.lab.limber.servlet.request.creator;

import no.kantega.lab.limber.servlet.AbstractRenderable;
import no.kantega.lab.limber.servlet.context.IRequestMapping;

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
