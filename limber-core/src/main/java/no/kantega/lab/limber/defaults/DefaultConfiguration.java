package no.kantega.lab.limber.defaults;

import com.google.common.io.Files;
import no.kantega.lab.limber.kernel.application.ILimberApplicationConfiguration;
import no.kantega.lab.limber.kernel.application.ILimberApplicationContext;
import no.kantega.lab.limber.kernel.application.ILimberApplicationListener;
import no.kantega.lab.limber.kernel.application.LoadOnStartup;
import no.kantega.lab.limber.kernel.container.IInstanceContainer;
import no.kantega.lab.limber.kernel.container.PagePersistingCacheInstanceContainer;
import no.kantega.lab.limber.kernel.container.VersioningPseudoContainer;
import no.kantega.lab.limber.kernel.creator.ReflectionInstanceCreator;
import no.kantega.lab.limber.kernel.mapper.AnnotationRequestMapper;
import no.kantega.lab.limber.kernel.mapper.IRequestMapper;
import no.kantega.lab.limber.kernel.mapper.LimberRequestExpressionMapper;
import no.kantega.lab.limber.kernel.mapper.RessourceRequestMapper;
import no.kantega.lab.limber.kernel.serialization.KryoSerializationStrategy;
import no.kantega.lab.limber.util.IStack;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.Deque;

@LoadOnStartup(DefaultConfiguration.DEFAULT_PRIORITY)
public class DefaultConfiguration implements ILimberApplicationListener {

    public static final int DEFAULT_PRIORITY = 1000;

    public static final String PAGE_SUBDIR = "pages";

    private PagePersistingCacheInstanceContainer pagePersistingCacheInstanceContainer;

    @Override
    public void onApplicationStart(@Nonnull ILimberApplicationContext applicationContext) {

        ILimberApplicationConfiguration applicationConfiguration = applicationContext.getLimberApplicationConfiguration();

        // Set up request interpreters
        Deque<IRequestMapper> requestInterpreters = applicationConfiguration.getRequestMapperDeque();
        requestInterpreters.add(new LimberRequestExpressionMapper());
        requestInterpreters.add(new AnnotationRequestMapper(applicationContext));
        requestInterpreters.add(new RessourceRequestMapper(applicationContext));

        // Set up default instance creator
        applicationConfiguration.getInstanceCreatorCollection().add(Object.class, new ReflectionInstanceCreator());

        // Make base directory
        File baseDirectory = Files.createTempDir();
        baseDirectory = new File(baseDirectory, applicationContext.getFilterId().toString());
        baseDirectory = new File(baseDirectory, PAGE_SUBDIR);

        // Set up serialization strategy
        applicationConfiguration.setSerializationStrategy(new KryoSerializationStrategy());

        // Set up request containers
        IStack<IInstanceContainer> instanceContainerStack = applicationConfiguration.getInstanceContainerStack();
        pagePersistingCacheInstanceContainer = new PagePersistingCacheInstanceContainer(baseDirectory, applicationConfiguration);
        instanceContainerStack.push(pagePersistingCacheInstanceContainer);
        instanceContainerStack.push(new VersioningPseudoContainer(instanceContainerStack.peek()));
    }

    @Override
    public void onApplicationShutdown(@Nonnull ILimberApplicationContext applicationContext) {
        pagePersistingCacheInstanceContainer.invalidate();
    }
}