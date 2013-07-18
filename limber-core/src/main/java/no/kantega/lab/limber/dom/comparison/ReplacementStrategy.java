package no.kantega.lab.limber.dom.comparison;


public enum ReplacementStrategy {
    REPLACE("replace");

    private final String replacementStrategyName;

    private ReplacementStrategy(String replacementStrategyName) {
        this.replacementStrategyName = replacementStrategyName;
    }

    public String getReplacementStrategyName() {
        return replacementStrategyName;
    }
}
