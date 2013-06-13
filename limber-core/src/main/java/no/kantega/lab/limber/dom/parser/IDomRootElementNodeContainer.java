package no.kantega.lab.limber.dom.parser;

import no.kantega.lab.limber.doctype.DoctypeDeclaration;
import no.kantega.lab.limber.dom.element.ElementNode;

import javax.annotation.Nonnull;

interface IDomRootElementNodeContainer {

    void setDoctype(@Nonnull DoctypeDeclaration doctypeDeclaration);

    void setRoot(@Nonnull ElementNode elementNode);
}
