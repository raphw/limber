package no.kantega.lab.limber.defaults;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.DiskStoreConfiguration;
import no.kantega.lab.limber.kernel.application.*;
import no.kantega.lab.limber.kernel.container.EhcacheContainer;
import no.kantega.lab.limber.kernel.container.IInstanceContainer;
import no.kantega.lab.limber.kernel.container.VersioningPseudoContainer;
import no.kantega.lab.limber.kernel.creator.IInstanceCreator;
import no.kantega.lab.limber.kernel.creator.ReflectionInstanceCreator;
import no.kantega.lab.limber.kernel.mapper.AnnotationRequestMapper;
import no.kantega.lab.limber.kernel.mapper.IRequestMapper;
import no.kantega.lab.limber.kernel.mapper.LimberRequestExpressionMapper;
import no.kantega.lab.limber.kernel.mapper.RessourceRequestMapper;

import javax.annotation.Nonnull;
import java.util.Deque;
import java.util.LinkedList;

@LoadOnStartup(DefaultConfiguration.DEFAULT_PRIORITY)
public class DefaultConfiguration implements ILimberApplicationListener {

    public static final int DEFAULT_PRIORITY = 1000;

    private static final Configuration DEFAULT_EHCACHE_CONFIGURATION =
            new Configuration().diskStore(new DiskStoreConfiguration().path("java.io.tmpdir"));

    private CacheManager cacheManager;

    @Override
    public void onApplicationStart(@Nonnull ILimberApplicationContext applicationContext) {

        // Set up request interpreters
        Deque<IRequestMapper> requestInterpreters = new LinkedList<IRequestMapper>();
        requestInterpreters.add(new LimberRequestExpressionMapper());
        requestInterpreters.add(new AnnotationRequestMapper(applicationContext));
        requestInterpreters.add(new RessourceRequestMapper(applicationContext));

        // Set up instance creator
        IInstanceCreator instanceCreator = new ReflectionInstanceCreator();

        // Create ehcache manager
        cacheManager = new CacheManager(DEFAULT_EHCACHE_CONFIGURATION);

        // Set up request containers
        IInstanceContainer latest = new EhcacheContainer(cacheManager);
        latest = new VersioningPseudoContainer(latest);
        IInstanceContainer topInstanceContainer = latest;

        // Create page register
        ILimberPageRegister limberPageRegister = new DefaultLimberPageRegister(requestInterpreters);

        // Create and set configuration
        ILimberApplicationConfiguration applicationConfiguration = new DefaultLimberApplicationConfiguration(
                limberPageRegister, requestInterpreters, topInstanceContainer, instanceCreator);
        applicationContext.setLimberApplicationConfiguration(applicationConfiguration);
    }

    @Override
    public void onApplicationShutdown(@Nonnull ILimberApplicationContext applicationContext) {
        cacheManager.shutdown();
    }
}