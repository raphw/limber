package no.kantega.lab.limber.kernel.application;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface ILimberApplicationListenerFilter {

    static class UuidFilter implements ILimberApplicationListenerFilter {

        private final UUID uuid;

        public UuidFilter(@Nonnull UUID uuid) {
            this.uuid = uuid;
        }

        @Override
        public boolean isApplicable(@Nonnull ILimberApplicationContext applicationContext) {
            return applicationContext.getApplicationId().equals(uuid);
        }
    }

    static class AllPassingFilter implements ILimberApplicationListenerFilter {
        @Override
        public boolean isApplicable(@Nonnull ILimberApplicationContext applicationContext) {
            return true;
        }
    }

    boolean isApplicable(@Nonnull ILimberApplicationContext applicationContext);
}
