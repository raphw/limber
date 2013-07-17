package no.kantega.lab.limber.kernel.application;

import no.kantega.lab.limber.util.PackageScanSupport;

import javax.annotation.Nonnull;
import javax.servlet.FilterConfig;
import java.util.*;

public class LimberApplicationHandler {

    private static final LimberApplicationHandler INSTANCE = new LimberApplicationHandler();

    private static final ILimberApplicationListenerFilter ALL_PASSING_FILTER = new ILimberApplicationListenerFilter.AllPassingFilter();

    public static LimberApplicationHandler getInstance() {
        return INSTANCE;
    }

    private final Map<UUID, ILimberApplicationContext> applicationMap;
    private final LinkedHashMap<ILimberApplicationListener, ILimberApplicationListenerFilter> applicationListeners;

    private LimberApplicationHandler() {
        applicationMap = new HashMap<UUID, ILimberApplicationContext>();
        applicationListeners = new LinkedHashMap<ILimberApplicationListener, ILimberApplicationListenerFilter>();
    }

    public UUID registerApplication(@Nonnull FilterConfig filterConfig) {

        UUID applicationId = UUID.randomUUID();
        ILimberApplicationContext applicationContext = new DefaultLimberApplicationContext(filterConfig, applicationId);
        applicationMap.put(applicationId, applicationContext);

        initializeStartUpListeners(applicationContext);

        filterConfig.getServletContext().addListener(applicationContext.getLimberSessionHandler());

        for (Map.Entry<ILimberApplicationListener, ILimberApplicationListenerFilter> applicationListener : applicationListeners.entrySet()) {
            if (applicationListener.getValue().isApplicable(applicationContext))
                applicationListener.getKey().onApplicationStart(applicationContext);
        }

        applicationContext.getLimberApplicationConfiguration().validate(true);

        return applicationId;
    }

    private void initializeStartUpListeners(@Nonnull ILimberApplicationContext applicationContext) {
        Collection<Class<?>> startupListeners = PackageScanSupport.getInstance().scanPackage(applicationContext.getRegisteredPackages(), LoadOnStartup.class);
        List<Class<?>> sortedStartupListeners = new ArrayList<Class<?>>(startupListeners);
        Collections.sort(sortedStartupListeners, new StartupPriorityComparator());
        ILimberApplicationListenerFilter uuidFilter = new ILimberApplicationListenerFilter.UuidFilter(applicationContext.getApplicationId());
        for (Class<?> startupListener : sortedStartupListeners) {
            addApplicationListener(instantiate(startupListener), uuidFilter);
        }
    }

    private ILimberApplicationListener instantiate(@Nonnull Class<?> clazz) {
        if (!ILimberApplicationListener.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException();
        }
        try {
            return (ILimberApplicationListener) clazz.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalArgumentException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public ILimberApplicationContext deregisterApplication(@Nonnull UUID uuid) {
        ILimberApplicationContext applicationContext = applicationMap.remove(uuid);
        for (Map.Entry<ILimberApplicationListener, ILimberApplicationListenerFilter> applicationListener : applicationListeners.entrySet()) {
            if (applicationListener.getValue().isApplicable(applicationContext))
                applicationListener.getKey().onApplicationShutdown(applicationContext);
        }
        return applicationContext;
    }

    public ILimberApplicationContext getApplication(@Nonnull UUID uuid) {
        ILimberApplicationContext applicationContext = applicationMap.get(uuid);
        if (applicationContext == null) throw new IllegalArgumentException();
        return applicationContext;
    }

    @Nonnull
    public LimberApplicationHandler addApplicationListener(@Nonnull ILimberApplicationListener applicationListener) {
        applicationListeners.put(applicationListener, ALL_PASSING_FILTER);
        return this;
    }

    @Nonnull
    public LimberApplicationHandler addApplicationListener(@Nonnull ILimberApplicationListener applicationListener,
                                                           @Nonnull ILimberApplicationListenerFilter applicationListenerFilter) {
        applicationListeners.put(applicationListener, applicationListenerFilter);
        return this;
    }

    @Nonnull
    public LimberApplicationHandler removeApplicationListener(@Nonnull ILimberApplicationListener applicationListener) {
        applicationListeners.remove(applicationListener);
        return this;
    }
}
