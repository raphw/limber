package no.kantega.lab.limber.kernel.meta;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PageRestitution {

    RestitutionPolicy value();
}
