package com.drestive.rapitoire.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by mustafa on 20/08/2016.
 */

@Target(java.lang.annotation.ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Element {

    String label();

    ElementType type() default ElementType.STRING;

    int minSize() default 1;

    int maxSize() default 100;

    boolean required() default false;

    boolean readOnly() default false;

    String source() default "";

    String options() default "";

    String groupLabel() default "";

    Class ref() default Object.class;

    IncludeOptions[] includeFor() default {IncludeOptions.ALL};

    PermitWriteOptions[] permitWriteFor() default {PermitWriteOptions.ALL};
}
