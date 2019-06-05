package org.california.monopolserver.utils.serialization;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.classgraph.ClassGraph;
import org.california.monopolserver.utils.annotations.JSONTypeInfo;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class JSONMapper extends ObjectMapper {

    private static Collection<Class<?>> deserializableClasses;
    private static Collection<Class<?>> serializableClasses;

    public JSONMapper() {
        SimpleModule module = new SimpleModule("JSONModule", new Version(2, 0, 0, null, null,null));
        set(module);
        registerModule(module);
    }

    private void set(SimpleModule module) {
        setSerializers(module);
        setDeserializers(module);
    }

    private void setDeserializers(SimpleModule module) {
        for(Class clazz : getDeserializableClasses())
            module.addDeserializer(clazz, new Deserializer<>(clazz));
    }

    private void setSerializers(SimpleModule module) {
        for(Class clazz : getSerializableClasses())
            module.addSerializer(clazz, new Serializer<>());
    }

    public Collection<Class<?>> getDeserializableClasses() {
        if(deserializableClasses == null)
            deserializableClasses = getAllPackageClasses("org.california.monopolserver.model.ws_message.request").stream()
                .filter(c -> c.isAnnotationPresent(JSONTypeInfo.class))
                .collect(Collectors.toSet());

        return deserializableClasses;
    }

    private Collection<Class<?>> getSerializableClasses() {
        if(serializableClasses == null)
            serializableClasses = getAllPackageClasses("org.california.monopolserver.model.ws_message.response").stream()
                .filter(c -> c.isAnnotationPresent(JSONTypeInfo.class))
                .collect(Collectors.toSet());

        return serializableClasses;
    }

    private Collection<Class<?>> getAllPackageClasses(String basePackage) {
        return new  ClassGraph()
                .blacklistClasses("org.california")
                .whitelistPackages(basePackage)
                .scan()
                .getAllClasses()
                .getNames()
                .stream()
                .map(this::getClassFromName)
                .collect(Collectors.toSet());
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
