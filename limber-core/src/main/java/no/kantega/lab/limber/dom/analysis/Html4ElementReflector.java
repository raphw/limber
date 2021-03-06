package no.kantega.lab.limber.dom.analysis;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Html4ElementReflector implements IHtmlElementReflector {

    private static final Set<String> BLOCK_TAGS = new HashSet<String>(Arrays.asList(
            "html", "head", "body", "frameset", "script", "noscript", "style", "meta", "link", "title", "frame",
            "noframes", "section", "nav", "aside", "hgroup", "header", "footer", "p", "h1", "h2", "h3", "h4", "h5", "h6",
            "ul", "ol", "pre", "div", "blockquote", "hr", "address", "figure", "figcaption", "form", "fieldset", "ins",
            "del", "s", "dl", "dt", "dd", "li", "table", "caption", "thead", "tfoot", "tbody", "colgroup", "col", "tr", "th",
            "td", "video", "audio", "canvas", "details", "menu", "plaintext"));

    private static final Set<String> INLINE_TAGS = new HashSet<String>(Arrays.asList(
            "object", "base", "font", "tt", "i", "b", "u", "big", "small", "em", "strong", "dfn", "code", "samp", "kbd",
            "var", "cite", "abbr", "time", "acronym", "mark", "ruby", "rt", "rp", "a", "img", "br", "wbr", "map", "q",
            "sub", "sup", "bdo", "iframe", "embed", "span", "input", "select", "textarea", "label", "button", "optgroup",
            "option", "legend", "datalist", "keygen", "output", "progress", "meter", "area", "param", "source", "track",
            "summary", "command", "device"));

    private static final Set<String> EMPTY_TAGS = new HashSet<String>(Arrays.asList(
            "meta", "link", "base", "frame", "img", "br", "wbr", "embed", "hr", "input", "keygen", "col", "command",
            "device"));

    private static final Set<String> FORMAT_AS_INLINE_TAGS = new HashSet<String>(Arrays.asList(
            "title", "a", "p", "h1", "h2", "h3", "h4", "h5", "h6", "pre", "address", "li", "th", "td", "script", "style",
            "ins", "del", "s"));

    private static final Set<String> PRESERVE_WHITESPACE_TAGS = new HashSet<String>(Arrays.asList(
            "pre", "plaintext", "title", "textarea"));

    @Override
    public boolean isBlock(CharSequence tagName) {
        return contains(tagName, BLOCK_TAGS);
    }

    @Override
    public boolean isInline(CharSequence tagName) {
        return contains(tagName, INLINE_TAGS);
    }

    @Override
    public boolean isEmpty(CharSequence tagName) {
        return contains(tagName, EMPTY_TAGS);
    }

    @Override
    public boolean isFormatAsInline(CharSequence tagName) {
        return contains(tagName, FORMAT_AS_INLINE_TAGS);
    }

    @Override
    public boolean isPreserveWhiteSpace(CharSequence tagName) {
        return contains(tagName, PRESERVE_WHITESPACE_TAGS);
    }

    private boolean contains(CharSequence tagName, Collection<String> containingCollection) {
        return containingCollection.contains(tagName.toString());
    }
}
