package no.kantega.lab.limber.dom.element;

import no.kantega.lab.limber.dom.abstraction.IDomTextNodeMorphable;
import no.kantega.lab.limber.dom.abstraction.IDomTextNodeQueryable;

import javax.annotation.Nonnull;

public class TextNode extends AbstractNode<TextNode> implements IDomTextNodeMorphable<TextNode>, IDomTextNodeQueryable {

    private String content;

    public TextNode(CharSequence content) {
        this(content, ContentEscapeMode.getDefault());
    }

    public TextNode(CharSequence content, @Nonnull ContentEscapeMode contentEscapeMode) {
        setContent(content, contentEscapeMode);
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public TextNode setContent(CharSequence content) {
        return setContent(content, ContentEscapeMode.getDefault());
    }

    @Override
    public TextNode setContent(CharSequence content, @Nonnull ContentEscapeMode contentEscapeMode) {
        if (content == null) {
            clear();
        } else {
            this.content = contentEscapeMode.translate(content);
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
