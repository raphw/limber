package no.kantega.lab.limber.util;

import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;

public class PackageScanSupport {

    private static final PackageScanSupport INSTANCE = new PackageScanSupport();

    public static PackageScanSupport getInstance() {
        return INSTANCE;
    }

    public Collection<Class<?>> scanPackage(@Nonnull Collection<String> packageNames, @Nonnull Collection<Class<? extends Annotation>> annotationTypes) {
        Collection<Class<?>> found = new HashSet<Class<?>>();
        for (String packageName : packageNames) {
            Reflections reflections = new Reflections(preparePackageName(packageName));
            for (Class<? extends Annotation> annotationType : annotationTypes) {
                found.addAll(reflections.getTypesAnnotatedWith(annotationType));
            }
        }
        return found;
    }

    public Collection<Class<?>> scanPackage(@Nonnull Collection<String> packageNames, @Nonnull Class<? extends Annotation> annotationType) {
        Collection<Class<?>> found = new HashSet<Class<?>>();
        for (String packageName : packageNames) {
            found.addAll(scanPackage(packageName, annotationType));
        }
        return found;
    }

    public Collection<Class<?>> scanPackage(@Nonnull String packageName, @Nonnull Class<? extends Annotation> annotationType) {
        return new Reflections(preparePackageName(packageName)).getTypesAnnotatedWith(annotationType);
    }

    private static String preparePackageName(@Nonnull String packageName) {
        return packageName.endsWith(".") ? packageName : packageName.concat(".");
    }
}
