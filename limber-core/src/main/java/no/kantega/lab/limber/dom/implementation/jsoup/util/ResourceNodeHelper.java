package no.kantega.lab.limber.dom.implementation.jsoup.util;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.net.URI;

public final class ResourceNodeHelper {

    private ResourceNodeHelper() {
        /* empty */
    }

    public static Element makeCssHeadExternalLink(URI resourceLocation) {
        Attributes attributes = new Attributes();
        attributes.put("type", "text/css");
        attributes.put("rel", "stylesheet");
        // TODO: Find way to only filter page requests.
        attributes.put("href", resourceLocation.toString());
        return new Element(Tag.valueOf("link"), "http://localhost:8080", attributes);
    }

    public static Element makeJsHeadExternalLink(URI resourceLocation) {
        Attributes attributes = new Attributes();
        attributes.put("type", "text/javascript");
        // TODO: Find way to only filter page requests.
        attributes.put("src ", resourceLocation.toString());
        return new Element(Tag.valueOf("script"), "http://localhost:8080", attributes);
    }

    public static Element makeCssHeadEmbededLink(String value) {
        Attributes attributes = new Attributes();
        attributes.put("type", "text/css");
        attributes.put("rel", "stylesheet");
        Element e = new Element(Tag.valueOf("link"), "http://localhost:8080", attributes);
        e.html(value);
        return e;
    }

    public static Element makeJsHeadEmbededlLink(String value) {
        Attributes attributes = new Attributes();
        attributes.put("type", "text/javascript");
        Element e = new Element(Tag.valueOf("script"), "http://localhost:8080", attributes);
        e.html(value);
        return e;
    }
}
