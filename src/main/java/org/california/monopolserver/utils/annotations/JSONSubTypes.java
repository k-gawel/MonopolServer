package org.california.monopolserver.utils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JSONSubTypes {

    Type[] value();

    @interface Type {
        Class value();
        String name();
    }

}
