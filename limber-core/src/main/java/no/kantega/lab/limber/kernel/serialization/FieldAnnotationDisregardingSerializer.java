package no.kantega.lab.limber.kernel.serialization;

import com.esotericsoftware.kryo.serializers.FieldSerializer;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class FieldAnnotationDisregardingSerializer<T> extends FieldSerializer<T> {

    private final LimberKryo limberKryo;

    public FieldAnnotationDisregardingSerializer(@Nonnull LimberKryo limberKryo, @Nonnull Class<?> type) {
        super(limberKryo, type);
        this.limberKryo = limberKryo;
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
            if (limberKryo.getDisregardedAnnotations().contains(annotation.annotationType())) {
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
