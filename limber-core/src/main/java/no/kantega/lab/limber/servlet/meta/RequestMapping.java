package no.kantega.lab.limber.servlet.meta;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    String[] value();
}
