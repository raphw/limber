package no.kantega.lab.limber.dom.filter.util;

import org.apache.commons.lang3.StringUtils;

public enum QueryMatchMode {

    FULL_MATCH,
    FULL_MATCH_IGNORE_CASE,
    STARTS_WITH,
    STARTS_WITH_IGNORE_CASE,
    ENDS_WITH,
    ENDS_WITH_IGNORE_CASE,
    CONTAINS,
    CONTAINS_IGNORE_CASE;

    public boolean compare(CharSequence actual, CharSequence found) {
        switch (this) {
            case FULL_MATCH:
                return StringUtils.equals(actual, found);
            case FULL_MATCH_IGNORE_CASE:
                return StringUtils.equalsIgnoreCase(actual, found);
            case STARTS_WITH:
                return StringUtils.startsWith(actual, found);
            case STARTS_WITH_IGNORE_CASE:
                return StringUtils.startsWithIgnoreCase(actual, found);
            case ENDS_WITH:
                return StringUtils.endsWith(actual, found);
            case ENDS_WITH_IGNORE_CASE:
                return StringUtils.endsWithIgnoreCase(actual, found);
            case CONTAINS:
                return StringUtils.contains(actual, found);
            case CONTAINS_IGNORE_CASE:
                return StringUtils.containsIgnoreCase(actual, found);
            default:
                throw new IllegalStateException();
        }
    }
}
