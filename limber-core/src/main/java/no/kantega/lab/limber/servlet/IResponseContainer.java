package no.kantega.lab.limber.servlet;

import no.kantega.lab.limber.servlet.request.ILimberRequest;

import java.net.URI;
import java.util.UUID;

public interface IResponseContainer {

    ILimberRequest getRequest();

    void setStatusCode(int code);

    void addHeader(String key, String value);

    URI decodeLink(Class<? extends IRenderable> renderableClass, UUID versionId, UUID ajaxId);
}
