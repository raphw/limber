package no.kantega.lab.limber.servlet.meta;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PageRenderSynchronization {

    boolean value();
}
