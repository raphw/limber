package no.kantega.lab.limber.dom.analysis;

public interface IHtmlElementReflector {

    boolean isBlock(CharSequence tagName);

    boolean isInline(CharSequence tagName);

    boolean isEmpty(CharSequence tagName);

    boolean isFormatAsInline(CharSequence tagName);

    boolean isPreserveWhiteSpace(CharSequence tagName);
}
