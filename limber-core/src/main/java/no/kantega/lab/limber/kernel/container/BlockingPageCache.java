package no.kantega.lab.limber.kernel.container;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.constructs.blocking.BlockingCache;
import net.sf.ehcache.constructs.blocking.LockTimeoutException;
import no.kantega.lab.limber.kernel.AbstractRenderable;
import no.kantega.lab.limber.kernel.creator.IInstanceCreator;
import no.kantega.lab.limber.kernel.request.IRequestMapping;

import javax.annotation.Nonnull;

class BlockingPageCache extends BlockingCache {

    public BlockingPageCache(Ehcache cache) throws CacheException {
        super(cache);
    }

    public AbstractRenderable getOrCreateRenderable(@Nonnull InstanceKey key,
                                                    @Nonnull IRequestMapping requestMapping,
                                                    @Nonnull IInstanceCreator instanceCreator) throws LockTimeoutException {

        Element element = super.get(key);

        if (element == null) {
            try {
                Object value = SerializationWrapper.wrap(instanceCreator.create(requestMapping));
                element = new Element(key, value);
            } catch (Throwable throwable) {
                element = new Element(key, null);
                throw new CacheException("Could not fetch object for cache entry with key \"" + key + "\".", throwable);
            } finally {
                put(element);
            }
        }

        return (AbstractRenderable) SerializationWrapper.unwrap(element.getObjectValue());
    }
}
