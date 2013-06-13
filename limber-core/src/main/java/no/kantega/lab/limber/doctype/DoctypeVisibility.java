package no.kantega.lab.limber.doctype;

public enum DoctypeVisibility {
    PUBLIC("PUBLIC"),
    SYSTEM("SYSTEM");

    private final String identifier;

    private DoctypeVisibility(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
