package no.kantega.lab.limber.kernel.session;

import javax.annotation.Nonnull;

public interface ILimberSessionListener {

    void onSessionDestroy(@Nonnull String sessionId);
}
