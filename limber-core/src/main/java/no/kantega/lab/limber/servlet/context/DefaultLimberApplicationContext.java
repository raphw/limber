package no.kantega.lab.limber.servlet.context;

import no.kantega.lab.limber.servlet.request.interpreter.IRequestInterpreter;

import javax.annotation.Nonnull;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultLimberApplicationContext implements ILimberApplicationContext {

    private final ServletContext servletContext;
    private final UUID filterId;
    private final Set<String> registeredPackages;

    public DefaultLimberApplicationContext(@Nonnull FilterConfig filterConfig, @Nonnull UUID filterId) {
        this.servletContext = filterConfig.getServletContext();
        this.filterId = filterId;
        registeredPackages = Collections.unmodifiableSet(findRegisteredPackages(filterConfig));
    }

    private Set<String> findRegisteredPackages(@Nonnull FilterConfig filterConfig) {
        String propertyValue = filterConfig.getInitParameter(LIMBER_PACKAGE_CONTEXT_PARAMETER);
        if (propertyValue == null) {
            throw new IllegalArgumentException();
        }
        Set<String> result = new HashSet<String>(Arrays.asList(propertyValue.split(",")));
        if (result.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Pattern packagePathPattern = Pattern.compile("^" + IRequestInterpreter.JAVA_PACKAGE_NAME_REGEX + "$");
        for (String packageElement : result) {
            assertPackageNameValidity(packageElement, packagePathPattern);
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
    public boolean isLocalResource(@Nonnull CharSequence path) {
        try {
            return servletContext.getResource(path.toString()) != null;
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
}
