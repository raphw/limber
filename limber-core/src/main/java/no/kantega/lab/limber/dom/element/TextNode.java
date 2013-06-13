package no.kantega.lab.limber.dom.element;

import no.kantega.lab.limber.dom.abstraction.IDomTextNodeMorphable;
import no.kantega.lab.limber.dom.abstraction.IDomTextNodeQueryable;
import no.kantega.lab.limber.servlet.IResponseContainer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.OutputStream;

public class TextNode extends AbstractNode<TextNode> implements IDomTextNodeMorphable<TextNode, TextNode>, IDomTextNodeQueryable {

    private static final String[] searchChars = {"\n", "\r"}, replaceChars = {"\\n", "\\r"};

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

    @Nonnull
    @Override
    public TextNode setContent(CharSequence content) {
        return setContent(content, ContentEscapeMode.getDefault());
    }

    @Nonnull
    @Override
    public TextNode setContent(CharSequence content, @Nonnull ContentEscapeMode contentEscapeMode) {
        if (content == null) {
            clear();
        } else {
            this.content = contentEscapeMode.translate(content);
        }
        return this;
    }

    @Nonnull
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
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public String toString() {
        return String.format("%s[content='%s',rendered=%b]", getClass().getName(),
                StringUtils.replaceEach(getContent(), searchChars, replaceChars), isRendered());
    }

    @Override
    public boolean onRender(@Nonnull OutputStream outputStream, @Nonnull IResponseContainer response) throws IOException {
        if (!StringUtils.isBlank(content)) {
            IOUtils.write(content, outputStream);
        }
        return true;
    }
}
