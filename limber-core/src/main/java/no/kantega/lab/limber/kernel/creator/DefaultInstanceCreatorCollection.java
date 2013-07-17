package no.kantega.lab.limber.kernel.creator;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import javax.annotation.Nonnull;
import java.util.List;

public class DefaultInstanceCreatorCollection implements IInstanceCreatorCollection {

    private final ListMultimap<Class<?>, IInstanceCreator> instanceCreatorMultimap;

    public DefaultInstanceCreatorCollection() {
        this.instanceCreatorMultimap = ArrayListMultimap.create();
    }

    private List<IInstanceCreator> findInstanceCreator(@Nonnull Class<?> clazz) {
        return instanceCreatorMultimap.get(clazz);
    }

    private <T> T applyOnHierarchy(@Nonnull Class<? extends T> requestedClass) {
        Class<?> classInHierarchy = requestedClass;
        do {
            for (IInstanceCreator instanceCreator : findInstanceCreator(classInHierarchy)) {
                T instance = instanceCreator.create(requestedClass);
                if (instance != null) return instance;
            }
            classInHierarchy = classInHierarchy.getSuperclass();
        } while (classInHierarchy != null);
        return null;
    }

    @Override
    public <T> T create(@Nonnull Class<? extends T> requestedClass) {
        return applyOnHierarchy(requestedClass);
    }

    @Nonnull
    @Override
    public IInstanceCreatorCollection add(@Nonnull Class<?> minimumTargetClass,
                                          @Nonnull IInstanceCreator instanceCreator) {
        instanceCreatorMultimap.put(minimumTargetClass, instanceCreator);
        return this;
    }

    @Override
    public boolean remove(@Nonnull IInstanceCreator instanceCreator) {
        return instanceCreatorMultimap.values().remove(instanceCreator);
    }

    @Override
    public boolean isObjectCreatable() {
        return instanceCreatorMultimap.get(Object.class).size() > 0;
    }
}
