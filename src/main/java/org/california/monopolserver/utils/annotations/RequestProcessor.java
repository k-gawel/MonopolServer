package org.california.monopolserver.utils.annotations;

import org.california.monopolserver.model.ws_message.request.RequestMessage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestProcessor {
    Class<? extends RequestMessage> value();
}
