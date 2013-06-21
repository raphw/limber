package no.kantega.lab.limber.servlet.meta;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PageVersioning {
    VersioningType value();
}
