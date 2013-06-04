package no.kantega.lab.limber.dom.implementation.limber.element;

import no.kantega.lab.limber.dom.implementation.limber.abstraction.IDomTextNodeMorphable;
import org.apache.commons.lang3.StringEscapeUtils;

public class TextNode extends AbstractNode<TextNode> implements IDomTextNodeMorphable<TextNode> {

    private String content;

    public TextNode(CharSequence content) {
        setContent(content);
    }

    public CharSequence getContent() {
        return content;
    }

    @Override
    public TextNode setContent(CharSequence content) {
        if (content == null) {
            clear();
        } else {
            this.content = StringEscapeUtils.ESCAPE_HTML4.translate(content);
        }
        return this;
    }

    @Override
    public TextNode clear() {
        content = null;
        return this;
    }

    @Override
    public String toString() {
        return String.format("TextNode[%b,%s]", isRender(), content);
    }
}
