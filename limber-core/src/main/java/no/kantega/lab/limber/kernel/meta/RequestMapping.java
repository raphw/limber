package no.kantega.lab.limber.kernel.meta;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    String[] value();
}
