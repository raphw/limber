package no.kantega.lab.limber.kernel.creator;

import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.request.IRequestMapping;

import javax.annotation.Nonnull;

public interface IInstanceCreator {

    AbstractRenderable create(@Nonnull IRequestMapping requestMapping);
}
