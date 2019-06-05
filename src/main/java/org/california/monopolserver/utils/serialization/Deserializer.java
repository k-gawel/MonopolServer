package org.california.monopolserver.utils.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.california.monopolserver.utils.annotations.JSONSubTypes;
import org.california.monopolserver.utils.annotations.JSONTypeInfo;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

public class Deserializer<T> extends StdDeserializer<T> {

    private static ObjectMapper mapper = new JSONMapper();

    private final Class<T> clazz;

    public Deserializer(Class<T> vc) {
        super(vc);
        this.clazz = vc;
    }

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = getNode(jsonParser);
        Class<? extends T> subClass;
        T result;

        if (hasSuperclassProperty(node))
            return (T) mapper.treeToValue(node, clazz.getSuperclass());


        subClass = getSubclass(node);

        if (subClass != null)
            result = (T) mapper.treeToValue(node, subClass);
        else if (!isAbstract(clazz)) {
            try {
                result = clazz.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                result = null;
            }
        } else
            throw new IllegalStateException(node.toString());

        try {
            setFields(node, result);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;
    }

    private JsonNode getNode(JsonParser parser) throws IOException {
        return parser.getCodec().readTree(parser);
    }

    private boolean isAbstract(Class<?> clazz) {
        return Modifier.isAbstract(clazz.getModifiers());
    }

    private String getPropertyName(Class<?> clazz) {
        try {
            return clazz.getAnnotation(JSONTypeInfo.class).value();
        } catch (Exception e) {
            return null;
        }
    }

    private String getPropertyName() {
        return getPropertyName(clazz);
    }

    private JSONSubTypes.Type[] getDeclaredSubtypes(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(JSONSubTypes.class))
            return null;

        return clazz.getAnnotation(JSONSubTypes.class).value();
    }

    private Class<? extends T> getSubclass(JsonNode node) {
        if (!node.has(getPropertyName()))
            return null;

        String property = node.get(getPropertyName()).asText();
        removeNode(node, getPropertyName());

        for (JSONSubTypes.Type type : getDeclaredSubtypes(clazz))
            if (type.name().equals(property))
                return type.value();

        return null;
    }

    private boolean hasSuperclassProperty(JsonNode node) {
        String propertyName = getPropertyName(clazz.getSuperclass());
        return node.has(propertyName);
    }

    private void removeNode(JsonNode node, String property) {
        ObjectNode objectNode = (ObjectNode) node;
        objectNode.remove(property);
    }

    private void setFields(JsonNode node, T result) throws IllegalAccessException, IOException {
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            JsonNode value = node.get(field.getName());

            field.set(result, mapper.readValue(value.toString(), field.getType()));
        }
    }

}
