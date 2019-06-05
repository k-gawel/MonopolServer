package org.california.monopolserver.model.ws_message.response.transaction;

import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.utils.annotations.JSONTypeInfo;
import org.california.monopolserver.utils.annotations.JSONSubTypes;
import org.california.monopolserver.model.ws_message.response.ResponseMessage;
import org.california.monopolserver.model.ws_message.response.transaction.actions.TransactionActionResponse;
import org.california.monopolserver.model.ws_message.response.transaction.init.TransactionInitResponse;
import org.california.monopolserver.model.ws_message.response.transaction.result.TransactionResultResponse;

@JSONTypeInfo("transaction_response_type")
@JSONSubTypes({
        @JSONSubTypes.Type(value = TransactionActionResponse.class, name = "action"),
        @JSONSubTypes.Type(value = TransactionInitResponse.class, name = "init"),
        @JSONSubTypes.Type(value = TransactionResultResponse.class, name = "result")
})
public abstract class TransactionResponse extends ResponseMessage {

    public String transaction;


    public TransactionResponse(Transaction transaction) {
        super(transaction.getGame());

        this.transaction = transaction.getUUID();
    }


}
