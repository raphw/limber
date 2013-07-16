package no.kantega.lab.limber.kernel.container;

import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.creator.IInstanceCreator;
import no.kantega.lab.limber.kernel.meta.PageRestitution;
import no.kantega.lab.limber.kernel.request.IRequestMapping;
import no.kantega.lab.limber.kernel.response.RedirectionResponse;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.UUID;

public class PagePersistingCacheInstanceContainer extends AbstractInstanceContainer {

    private final PagePersistingCache pagePersistingCache;

    public PagePersistingCacheInstanceContainer(@Nonnull File directory) {
        this.pagePersistingCache = new PagePersistingCache(directory);
    }

    @Override
    public UUID store(@Nonnull String sessionId, @Nonnull AbstractRenderable renderable) {
        UUID uuid = UUID.randomUUID();
        InstanceKey instanceKey = new InstanceKey(sessionId, renderable.getClass(), uuid);
        pagePersistingCache.put(instanceKey, renderable);
        return uuid;
    }

    @Override
    public AbstractRenderable storeBlockingIfAbsent(@Nonnull final IRequestMapping requestMapping, @Nonnull final IInstanceCreator instanceCreator) {
        InstanceKey instanceKey = instanceKeyFrom(requestMapping);
        return pagePersistingCache.get(instanceKey, pagePersistingCache.new PersistedStateCacheLoader(instanceKey) {
            @Override
            protected AbstractRenderable makeValue(@Nonnull InstanceKey key) {
                return instanceCreator.create(requestMapping);
            }
        });
    }

    @Override
    public AbstractRenderable resolve(@Nonnull IRequestMapping requestMapping, @Nonnull IInstanceCreator instanceCreator) {
        if (requestMapping.isVersioned()) {
            InstanceKey instanceKey = instanceKeyFrom(requestMapping);
            AbstractRenderable renderable = pagePersistingCache.getIfPresent(instanceKey);
            if (renderable != null) {
                return renderable;
            } else if (!requestMapping.getClass().getAnnotation(PageRestitution.class).value().encloses(requestMapping)) {
                return null;
            } else {
                return storeBlockingIfAbsent(requestMapping, instanceCreator);
            }
        } else {
            UUID uuid = store(requestMapping.getSessionId(), instanceCreator.create(requestMapping));
            return new RedirectionResponse(requestMapping.getRenderableClass(), uuid);
        }
    }

    @Override
    public AbstractRenderable remove(@Nonnull IRequestMapping requestMapping) {
        InstanceKey instanceKey = instanceKeyFrom(requestMapping);
        try {
            return pagePersistingCache.getIfPresent(instanceKey);
        } finally {
            pagePersistingCache.remove(instanceKey);
        }
    }

    private InstanceKey instanceKeyFrom(@Nonnull IRequestMapping requestMapping) {
        return new InstanceKey(
                requestMapping.getSessionId(),
                requestMapping.getRenderableClass(),
                requestMapping.getVersionId());
    }

    public boolean releaseCache() {
        return pagePersistingCache.releaseCache();
    }
}
