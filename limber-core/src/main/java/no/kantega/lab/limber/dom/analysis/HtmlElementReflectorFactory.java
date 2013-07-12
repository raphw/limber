package no.kantega.lab.limber.dom.analysis;


public class HtmlElementReflectorFactory {

    private static final HtmlElementReflectorFactory INSTANCE = new HtmlElementReflectorFactory();

    public static HtmlElementReflectorFactory getInstance() {
        return INSTANCE;
    }

    private HtmlElementReflectorFactory() {
        /* empty */
    }

    public IHtmlElementReflector getReflector(IHtmlElementReflector htmlElementReflector) {
        // TODO: Add more reflectors and implement actual factory.
        return new Html4ElementReflector();
    }

}
