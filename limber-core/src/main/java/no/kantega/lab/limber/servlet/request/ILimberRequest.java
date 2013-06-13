package no.kantega.lab.limber.servlet.request;

import no.kantega.lab.limber.servlet.IRenderable;

import java.util.UUID;

public interface ILimberRequest {

    String getSessionId();

    Class<? extends IRenderable> getRenderableClass();

    UUID getVersionId();

    boolean isVersioned();

    UUID getSubroutineId();

    boolean isSubroutine();
}