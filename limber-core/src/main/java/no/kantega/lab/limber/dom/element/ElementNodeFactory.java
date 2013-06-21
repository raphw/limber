package no.kantega.lab.limber.dom.element;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class ElementNodeFactory {

    private ElementNodeFactory() {
        /* empty */
    }

    private static final ElementNodeFactory INSTANCE = new ElementNodeFactory();

    private static final Map<String, String> TARGET_MAP = new HashMap<String, String>();

    static {
        TARGET_MAP.put("A", "href");
        TARGET_MAP.put("IMG", "src");
    }

    public static ElementNodeFactory getInstance() {
        return INSTANCE;
    }

    public ElementNode<?> make(CharSequence tagName) {
        if (isTargetable(tagName)) {
            return new PlainLinkNode(tagName);
        } else {
            return new PlainElementNode(tagName);
        }
    }

    @Nonnull
    public String getTargetAttr(CharSequence tagName) {
        return TARGET_MAP.get(tagName.toString());
    }

    public boolean isTargetable(CharSequence tagName) {
        return TARGET_MAP.containsKey(tagName.toString());
    }
}
