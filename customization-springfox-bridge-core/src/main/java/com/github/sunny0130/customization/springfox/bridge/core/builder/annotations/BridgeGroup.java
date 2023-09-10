package com.github.sunny0130.customization.springfox.bridge.core.builder.annotations;

import java.lang.annotation.*;

/**
 * mark class for specifying springfox group
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BridgeGroup {
    String value() default "";

    String[] authors() default "";
}
