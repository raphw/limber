package no.kantega.lab.limber.servlet.request.interpreter;

import no.kantega.lab.limber.servlet.IRenderable;
import no.kantega.lab.limber.servlet.request.ILimberRequest;
import no.kantega.lab.limber.servlet.request.RawRequest;

import java.net.URI;
import java.util.UUID;

public interface IRequestInterpreter {

    static final String UUID_V4_REGEX = "[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    static final String JAVA_PACKAGE_NAME_REGEX = "[a-zA-Z_]{1}[a-zA-Z0-9_]*(?:\\.[a-zA-Z_]{1}[a-zA-Z0-9_]*)*";

    ILimberRequest interpret(RawRequest rawRequest);

    URI resolve(Class<? extends IRenderable> renderableClass, UUID versionId, UUID ajaxId);
}
