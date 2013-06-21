package no.kantega.lab.limber.servlet.request.creator;

import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.context.IRequestMapping;

import javax.annotation.Nonnull;

public interface IInstanceCreator {

    @Nonnull
    IRenderable create(@Nonnull IRequestMapping requestMapping);
}
