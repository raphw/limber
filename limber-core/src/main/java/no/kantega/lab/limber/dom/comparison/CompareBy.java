package no.kantega.lab.limber.dom.comparison;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CompareBy {

    Class<? extends IDomComparisonStrategy> value();
}
