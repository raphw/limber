package no.kantega.lab.limber.servlet.request.creator;

import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.request.ILimberRequest;

public interface IInstanceCreator {

    IRenderable create(ILimberRequest limberRequest);
}
