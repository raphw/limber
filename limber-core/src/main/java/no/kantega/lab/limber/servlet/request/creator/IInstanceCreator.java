package no.kantega.lab.limber.servlet.request.creator;

import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.request.ILimberRequest;

import javax.annotation.Nonnull;

public interface IInstanceCreator {

    IRenderable create(@Nonnull ILimberRequest limberRequest);
}
