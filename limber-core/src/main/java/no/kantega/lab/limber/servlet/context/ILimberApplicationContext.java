package no.kantega.lab.limber.servlet.context;

import javax.annotation.Nonnull;
import javax.servlet.ServletContext;
import java.util.Set;
import java.util.UUID;

public interface ILimberApplicationContext {

    public static final String LIMBER_PACKAGE_CONTEXT_PARAMETER = "scan-package";

    @Nonnull
    ServletContext getServletContext();

    @Nonnull
    UUID getFilterId();

    @Nonnull
    Set<String> getRegisteredPackages();

    @Nonnull
    String getContextPath();

    boolean isLocalResource(@Nonnull CharSequence path);

    @Nonnull
    String getMimeType(@Nonnull CharSequence path);

}
