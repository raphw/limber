package no.kantega.lab.limber.dom.analysis;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Html4ElementReflector implements IHtmlElementReflector {

    private static final Set<String> blockTags = new HashSet<String>(Arrays.asList(
            "html", "head", "body", "frameset", "script", "noscript", "style", "meta", "link", "title", "frame",
            "noframes", "section", "nav", "aside", "hgroup", "header", "footer", "p", "h1", "h2", "h3", "h4", "h5", "h6",
            "ul", "ol", "pre", "div", "blockquote", "hr", "address", "figure", "figcaption", "form", "fieldset", "ins",
            "del", "s", "dl", "dt", "dd", "li", "table", "caption", "thead", "tfoot", "tbody", "colgroup", "col", "tr", "th",
            "td", "video", "audio", "canvas", "details", "menu", "plaintext"));

    private static final Set<String> inlineTags = new HashSet<String>(Arrays.asList(
            "object", "base", "font", "tt", "i", "b", "u", "big", "small", "em", "strong", "dfn", "code", "samp", "kbd",
            "var", "cite", "abbr", "time", "acronym", "mark", "ruby", "rt", "rp", "a", "img", "br", "wbr", "map", "q",
            "sub", "sup", "bdo", "iframe", "embed", "span", "input", "select", "textarea", "label", "button", "optgroup",
            "option", "legend", "datalist", "keygen", "output", "progress", "meter", "area", "param", "source", "track",
            "summary", "command", "device"));

    private static final Set<String> emptyTags = new HashSet<String>(Arrays.asList(
            "meta", "link", "base", "frame", "img", "br", "wbr", "embed", "hr", "input", "keygen", "col", "command",
            "device"));

    private static final Set<String> formatAsInlineTags = new HashSet<String>(Arrays.asList(
            "title", "a", "p", "h1", "h2", "h3", "h4", "h5", "h6", "pre", "address", "li", "th", "td", "script", "style",
            "ins", "del", "s"));

    private static final Set<String> preserveWhitespaceTags = new HashSet<String>(Arrays.asList(
            "pre", "plaintext", "title", "textarea"));

    @Override
    public boolean isBlock(CharSequence tagName) {
        return contains(tagName, blockTags);
    }

    @Override
    public boolean isInline(CharSequence tagName) {
        return contains(tagName, inlineTags);
    }

    @Override
    public boolean isEmpty(CharSequence tagName) {
        return contains(tagName, emptyTags);
    }

    @Override
    public boolean isFormatAsInline(CharSequence tagName) {
        return contains(tagName, formatAsInlineTags);
    }

    @Override
    public boolean isPreserveWhiteSpace(CharSequence tagName) {
        return contains(tagName, preserveWhitespaceTags);
    }

    private boolean contains(CharSequence tagName, Collection<String> containingCollection) {
        return containingCollection.contains(tagName.toString());
    }
}
