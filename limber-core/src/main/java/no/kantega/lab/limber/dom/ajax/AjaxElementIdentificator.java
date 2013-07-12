package no.kantega.lab.limber.dom.ajax;


public enum AjaxElementIdentificator {
    SELECTOR("selector"),
    XPATH("xpath");

    private final String identificatorName;

    private AjaxElementIdentificator(String identificatorName) {
        this.identificatorName = identificatorName;
    }

    public String getIdentificatorName() {
        return identificatorName;
    }
}
