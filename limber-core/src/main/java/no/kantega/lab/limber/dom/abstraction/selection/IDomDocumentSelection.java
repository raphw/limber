package no.kantega.lab.limber.dom.abstraction.selection;

import java.net.URI;

public interface IDomDocumentSelection extends IDomSelection<IDomDocumentSelection> {

    void setTitle(String title);

    String getTitle();

    String getOutput();

    IDomDocumentSelection addExternalResource(HeadResource headerResource, URI resourceLocation);

    IDomDocumentSelection addEmbededResource(HeadResource headerResource, String value);

}
