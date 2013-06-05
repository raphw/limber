package no.kantega.lab.limber.dom.element;

import no.kantega.lab.limber.dom.abstraction.IDomTextNodeMorphable;
import no.kantega.lab.limber.dom.abstraction.IDomTextNodeQueryable;
import org.apache.commons.lang3.StringEscapeUtils;

public class TextNode extends AbstractNode<TextNode> implements IDomTextNodeMorphable<TextNode>, IDomTextNodeQueryable {

    private String content;

    public TextNode(CharSequence content) {
        setContent(content);
    }

    @Override
    public String getContent() {
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
    public int size() {
        if (content == null) {
            return 0;
        } else {
            return content.length();
        }
    }

    @Override
    public String toString() {
        return String.format("TextNode[%b,%s]", isRendered(), content);
    }
}
