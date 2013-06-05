package no.kantega.lab.limber.dom.element;

import org.apache.commons.lang3.StringEscapeUtils;

public enum ContentEscapeMode {
    NONE,
    HTML3,
    HTML4,
    XML;

    public String translate(CharSequence value) {
        switch (this) {
            case HTML3:
                return StringEscapeUtils.ESCAPE_HTML3.translate(value);
            case HTML4:
                return StringEscapeUtils.ESCAPE_HTML4.translate(value);
            case XML:
                return StringEscapeUtils.ESCAPE_XML.translate(value);
            case NONE:
                return value.toString();
            default:
                throw new IllegalStateException();
        }
    }

    public static ContentEscapeMode getDefault() {
        return HTML4;
    }
}
