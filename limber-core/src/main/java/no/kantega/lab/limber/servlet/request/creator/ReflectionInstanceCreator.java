package no.kantega.lab.limber.servlet.request.creator;

import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.context.IRequestMapping;

import javax.annotation.Nonnull;

public class ReflectionInstanceCreator implements IInstanceCreator {

    @Nonnull
    @Override
    public IRenderable create(@Nonnull IRequestMapping requestMapping) {
        try {
            return requestMapping.getRenderableClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
