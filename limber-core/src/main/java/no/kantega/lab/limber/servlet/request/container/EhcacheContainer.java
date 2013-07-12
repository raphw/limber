package no.kantega.lab.limber.servlet.request.container;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.*;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import no.kantega.lab.limber.servlet.AbstractRenderable;
import no.kantega.lab.limber.servlet.context.ILimberApplicationContext;
import no.kantega.lab.limber.servlet.context.IRequestMapping;
import no.kantega.lab.limber.servlet.meta.PageRestitution;
import no.kantega.lab.limber.servlet.request.creator.IInstanceCreator;
import no.kantega.lab.limber.servlet.request.responses.RedirectionResponse;

import javax.annotation.Nonnull;
import java.util.UUID;

public class EhcacheContainer extends AbstractInstanceContainer {

    private static final Configuration DEFAULT_CONFIGURATION = new Configuration().diskStore(new DiskStoreConfiguration().path("java.io.tmpdir"));

    private final Ehcache pageCache;
    private final BlockingPageCache blockingPageCache;

    public EhcacheContainer(ILimberApplicationContext limberApplicationContext) {
        CacheManager singletonManager = CacheManager.create(DEFAULT_CONFIGURATION);
        pageCache = new Cache(makePageStoreConfig());
        singletonManager.addCache(pageCache);
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
        pageCache.put(new Element(instanceKey, renderable));
        return uuid;
    }

    @Override
    public AbstractRenderable storeBlockingIfAbsent(@Nonnull IRequestMapping requestMapping, @Nonnull IInstanceCreator instanceCreator) {
        return blockingPageCache.getRenderable(instanceKeyFrom(requestMapping), requestMapping, instanceCreator);
    }

    @Override
    public AbstractRenderable remove(@Nonnull IRequestMapping requestMapping) {
        InstanceKey instanceKey = instanceKeyFrom(requestMapping);
        try {
            Element element = pageCache.get(instanceKey);
            if (element == null) {
                return null;
            } else {
                return (AbstractRenderable) element.getObjectValue();
            }
        } finally {
            pageCache.remove(instanceKeyFrom(requestMapping));
        }
    }

    private AbstractRenderable findRenderableTo(InstanceKey instanceKey) {
        Element element = pageCache.get(instanceKey);
        if (element == null) {
            return null;
        } else {
            return (AbstractRenderable) element.getObjectValue();
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

        Searchable searchable = new Searchable();
        searchable.addSearchAttribute(new SearchAttribute().name("sessionId").expression("key.getSessionId()"));
        cacheConfiguration.addSearchable(searchable);

        return cacheConfiguration;
    }
}
