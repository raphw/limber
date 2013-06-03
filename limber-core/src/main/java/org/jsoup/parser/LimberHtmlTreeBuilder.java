package org.jsoup.parser;

import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

public class LimberHtmlTreeBuilder extends HtmlTreeBuilder {


    @Override
    Element insertEmpty(Token.StartTag startTag) {
        Tag tag = Tag.valueOf(startTag.name());
        Element el = new Element(tag, baseUri, startTag.attributes);
        insertNode(el);
        if (startTag.isSelfClosing()) {
            if (tag.isKnownTag()) {
                if (tag.isSelfClosing()) tokeniser.acknowledgeSelfClosingFlag(); // if not acked, promulagates error
            } else {
                // unknown tag, remember this is self closing for output
                tag.setSelfClosing();
                tokeniser.acknowledgeSelfClosingFlag(); // not an distinct error
            }
        }
        return el;
    }

    void insert(Token.Comment commentToken) {
        Comment comment = new Comment(commentToken.getData(), baseUri);
        insertNode(comment);
    }

    private void insertNode(Node node) {
        // if the stack hasn't been set up yet, elements (doctype, comments) go into the doc
        if (stack.size() == 0)
            doc.appendChild(node);
        else if (isFosterInserts())
            insertInFosterParent(node);
        else
            currentElement().appendChild(node);
    }
}
