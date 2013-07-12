package no.kantega.lab.limber.kernel.application;

import com.google.common.base.Splitter;
import no.kantega.lab.limber.kernel.mapper.IRequestMapper;

import javax.annotation.Nonnull;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultLimberApplicationContext implements ILimberApplicationContext {

    private static final Pattern JAVA_PACKAGE_PATTERN = Pattern.compile("^" + IRequestMapper.JAVA_PACKAGE_NAME_REGEX + "$");

    private static final Splitter SPLITTER = Splitter.on(',').trimResults().omitEmptyStrings();

    private final String applicationName;
    private final ServletContext servletContext;
    private final UUID filterId;
    private final Set<String> registeredPackages;

    private final Set<ILimberApplicationListener> applicationListeners;

    private ILimberApplicationConfiguration applicationConfiguration;

    public DefaultLimberApplicationContext(@Nonnull FilterConfig filterConfig, @Nonnull UUID filterId) {
        this.applicationName = filterConfig.getFilterName();
        this.servletContext = filterConfig.getServletContext();
        this.filterId = filterId;
        registeredPackages = Collections.unmodifiableSet(findRegisteredPackages(filterConfig));
        applicationListeners = new LinkedHashSet<ILimberApplicationListener>();
    }

    private Set<String> findRegisteredPackages(@Nonnull FilterConfig filterConfig) {
        String propertyValue = filterConfig.getInitParameter(LIMBER_PACKAGE_CONTEXT_PARAMETER);
        if (propertyValue == null) {
            throw new IllegalArgumentException();
        }
        Iterable<String> split = SPLITTER.split(propertyValue);
        Set<String> result = new HashSet<String>();
        for (String packageElement : split) {
            assertPackageNameValidity(packageElement, JAVA_PACKAGE_PATTERN);
            result.add(packageElement);
        }
        if (result.size() == 0) {
            throw new IllegalArgumentException();
        }
        return result;
    }

    private void assertPackageNameValidity(@Nonnull String packageElement, @Nonnull Pattern packagePathPattern) {
        Matcher matcher = packagePathPattern.matcher(packageElement);
        boolean matches = matcher.matches();
        if (!matches) {
            throw new IllegalArgumentException();
        }
    }

    @Nonnull
    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    @Nonnull
    @Override
    public UUID getFilterId() {
        return filterId;
    }

    @Nonnull
    @Override
    public Set<String> getRegisteredPackages() {
        return registeredPackages;
    }

    @Nonnull
    @Override
    public String getContextPath() {
        return servletContext.getContextPath();
    }

    @Override
    public boolean isLocalReadableFile(@Nonnull CharSequence path) {
        try {
            URL url = servletContext.getResource(path.toString());
            if (url == null) return false;
            File file = new File(url.getFile());
            return file.isFile() && file.canRead();
        } catch (MalformedURLException e) {
            return false;
        }
    }

    @Nonnull
    @Override
    public InputStream getLocalResourceAsStream(@Nonnull CharSequence path) {
        InputStream resource = servletContext.getResourceAsStream(path.toString());
        if (resource == null) {
            throw new IllegalArgumentException();
        } else {
            return resource;
        }
    }

    @Nonnull
    @Override
    public String getMimeType(@Nonnull CharSequence path) {
        return servletContext.getMimeType(path.toString());
    }

    @Nonnull
    @Override
    public String getFilterName() {
        return servletContext.getServletContextName();
    }

    @Nonnull
    @Override
    public String getApplicationName() {
        return applicationName;
    }

    @Override
    public ILimberApplicationConfiguration getLimberApplicationConfiguration() {
        return applicationConfiguration;
    }

    @Override
    public ILimberApplicationConfiguration setLimberApplicationConfiguration(ILimberApplicationConfiguration applicationConfiguration) {
        try {
            return this.applicationConfiguration;
        } finally {
            this.applicationConfiguration = applicationConfiguration;
        }
    }
}
