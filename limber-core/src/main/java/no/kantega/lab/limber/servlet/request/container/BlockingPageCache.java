package no.kantega.lab.limber.servlet.request.container;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.constructs.blocking.BlockingCache;
import net.sf.ehcache.constructs.blocking.LockTimeoutException;
import no.kantega.lab.limber.servlet.AbstractRenderable;
import no.kantega.lab.limber.servlet.context.IRequestMapping;
import no.kantega.lab.limber.servlet.request.creator.IInstanceCreator;

import javax.annotation.Nonnull;

class BlockingPageCache extends BlockingCache {

    public BlockingPageCache(Ehcache cache) throws CacheException {
        super(cache);
    }

    public AbstractRenderable getRenderable(@Nonnull InstanceKey key,
                                            @Nonnull IRequestMapping requestMapping,
                                            @Nonnull IInstanceCreator instanceCreator) throws LockTimeoutException {

        Element element = super.get(key);

        if (element == null) {
            try {
                Object value = instanceCreator.create(requestMapping);
                element = new Element(key, value);
            } catch (Throwable throwable) {
                element = new Element(key, null);
                throw new CacheException("Could not fetch object for cache entry with key \"" + key + "\".", throwable);
            } finally {
                put(element);
            }
        }

        return (AbstractRenderable) element.getObjectValue();
    }
}
