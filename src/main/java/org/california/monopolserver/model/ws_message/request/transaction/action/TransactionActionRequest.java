package org.california.monopolserver.model.ws_message.request.transaction.action;

import org.california.monopolserver.utils.annotations.JSONTypeInfo;
import org.california.monopolserver.utils.annotations.JSONSubTypes;
import org.california.monopolserver.model.ws_message.request.transaction.TransactionRequest;
import org.california.monopolserver.model.ws_message.request.transaction.action.add.TransactionAddRequest;
import org.california.monopolserver.model.ws_message.request.transaction.action.remove.TransactionRemoveRequest;
import org.california.monopolserver.model.ws_message.request.transaction.action.status.TransactionStatusRequest;

@JSONTypeInfo("transaction_action_type")
@JSONSubTypes({
        @JSONSubTypes.Type(value = TransactionAddRequest.class, name = "add"),
        @JSONSubTypes.Type(value = TransactionRemoveRequest.class, name = "remove"),
        @JSONSubTypes.Type(value = TransactionStatusRequest.class, name = "status")
})
public abstract class TransactionActionRequest extends TransactionRequest {

    public String transaction;
    public String side;

}
