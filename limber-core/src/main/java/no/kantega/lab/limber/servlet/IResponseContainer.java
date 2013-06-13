package no.kantega.lab.limber.servlet;

import no.kantega.lab.limber.servlet.request.ILimberRequest;

import javax.annotation.Nonnull;
import java.net.URI;
import java.util.UUID;

public interface IResponseContainer {

    @Nonnull
    ILimberRequest getRequest();

    void setStatusCode(int code);

    void addHeader(@Nonnull String key, String value);

    URI decodeLink(@Nonnull Class<? extends IRenderable> renderableClass, UUID versionId, UUID ajaxId);
}
