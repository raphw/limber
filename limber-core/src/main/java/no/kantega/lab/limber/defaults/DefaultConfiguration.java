package no.kantega.lab.limber.defaults;

import no.kantega.lab.limber.kernel.AbstractRenderable;
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
import no.kantega.lab.limber.util.IStack;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.Deque;

@LoadOnStartup(DefaultConfiguration.DEFAULT_PRIORITY)
public class DefaultConfiguration implements ILimberApplicationListener {

    public static final int DEFAULT_PRIORITY = 1000;

    private PagePersistingCacheInstanceContainer pagePersistingCacheInstanceContainer;

    @Override
    public void onApplicationStart(@Nonnull ILimberApplicationContext applicationContext) {

        ILimberApplicationConfiguration applicationConfiguration = applicationContext.getLimberApplicationConfiguration();

        // Set up request interpreters
        Deque<IRequestMapper> requestInterpreters = applicationConfiguration.getRequestInterpreters();
        requestInterpreters.add(new LimberRequestExpressionMapper());
        requestInterpreters.add(new AnnotationRequestMapper(applicationContext));
        requestInterpreters.add(new RessourceRequestMapper(applicationContext));

        // Set up default instance creator
        applicationConfiguration.getInstanceCreationMapper()
                .addCreator(AbstractRenderable.class, new ReflectionInstanceCreator());

        // Make base directory
        File baseDirectory = createTempDirectory();
        baseDirectory = new File(baseDirectory, applicationContext.getFilterId().toString());
        baseDirectory = new File(baseDirectory, "pagecache");

        // Set up request containers
        IStack<IInstanceContainer> instanceContainerStack = applicationConfiguration.getInstanceContainerStack();
        pagePersistingCacheInstanceContainer = new PagePersistingCacheInstanceContainer(baseDirectory);
        instanceContainerStack.push(pagePersistingCacheInstanceContainer);
        instanceContainerStack.push(new VersioningPseudoContainer(instanceContainerStack.peek()));
    }

    @Override
    public void onApplicationShutdown(@Nonnull ILimberApplicationContext applicationContext) {
        pagePersistingCacheInstanceContainer.releaseCache();
    }

    private static File createTempDirectory() {
        try {
            File temp = File.createTempFile("temp", Long.toString(System.nanoTime()));
            if (!temp.delete()) throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
            if (!temp.mkdir()) throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
            return temp;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}