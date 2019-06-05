package org.california.monopolserver.model.ws_message.request.transaction;

import org.california.monopolserver.utils.annotations.JSONTypeInfo;
import org.california.monopolserver.utils.annotations.JSONSubTypes;
import org.california.monopolserver.model.ws_message.request.RequestMessage;
import org.california.monopolserver.model.ws_message.request.transaction.action.TransactionActionRequest;
import org.california.monopolserver.model.ws_message.request.transaction.init.TransactionInitRequest;

@JSONTypeInfo(value = "transaction_request_type")
@JSONSubTypes({
        @JSONSubTypes.Type(value = TransactionInitRequest.class, name = "init"),
        @JSONSubTypes.Type(value = TransactionActionRequest.class, name = "action")
})
public abstract class TransactionRequest extends RequestMessage {

}
