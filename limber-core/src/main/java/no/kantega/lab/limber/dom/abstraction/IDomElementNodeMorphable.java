package no.kantega.lab.limber.dom.abstraction;

import no.kantega.lab.limber.ajax.abstraction.AjaxEventTrigger;
import no.kantega.lab.limber.ajax.abstraction.IAjaxCallback;
import no.kantega.lab.limber.dom.element.AbstractNode;
import no.kantega.lab.limber.dom.element.ContentEscapeMode;
import no.kantega.lab.limber.dom.element.ElementNode;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public interface IDomElementNodeMorphable extends IDomNodeMorphable<ElementNode> {

    @Nonnull
    <N2 extends AbstractNode<? extends N2>> Object addChild(int index, @Nonnull N2 node);

    @Nonnull
    IDomElementNodeRepresentable addChildAndStay(int index, @Nonnull AbstractNode<?> node);

    @Nonnull
    Object addChild(int index, @Nonnull CharSequence tagName);

    @Nonnull
    IDomElementNodeRepresentable addChildAndStay(int index, @Nonnull CharSequence tagName);

    @Nonnull
    Object addText(int index, @Nonnull CharSequence text);

    @Nonnull
    IDomElementNodeRepresentable addTextAndStay(int index, @Nonnull CharSequence text);

    @Nonnull
    Object addText(int index, @Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    IDomElementNodeRepresentable addTextAndStay(int index, @Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    <N2 extends AbstractNode<? extends N2>> Object appendChild(@Nonnull N2 node);

    @Nonnull
    IDomElementNodeRepresentable appendChildAndStay(@Nonnull AbstractNode<?> node);

    @Nonnull
    Object appendChild(@Nonnull CharSequence tagName);

    @Nonnull
    IDomElementNodeRepresentable appendChildAndStay(@Nonnull CharSequence tagName);

    @Nonnull
    Object appendText(@Nonnull CharSequence text);

    @Nonnull
    IDomElementNodeRepresentable appendTextAndStay(@Nonnull CharSequence text);

    @Nonnull
    Object appendText(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    IDomElementNodeRepresentable appendTextAndStay(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    <N2 extends AbstractNode<? extends N2>> Object prependChild(@Nonnull N2 node);

    @Nonnull
    IDomElementNodeRepresentable prependChildAndStay(@Nonnull AbstractNode<?> node);

    @Nonnull
    Object prependChild(@Nonnull CharSequence tagName);

    @Nonnull
    IDomElementNodeRepresentable prependChildAndStay(@Nonnull CharSequence tagName);

    @Nonnull
    Object prependText(@Nonnull CharSequence text);

    @Nonnull
    IDomElementNodeRepresentable prependTextAndStay(@Nonnull CharSequence text);

    @Nonnull
    Object prependText(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    IDomElementNodeRepresentable prependTextAndStay(@Nonnull CharSequence text, @Nonnull ContentEscapeMode contentEscapeMode);

    @Nonnull
    Object wrap(@Nonnull ElementNode elementNode);

    @Nonnull
    IDomElementNodeRepresentable wrapAndStay(@Nonnull ElementNode elementNode);

    @Nonnull
    Object wrap(@Nonnull CharSequence tagName);

    @Nonnull
    IDomElementNodeRepresentable wrapAndStay(@Nonnull CharSequence tagName);

    @Nonnull
    IDomElementNodeRepresentable setTagName(@Nonnull CharSequence tagName);

    @Nonnull
    IDomElementNodeRepresentable putAttr(@Nonnull CharSequence key, CharSequence value);

    @Nonnull
    IDomElementNodeRepresentable removeAttr(@Nonnull CharSequence key);

    @Nonnull
    IDomElementNodeRepresentable setId(@Nonnull CharSequence id);

    @Nonnull
    IDomElementNodeRepresentable setRandomId();

    @Nonnull
    IDomElementNodeRepresentable setRandomIdIfNone();

    @Nonnull
    IDomElementNodeRepresentable removeId();

    @Nonnull
    IDomElementNodeRepresentable addCssClass(@Nonnull CharSequence cssClassName);

    @Nonnull
    IDomElementNodeRepresentable setCssClasses(@Nonnull List<? extends CharSequence> cssClassNames);

    @Nonnull
    IDomElementNodeRepresentable removeCssClass(@Nonnull CharSequence cssClassName);

    @Nonnull
    IDomElementNodeRepresentable addCssStyle(@Nonnull CharSequence styleKey, CharSequence styleValue);

    @Nonnull
    IDomElementNodeRepresentable setCssStyles(@Nonnull Map<? extends CharSequence, ? extends CharSequence> cssStyles);

    @Nonnull
    IDomElementNodeRepresentable removeCssStyle(@Nonnull CharSequence styleKey);

    @Nonnull
    IDomElementNodeRepresentable addAjaxEvent(@Nonnull AjaxEventTrigger ajaxEventTrigger, @Nonnull IAjaxCallback<? super ElementNode> ajaxCallback);

    @Nonnull
    IDomElementNodeRepresentable removeAjaxEvent(@Nonnull IAjaxCallback<?> ajaxCallback);

    @Nonnull
    IDomElementNodeRepresentable removeAjaxEvent(@Nonnull AjaxEventTrigger ajaxEventTrigger);

}
