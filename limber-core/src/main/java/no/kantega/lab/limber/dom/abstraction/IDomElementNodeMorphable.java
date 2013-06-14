package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.ajax.abstraction.AjaxEventTrigger;
import no.kantega.lab.limber.ajax.abstraction.IAjaxCallback;
import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ContentEscapeMode;
import no.kantega.lab.limber.dom.element.ElementNode;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public interface IDomElementNodeMorphable<N extends ElementNode<? extends N>> extends IDomNodeMorphable<N> {

    @Nonnull
    <N2 extends AbstractNode<? extends N2>> IDomNodeRepresentable<? extends N2> addChild(int index, @Nonnull N2 node);

    @Nonnull
    IDomElementNodeRepresentable<N> addChildAndStay(int index, @Nonnull AbstractNode<?> node);

    @Nonnull
    IDomElementNodeRepresentable<?> addChild(int index, @Nonnull CharSequence tagName);

    @Nonnull
    IDomElementNodeRepresentable<N> addChildAndStay(int index, @Nonnull CharSequence tagName);

    @Nonnull
    IDomTextNodeRepresentable addText(int index, @Nonnull CharSequence text);

    @Nonnull
    IDomElementNodeRepresentable<N> addTextAndStay(int index, @Nonnull CharSequence text);

    @Nonnull
    IDomTextNodeRepresentable addText(int index, @Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    IDomElementNodeRepresentable<N> addTextAndStay(int index, @Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    <N2 extends AbstractNode<? extends N2>> IDomNodeRepresentable<? extends N2> appendChild(@Nonnull N2 node);

    @Nonnull
    IDomElementNodeRepresentable<N> appendChildAndStay(@Nonnull AbstractNode<?> node);

    @Nonnull
    IDomElementNodeRepresentable<?> appendChild(@Nonnull CharSequence tagName);

    @Nonnull
    IDomElementNodeRepresentable<N> appendChildAndStay(@Nonnull CharSequence tagName);

    @Nonnull
    IDomTextNodeRepresentable appendText(@Nonnull CharSequence text);

    @Nonnull
    IDomElementNodeRepresentable<N> appendTextAndStay(@Nonnull CharSequence text);

    @Nonnull
    IDomTextNodeRepresentable appendText(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    IDomElementNodeRepresentable<N> appendTextAndStay(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    <N2 extends AbstractNode<? extends N2>> IDomNodeRepresentable<? extends N2> prependChild(@Nonnull N2 node);

    @Nonnull
    IDomElementNodeRepresentable<N> prependChildAndStay(@Nonnull AbstractNode<?> node);

    @Nonnull
    IDomElementNodeRepresentable<?> prependChild(@Nonnull CharSequence tagName);

    @Nonnull
    IDomElementNodeRepresentable<N> prependChildAndStay(@Nonnull CharSequence tagName);

    @Nonnull
    IDomTextNodeRepresentable prependText(@Nonnull CharSequence text);

    @Nonnull
    IDomElementNodeRepresentable<N> prependTextAndStay(@Nonnull CharSequence text);

    @Nonnull
    IDomTextNodeRepresentable prependText(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    IDomElementNodeRepresentable<N> prependTextAndStay(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    <N2 extends ElementNode<? extends N2>> IDomNodeRepresentable<? extends N2> wrap(@Nonnull N2 elementNode);

    @Nonnull
    IDomElementNodeRepresentable<N> wrapAndStay(@Nonnull ElementNode<?> elementNode);

    @Nonnull
    IDomElementNodeRepresentable<?> wrap(@Nonnull CharSequence tagName);

    @Nonnull
    IDomElementNodeRepresentable<N> wrapAndStay(@Nonnull CharSequence tagName);

    @Nonnull
    IDomElementNodeRepresentable<N> setTagName(@Nonnull CharSequence tagName);

    @Nonnull
    IDomElementNodeRepresentable<N> putAttr(@Nonnull CharSequence key, CharSequence value);

    @Nonnull
    IDomElementNodeRepresentable<N> removeAttr(@Nonnull CharSequence key);

    @Nonnull
    IDomElementNodeRepresentable<N> setId(@Nonnull CharSequence id);

    @Nonnull
    IDomElementNodeRepresentable<N> setRandomId();

    @Nonnull
    IDomElementNodeRepresentable<N> setRandomIdIfNone();

    @Nonnull
    IDomElementNodeRepresentable<N> removeId();

    @Nonnull
    IDomElementNodeRepresentable<N> addCssClass(@Nonnull CharSequence cssClassName);

    @Nonnull
    IDomElementNodeRepresentable<N> setCssClasses(@Nonnull List<? extends CharSequence> cssClassNames);

    @Nonnull
    IDomElementNodeRepresentable<N> removeCssClass(@Nonnull CharSequence cssClassName);

    @Nonnull
    IDomElementNodeRepresentable<N> addCssStyle(@Nonnull CharSequence styleKey, CharSequence styleValue);

    @Nonnull
    IDomElementNodeRepresentable<N> setCssStyles(@Nonnull Map<? extends CharSequence, ? extends CharSequence> cssStyles);

    @Nonnull
    IDomElementNodeRepresentable<N> removeCssStyle(@Nonnull CharSequence styleKey);

    @Nonnull
    IDomElementNodeRepresentable<N> addAjaxEvent(@Nonnull AjaxEventTrigger ajaxEventTrigger, @Nonnull IAjaxCallback<? super ElementNode> ajaxCallback);

    @Nonnull
    IDomElementNodeRepresentable<N> removeAjaxEvent(@Nonnull IAjaxCallback<?> ajaxCallback);

    @Nonnull
    IDomElementNodeRepresentable<N> removeAjaxEvent(@Nonnull AjaxEventTrigger ajaxEventTrigger);
}
