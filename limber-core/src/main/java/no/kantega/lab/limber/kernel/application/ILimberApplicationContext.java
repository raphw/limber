package no.kantega.lab.limber.kernel.application;

import no.kantega.lab.limber.kernel.session.LimberSessionHandler;

import javax.annotation.Nonnull;
import javax.servlet.ServletContext;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

public interface ILimberApplicationContext {

    public static final String LIMBER_PACKAGE_CONTEXT_PARAMETER = "scan-package";

    @Nonnull
    ServletContext getServletContext();

    @Nonnull
    UUID getFilterId();

    @Nonnull
    String getFilterName();

    @Nonnull
    Set<String> getRegisteredPackages();

    @Nonnull
    String getContextPath();

    boolean isLocalReadableFile(@Nonnull CharSequence path);

    @Nonnull
    InputStream getLocalResourceAsStream(@Nonnull CharSequence path);

    @Nonnull
    String getMimeType(@Nonnull CharSequence path);

    @Nonnull
    String getApplicationName();

    @Nonnull
    ILimberApplicationConfiguration getLimberApplicationConfiguration();

    @Nonnull
    LimberSessionHandler getLimberSessionHandler();
}
