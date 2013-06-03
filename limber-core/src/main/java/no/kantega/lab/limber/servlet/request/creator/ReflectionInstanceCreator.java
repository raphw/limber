package no.kantega.lab.limber.servlet.request.creator;

import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.request.ILimberRequest;

public class ReflectionInstanceCreator implements IInstanceCreator {

    @Override
    public IRenderable create(ILimberRequest limberRequest) {
        try {
            return limberRequest.getRenderableClass().newInstance();
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }
}
