package org.california.monopolserver.model.ws_message.request.transaction.action.remove;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.california.monopolserver.utils.serialization.Deserializer;
import org.california.monopolserver.utils.serialization.JSONMapper;
import org.california.monopolserver.utils.serialization.Serializer;
import org.california.monopolserver.model.ws_message.request.RequestMessage;
import org.california.monopolserver.model.ws_message.request.transaction.TransactionRequest;
import org.california.monopolserver.model.ws_message.request.transaction.init.TransactionInitRequest;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;


public class TransactionRemoveItemRequestTest {


    @Test
    public void deserializeTest() throws IOException {

        String json = "{\n" +
                "  \"request_type\": \"transaction\",\n" +
                "  \"transaction_request_type\": \"init\",\n" +
                "  \"session\": \"SESSION\",\n" +
                "  \"game\": \"GAME\",\n" +
                "  \"transaction\": \"TRANSACTION\",\n" +
                "  \"initiator\": \"INITIATOR\",\n" +
                "  \"invited\": \"INVITED\"\n" +
                "}\n";
        ObjectMapper mapper = new ObjectMapper();

        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(json);

        TransactionInitRequest requestMessage = mapper.readValue(parser, TransactionInitRequest.class);
        System.out.println(requestMessage.game + " :game | session: " +requestMessage.session);
    }


    @Test
    public void customSerializeTest() throws JsonProcessingException {

        TransactionInitRequest message = new TransactionInitRequest();
        message.session = "SESSION";
        message.game = "GAME";
        message.initiator = "INITIATOR";
        message.invited = "INVITED";

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("Deserializer module", new Version(0, 0, 0, null, null, null));
        module.addSerializer(RequestMessage.class, new Serializer<>(RequestMessage.class));
        mapper.registerModule(module);

        ObjectWriter writer = mapper.writerFor(RequestMessage.class);
        String json = writer.writeValueAsString(message);
        System.out.println(json);
    }

    @Test
    public void serializeTest() throws JsonProcessingException {
        TransactionInitRequest message = new TransactionInitRequest();
        message.session = "SESSION";
        message.game = "GAME";
        message.initiator = "INITIATOR";
        message.invited = "INVITED";

        ObjectWriter writer = new ObjectMapper().writerFor(RequestMessage.class);
        String json = writer.writeValueAsString(message);

        System.out.println(json);
    }

    @Test
    public void reflectionTest() {

        Field[] fields = TransactionInitRequest.class.getDeclaredFields();

        for(Field field : fields)
            System.out.println(field.getName());
    }


    @Test
    public void genericDeserializer() throws IOException {
        String json = "{\n" +
                "  \"request_type\": \"transaction\",\n" +
                "  \"transaction_request_type\": \"init\",\n" +
                "  \"session\": \"SESSION\",\n" +
                "  \"game\": \"GAME\",\n" +
                "  \"transaction\": \"TRANSACTION\",\n" +
                "  \"initiator\": \"INITIATOR\",\n" +
                "  \"invited\": \"INVITED\"\n" +
                "}\n";
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(json);

        ObjectMapper mapper = new JSONMapper();
        SimpleModule module = new SimpleModule("Deserializer module", new Version(0, 0, 0, null, null, null));
        module.addDeserializer(RequestMessage.class, new Deserializer<>(RequestMessage.class));
        module.addDeserializer(TransactionRequest.class, new Deserializer<>(TransactionRequest.class));
        module.addDeserializer(TransactionInitRequest.class, new Deserializer<>(TransactionInitRequest.class));
        module.addSerializer(RequestMessage.class, new Serializer<>(RequestMessage.class));
        mapper.registerModule(module);
        RequestMessage request = mapper.readValue(json, TransactionInitRequest.class);
        String jsonResult      = mapper.writeValueAsString(request);
        System.out.println(json);
        System.out.println(jsonResult);
        System.out.println(json.equals(jsonResult));
    }


    @Test
    public void packageScanTest() {

        JSONMapper mapper = new JSONMapper();
        System.out.println(this.getClass().getPackage());
        mapper.getDeserializableClasses().forEach(c -> System.out.println(c.getSimpleName()));


    }


}
