package no.kantega.lab.limber.kernel.container;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.FieldSerializer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;

public class FieldAnnotationDisregardingSerializer<T> extends FieldSerializer<T> {

    private static Collection<Class<? extends Annotation>> disregardedAnnotationTypes =
            new HashSet<Class<? extends Annotation>>();

    public static boolean markAnnotation(Class<? extends Annotation> annotationType) {
        return disregardedAnnotationTypes.add(annotationType);
    }

    public static boolean unmarkAnnotation(Class<? extends Annotation> annotationType) {
        return disregardedAnnotationTypes.remove(annotationType);
    }

    public FieldAnnotationDisregardingSerializer(Kryo kryo, Class<?> type) {
        super(kryo, type);
    }

    protected void initializeCachedFields() {
        CachedField[] cachedFields = getFields();
        for (CachedField cachedField : cachedFields) {
            Field field = cachedField.getField();
            if (containsDisregardedAnnotation(field)) {
                super.removeField(field.getName());
            }
        }
    }

    private boolean containsDisregardedAnnotation(Field field) {
        for (Annotation annotation : field.getAnnotations()) {
            if (disregardedAnnotationTypes.contains(annotation.annotationType())) {
                return true;
            }
        }
        return false;
    }

    public void removeField(String fieldName) {
        super.removeField(fieldName);
        initializeCachedFields();
    }
}
