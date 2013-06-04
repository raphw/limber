package no.kantega.lab.limber.dom.implementation.limber.abstraction;

import no.kantega.lab.limber.dom.implementation.limber.element.AbstractNode;

import java.util.List;
import java.util.Set;

public interface IDomElementQueryable extends IDomNodeQueryable {

    String getTagName();

    List<AbstractNode<?>> children();

    String getAttr(CharSequence key);

    Set<CharSequence> attrs();
}
