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
    IDomElementNodeRepresentable<? extends N> addChildAndStay(int index, @Nonnull AbstractNode<?> node);

    @Nonnull
    IDomElementNodeRepresentable<?> addChild(int index, @Nonnull CharSequence tagName);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> addChildAndStay(int index, @Nonnull CharSequence tagName);

    @Nonnull
    IDomTextNodeRepresentable addText(int index, @Nonnull CharSequence text);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> addTextAndStay(int index, @Nonnull CharSequence text);

    @Nonnull
    IDomTextNodeRepresentable addText(int index, @Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> addTextAndStay(int index, @Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    <N2 extends AbstractNode<? extends N2>> IDomNodeRepresentable<? extends N2> appendChild(@Nonnull N2 node);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> appendChildAndStay(@Nonnull AbstractNode<?> node);

    @Nonnull
    IDomElementNodeRepresentable<?> appendChild(@Nonnull CharSequence tagName);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> appendChildAndStay(@Nonnull CharSequence tagName);

    @Nonnull
    IDomTextNodeRepresentable appendText(@Nonnull CharSequence text);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> appendTextAndStay(@Nonnull CharSequence text);

    @Nonnull
    IDomTextNodeRepresentable appendText(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> appendTextAndStay(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    <N2 extends AbstractNode<? extends N2>> IDomNodeRepresentable<? extends N2> prependChild(@Nonnull N2 node);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> prependChildAndStay(@Nonnull AbstractNode<?> node);

    @Nonnull
    IDomElementNodeRepresentable<?> prependChild(@Nonnull CharSequence tagName);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> prependChildAndStay(@Nonnull CharSequence tagName);

    @Nonnull
    IDomTextNodeRepresentable prependText(@Nonnull CharSequence text);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> prependTextAndStay(@Nonnull CharSequence text);

    @Nonnull
    IDomTextNodeRepresentable prependText(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> prependTextAndStay(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    <N2 extends ElementNode<? extends N2>> IDomNodeRepresentable<? extends N2> wrap(@Nonnull N2 elementNode);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> wrapAndStay(@Nonnull ElementNode<?> elementNode);

    @Nonnull
    IDomElementNodeRepresentable<?> wrap(@Nonnull CharSequence tagName);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> wrapAndStay(@Nonnull CharSequence tagName);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> setTagName(@Nonnull CharSequence tagName);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> putAttr(@Nonnull CharSequence key, CharSequence value);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> removeAttr(@Nonnull CharSequence key);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> setId(@Nonnull CharSequence id);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> setRandomId();

    @Nonnull
    IDomElementNodeRepresentable<? extends N> setRandomIdIfNone();

    @Nonnull
    IDomElementNodeRepresentable<? extends N> removeId();

    @Nonnull
    IDomElementNodeRepresentable<? extends N> addCssClass(@Nonnull CharSequence cssClassName);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> setCssClasses(@Nonnull List<? extends CharSequence> cssClassNames);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> removeCssClass(@Nonnull CharSequence cssClassName);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> addCssStyle(@Nonnull CharSequence styleKey, CharSequence styleValue);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> setCssStyles(@Nonnull Map<? extends CharSequence, ? extends CharSequence> cssStyles);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> removeCssStyle(@Nonnull CharSequence styleKey);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> addAjaxEvent(@Nonnull AjaxEventTrigger ajaxEventTrigger, @Nonnull IAjaxCallback<? super ElementNode> ajaxCallback);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> removeAjaxEvent(@Nonnull IAjaxCallback<?> ajaxCallback);

    @Nonnull
    IDomElementNodeRepresentable<? extends N> removeAjaxEvent(@Nonnull AjaxEventTrigger ajaxEventTrigger);
}
