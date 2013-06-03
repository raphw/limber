package no.kantega.lab.limber.servlet.request;

import no.kantega.lab.limber.servlet.IRenderable;

import java.util.UUID;

public class DefaultLimberRequest implements ILimberRequest {

    private final String sessionId;
    private final Class<? extends IRenderable> renderableClass;
    private final UUID ajaxIdentificator;
    private final UUID versionNumber;

    public DefaultLimberRequest(String sessionId,
                                Class<? extends IRenderable> renderableClass,
                                UUID versionNumber,
                                UUID ajaxIdentificator) {
        this.sessionId = sessionId;
        this.renderableClass = renderableClass;
        this.versionNumber = versionNumber;
        this.ajaxIdentificator = ajaxIdentificator;
    }

    @Override
    public Class<? extends IRenderable> getRenderableClass() {
        return renderableClass;
    }

    @Override
    public UUID getVersionId() {
        return versionNumber;
    }

    @Override
    public UUID getAjaxId() {
        return ajaxIdentificator;
    }

    @Override
    public boolean isAjax() {
        return ajaxIdentificator != null;
    }

    @Override
    public boolean isVersioned() {
        return versionNumber != null;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }
}
