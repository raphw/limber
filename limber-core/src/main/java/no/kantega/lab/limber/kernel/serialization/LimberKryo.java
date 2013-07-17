package no.kantega.lab.limber.kernel.serialization;

import com.esotericsoftware.kryo.Kryo;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;

public class LimberKryo extends Kryo {

    private final Collection<Class<? extends Annotation>> disregardedAnnotations;

    public LimberKryo() {
        this.disregardedAnnotations = new HashSet<Class<? extends Annotation>>();
    }

    public Collection<Class<? extends Annotation>> getDisregardedAnnotations() {
        return disregardedAnnotations;
    }
}
