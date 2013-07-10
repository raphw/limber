package no.kantega.lab.limber.servlet.meta;

import no.kantega.lab.limber.servlet.context.IRequestMapping;

import javax.annotation.Nonnull;

public enum RestitutionPolicy {

    RECREATE_ALL,
    RECREATE_MAIN_ROUTINE,
    RECREATE_SUB_ROUTINE,
    RECREATE_NEVER;

    public boolean encloses(@Nonnull IRequestMapping requestMapping) {
        switch (this) {
            case RECREATE_ALL:
                return true;
            case RECREATE_NEVER:
                return false;
            case RECREATE_MAIN_ROUTINE:
                return !requestMapping.isSubroutine();
            case RECREATE_SUB_ROUTINE:
                return requestMapping.isSubroutine();
            default:
                throw new IllegalStateException();
        }
    }
}
