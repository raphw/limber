package no.kantega.lab.limber.kernel.session;

import javax.annotation.Nonnull;

public interface ILimberSessionListener {

    void onSessionCreate(@Nonnull String sessionId);

    void onSessionDestroy(@Nonnull String sessionId);
}
