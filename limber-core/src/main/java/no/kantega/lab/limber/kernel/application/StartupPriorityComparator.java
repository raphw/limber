package no.kantega.lab.limber.kernel.application;

import javax.annotation.Nonnull;
import java.util.Comparator;

class StartupPriorityComparator implements Comparator<Class<?>> {

    @Override
    public int compare(@Nonnull Class<?> o1, @Nonnull Class<?> o2) {
        return o1.getAnnotation(LoadOnStartup.class).value() - o2.getAnnotation(LoadOnStartup.class).value();
    }
}
