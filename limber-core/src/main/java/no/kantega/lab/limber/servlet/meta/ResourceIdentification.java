package no.kantega.lab.limber.servlet.meta;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResourceIdentification {
    ResourceType value();
    String location() default "";
}
