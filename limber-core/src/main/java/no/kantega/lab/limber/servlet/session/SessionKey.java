package no.kantega.lab.limber.servlet.session;

import javax.annotation.Nonnull;

public class SessionKey<T> {

    private final Class<? super T> type;

    private final String identifier;

    public SessionKey(@Nonnull Class<? super T> type, @Nonnull String identifier) {
        this.type = type;
        this.identifier = identifier;
    }

    @Nonnull
    public Class<? super T> getType() {
        return type;
    }

    @Nonnull
    public String getIdentifier() {
        return identifier;
    }
}
