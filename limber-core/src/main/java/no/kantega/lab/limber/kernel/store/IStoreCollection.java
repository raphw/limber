package no.kantega.lab.limber.kernel.store;

import javax.annotation.Nonnull;

public interface IStoreCollection {

    @Nonnull
    IStore getApplicationStore();

    @Nonnull
    IStore getSessionStore();

    @Nonnull
    IStore getRequestStore();
}
