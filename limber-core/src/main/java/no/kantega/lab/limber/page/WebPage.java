package no.kantega.lab.limber.page;

import no.kantega.lab.limber.dom.implementations.DomDocumentSelection;
import no.kantega.lab.limber.dom.interfaces.IDomDocumentSelection;

import java.io.*;

public class WebPage {

    private final IDomDocumentSelection domSelection;

    public WebPage() {
        this.domSelection = new DomDocumentSelection(this);
    }

    public final InputStream getDocumentResourceStream() {
        // TODO: Make private and allow to change value by annotation.
        String name = getClass().getSimpleName() + ".html";
        InputStream inputStream = getClass().getResourceAsStream(name);
        if (inputStream == null) {
            throw new RuntimeException("Cannot find resource.");
        }
        return inputStream;
    }

    public final void writePageToStream(OutputStream outputStream) throws IOException {
        // TODO: Find better way of doing this.
        InputStream inputStream = getDocumentResourceStream();
        String documentHtml = domSelection.getOutput();

        writeString(documentHtml, outputStream);
//        copyStream(inputStream, outputStream);
        inputStream.close();

    }

    private static void writeString(String input, OutputStream output) throws IOException {
        // TODO: Move this outside of class that is used by an end user.
        Writer writer = new OutputStreamWriter(output);
        writer.write(input);
        writer.flush();
    }

    private static void copyStream(InputStream input, OutputStream output) throws IOException {
        // TODO: Move this outside of class that is used by an end user.
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        output.flush();
    }

    public final IDomDocumentSelection select() {
        return domSelection;
    }

}
