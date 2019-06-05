package org.california.monopolserver.controller;

import io.github.classgraph.ClassGraph;
import org.california.monopolserver.model.ws_message.request.RequestMessage;
import org.california.monopolserver.utils.annotations.RequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
public class WebSocketController {

    private final Map<Class<? extends RequestMessage>, Method> requestProcessors = new HashMap<>();
    private final ApplicationContext appContext;


    @Autowired
    public WebSocketController(ApplicationContext appContext) {
        this.appContext = appContext;
        setRequestProcessors();
    }

    @MessageMapping("/game")
    public void map(RequestMessage requestMessage) throws IllegalAccessException, InvocationTargetException {
        Method method = requestProcessors.get(requestMessage.getClass());
        Object service = appContext.getBean(method.getDeclaringClass());
        method.invoke(service, requestMessage);
;    }

    public void setRequestProcessors() {
        new ClassGraph()
                .blacklistClasses("org.california")
                .whitelistPackages("org.california.monopolserver.service")
                .scan()
                .getAllClasses()
                .getNames()
                .stream()
                .map(this::getClassFromName)
                .flatMap(c -> Arrays.stream(c.getMethods()))
                .filter(m -> m.isAnnotationPresent(RequestProcessor.class))
                .forEach(m -> this.requestProcessors.put(m.getAnnotation(RequestProcessor.class).value(), m));
    }

    private Class<?> getClassFromName(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
