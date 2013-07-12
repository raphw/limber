package no.kantega.lab.limber.kernel.application;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoadOnStartup {

    int value();
}
