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
    CONTAINS_IGNORE_CASE,
    MATCHES_REGEX;

    public boolean compare(CharSequence found, CharSequence parameter) {
        switch (this) {
            case FULL_MATCH:
                return StringUtils.equals(found, parameter);
            case FULL_MATCH_IGNORE_CASE:
                return StringUtils.equalsIgnoreCase(found, parameter);
            case STARTS_WITH:
                return StringUtils.startsWith(found, parameter);
            case STARTS_WITH_IGNORE_CASE:
                return StringUtils.startsWithIgnoreCase(found, parameter);
            case ENDS_WITH:
                return StringUtils.endsWith(found, parameter);
            case ENDS_WITH_IGNORE_CASE:
                return StringUtils.endsWithIgnoreCase(found, parameter);
            case CONTAINS:
                return StringUtils.contains(found, parameter);
            case CONTAINS_IGNORE_CASE:
                return StringUtils.containsIgnoreCase(found, parameter);
            case MATCHES_REGEX:
                return found != null && parameter != null
                        && found.toString().matches(parameter.toString());
            default:
                throw new IllegalStateException();
        }
    }
}
