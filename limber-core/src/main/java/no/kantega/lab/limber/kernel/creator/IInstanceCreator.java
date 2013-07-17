package no.kantega.lab.limber.kernel.creator;

import javax.annotation.Nonnull;

public interface IInstanceCreator {

    <T> T create(@Nonnull Class<? extends T> requestedClass);
}
