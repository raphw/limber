package no.kantega.lab.limber.kernel.application;

import javax.annotation.Nonnull;

public interface ILimberApplicationListener {

    void onApplicationStart(@Nonnull ILimberApplicationContext applicationContext);

    void onApplicationShutdown(@Nonnull ILimberApplicationContext applicationContext);
}
