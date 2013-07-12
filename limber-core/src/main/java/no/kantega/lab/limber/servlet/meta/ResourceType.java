package no.kantega.lab.limber.servlet.meta;


public enum ResourceType {

    PLAIN(null),
    TXT("txt"),
    HTML("html");

    private final String fileNameExtension;

    private ResourceType(String fileNameExtension) {
        this.fileNameExtension = fileNameExtension;
    }

    public String getFileNameExtension() {
        return fileNameExtension;
    }
}
