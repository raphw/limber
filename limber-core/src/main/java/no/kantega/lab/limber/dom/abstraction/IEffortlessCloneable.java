package no.kantega.lab.limber.dom.abstraction;

import javax.annotation.Nonnull;
import java.io.Serializable;

public interface IEffortlessCloneable extends Cloneable, Serializable {

    @Nonnull
    Object clone();
}
