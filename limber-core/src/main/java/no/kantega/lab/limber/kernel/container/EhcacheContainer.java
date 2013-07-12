package no.kantega.lab.limber.kernel.container;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.creator.IInstanceCreator;
import no.kantega.lab.limber.kernel.meta.PageRestitution;
import no.kantega.lab.limber.kernel.request.IRequestMapping;
import no.kantega.lab.limber.kernel.response.RedirectionResponse;

import javax.annotation.Nonnull;
import java.util.UUID;

public class EhcacheContainer extends AbstractInstanceContainer {

    private final Ehcache pageCache;
    private final BlockingPageCache blockingPageCache;

    public EhcacheContainer(@Nonnull CacheManager cacheManager) {
        pageCache = new Cache(makePageStoreConfig());
        cacheManager.addCache(pageCache);
        blockingPageCache = new BlockingPageCache(pageCache);

    }

    @Override
    public AbstractRenderable resolve(@Nonnull IRequestMapping requestMapping, @Nonnull IInstanceCreator instanceCreator) {
        if (requestMapping.isVersioned()) {
            InstanceKey instanceKey = instanceKeyFrom(requestMapping);
            AbstractRenderable renderable = findRenderableTo(instanceKey);
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
    public UUID store(@Nonnull String sessionId, @Nonnull AbstractRenderable renderable) {
        UUID uuid = UUID.randomUUID();
        InstanceKey instanceKey = new InstanceKey(sessionId, renderable.getClass(), uuid);
        pageCache.put(new Element(instanceKey, SerializationWrapper.wrap(renderable)));
        return uuid;
    }

    @Override
    public AbstractRenderable storeBlockingIfAbsent(@Nonnull IRequestMapping requestMapping, @Nonnull IInstanceCreator instanceCreator) {
        return blockingPageCache.getOrCreateRenderable(instanceKeyFrom(requestMapping), requestMapping, instanceCreator);
    }

    @Override
    public AbstractRenderable remove(@Nonnull IRequestMapping requestMapping) {
        InstanceKey instanceKey = instanceKeyFrom(requestMapping);
        try {
            return findRenderableTo(instanceKey);
        } finally {
            pageCache.remove(instanceKey);
        }
    }

    private AbstractRenderable findRenderableTo(InstanceKey instanceKey) {
        Element element = pageCache.get(instanceKey);
        if (element == null) {
            return null;
        } else {
            return (AbstractRenderable) SerializationWrapper.unwrap(element.getObjectValue());
        }
    }

    private InstanceKey instanceKeyFrom(@Nonnull IRequestMapping requestMapping) {
        return new InstanceKey(
                requestMapping.getSessionId(),
                requestMapping.getRenderableClass(),
                requestMapping.getVersionId());
    }

    private CacheConfiguration makePageStoreConfig() {

        CacheConfiguration cacheConfiguration = new CacheConfiguration()
                .name(String.format("pageCache"))
                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LRU)
                .timeToIdleSeconds(0)
                .timeToLiveSeconds(0)
                .diskExpiryThreadIntervalSeconds(0)
                .maxEntriesInCache(0)
                .maxEntriesLocalDisk(0)
                .maxEntriesLocalHeap(0)
                .clearOnFlush(true)
                .eternal(true)
                .persistence(new PersistenceConfiguration().strategy(PersistenceConfiguration.Strategy.LOCALTEMPSWAP))
                .logging(false);

//        Searchable searchable = new Searchable();
//        searchable.addSearchAttribute(new SearchAttribute().name("sessionId").expression("key.getSessionId()"));
//        cacheConfiguration.addSearchable(searchable);

        return cacheConfiguration;
    }
}
