package no.kantega.lab.limber.servlet.request.creator;

import no.kantega.lab.limber.servlet.AbstractRenderable;
import no.kantega.lab.limber.servlet.context.IRequestMapping;

import javax.annotation.Nonnull;

public interface IInstanceCreator {

    @Nonnull
    AbstractRenderable create(@Nonnull IRequestMapping requestMapping);
}
