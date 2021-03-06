package no.kantega.lab.limber.kernel.container;

import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.application.ILimberApplicationConfiguration;
import no.kantega.lab.limber.kernel.creator.IInstanceCreator;
import no.kantega.lab.limber.kernel.meta.PageRestitution;
import no.kantega.lab.limber.kernel.request.IRequestMapping;
import no.kantega.lab.limber.kernel.response.RedirectionResponse;
import no.kantega.lab.limber.kernel.store.IStoreCollection;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.UUID;

public class PagePersistingCacheInstanceContainer extends AbstractInstanceContainer {

    private final PagePersistingCache pagePersistingCache;

    public PagePersistingCacheInstanceContainer(@Nonnull File directory, @Nonnull ILimberApplicationConfiguration applicationConfiguration) {
        this.pagePersistingCache = new PagePersistingCache(directory, applicationConfiguration.getSerializationStrategy());
    }

    @Override
    public UUID store(@Nonnull String sessionId, @Nonnull AbstractRenderable renderable, @Nonnull IStoreCollection storeCollection) {
        UUID uuid = UUID.randomUUID();
        InstanceKey instanceKey = new InstanceKey(sessionId, renderable.getClass(), uuid);
        pagePersistingCache.put(instanceKey, renderable);
        return uuid;
    }

    @Override
    public AbstractRenderable storeBlockingIfAbsent(@Nonnull final IRequestMapping requestMapping, @Nonnull final IInstanceCreator instanceCreator, @Nonnull IStoreCollection storeCollection) {
        InstanceKey instanceKey = instanceKeyFrom(requestMapping);
        return pagePersistingCache.get(instanceKey, pagePersistingCache.new PersistedStateCacheLoader(instanceKey) {
            @Override
            protected AbstractRenderable makeValue(@Nonnull InstanceKey key) {
                return instanceCreator.create(requestMapping.getRenderableClass());
            }
        });
    }

    @Override
    public AbstractRenderable resolve(@Nonnull IRequestMapping requestMapping, @Nonnull IInstanceCreator instanceCreator, @Nonnull IStoreCollection storeCollection) {
        if (requestMapping.isVersioned()) {
            InstanceKey instanceKey = instanceKeyFrom(requestMapping);
            AbstractRenderable renderable = pagePersistingCache.getIfPresent(instanceKey);
            if (renderable != null) {
                return renderable;
            } else if (!requestMapping.getClass().getAnnotation(PageRestitution.class).value().encloses(requestMapping)) {
                return null;
            } else {
                return storeBlockingIfAbsent(requestMapping, instanceCreator, storeCollection);
            }
        } else {
            UUID uuid = store(requestMapping.getSessionId(), instanceCreator.create(requestMapping.getRenderableClass()), storeCollection);
            return new RedirectionResponse(requestMapping.getRenderableClass(), uuid);
        }
    }

    @Override
    public AbstractRenderable remove(@Nonnull IRequestMapping requestMapping, @Nonnull IStoreCollection storeCollection) {
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

    public boolean invalidate() {
        return pagePersistingCache.invalidate();
    }
}
