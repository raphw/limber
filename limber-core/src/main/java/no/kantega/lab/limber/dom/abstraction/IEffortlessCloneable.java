package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.kernel.clone.ICloningStrategy;

import javax.annotation.Nonnull;
import java.io.Serializable;

public interface IEffortlessCloneable extends Cloneable, Serializable {

    @Nonnull
    Object clone();

    @Nonnull
    Object clone(@Nonnull ICloningStrategy cloningStrategy);
}
