package org.california.monopolserver.utils.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.california.monopolserver.utils.annotations.JSONTypeInfo;
import org.california.monopolserver.utils.annotations.JSONSubTypes;

import java.io.IOException;
import java.lang.reflect.Field;

public class Serializer<T> extends StdSerializer<T> {

    private Class<T> clazz;

    public Serializer(Class<T> t) {
        super(t);
        this.clazz = t;
    }

    public Serializer() {
        this(null);
    }

    @Override
    public void serialize(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        this.clazz = (Class<T>) t.getClass();
        ObjectWriter writer = new ObjectMapper().writerFor(t.getClass());
        Object outputTarget = jsonGenerator.getOutputTarget();

        jsonGenerator.writeStartObject();
        write(t, jsonGenerator);
        for(Field field : t.getClass().getFields()) {
            try {
                jsonGenerator.writeObjectField(field.getName(), field.get(t));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        jsonGenerator.writeEndObject();
    }

    private boolean isSuperclassAnnotated(Class<?> clazz) {
        return clazz.getSuperclass().isAnnotationPresent(JSONTypeInfo.class);
    }

    private String getPropertyName(Class<?> clazz) {
        Class<?> superclass = clazz.getSuperclass();
        JSONTypeInfo annotation = superclass.getAnnotation(JSONTypeInfo.class);

        return annotation == null ? superclass.getSimpleName().concat("_type")
                : annotation.value();
    }

    private String getValueName(Class<?> clazz) {
        for(JSONSubTypes.Type type : getTypes(clazz))
            if(type.value().equals(clazz))
                return type.name();

        return clazz.getSimpleName();
    }

    private JSONSubTypes.Type[] getTypes(Class<?> clazz) {
        return clazz.getSuperclass().getAnnotation(JSONSubTypes.class).value();
    }

    private void write(T t, JsonGenerator jsonGenerator) throws IOException {
        Class<?> clazz = this.clazz;
        while(isSuperclassAnnotated(clazz)) {
            String property = getPropertyName(clazz);
            String value = getValueName(clazz);
            jsonGenerator.writeStringField(property, value);
            clazz = clazz.getSuperclass();
        }
    }

    @Override
    public Class<T> handledType() {
        return clazz;
    }
}
