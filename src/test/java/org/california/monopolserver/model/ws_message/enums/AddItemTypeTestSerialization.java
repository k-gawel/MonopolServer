package org.california.monopolserver.model.ws_message.enums;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.california.monopolserver.model.ws_message.request.transaction.action.add.TransactionAddNewDiscountRequest;
import org.junit.Test;

import java.io.IOException;

public class AddItemTypeTestSerialization {

    @Test
    public void test() throws IOException {
        TransactionAddNewDiscountRequest.DiscountChargeableType type = TransactionAddNewDiscountRequest.DiscountChargeableType.TOWN;

        String string = "{\"value\":\"xd\",\"type\":\"town_discount\"}";

        TransactionAddNewDiscountRequest.DiscountChargeableType result = new ObjectMapper().readerFor(MyBean.class).readValue(string);

        System.out.println(result.equals(type));
    }

    static class MyBean {
        public String name;
        public TransactionAddNewDiscountRequest.DiscountChargeableType type = TransactionAddNewDiscountRequest.DiscountChargeableType.TOWN;

        public String toString() {
            return name + type.toString();
        }
    }

}
