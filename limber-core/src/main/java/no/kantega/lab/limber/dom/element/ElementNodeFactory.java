package no.kantega.lab.limber.dom.element;

public class ElementNodeFactory {

    private ElementNodeFactory() {
        /* empty */
    }

    public static ElementNode<?> make(CharSequence tagName) {
        return new PlainElementNode(tagName);
    }
}
