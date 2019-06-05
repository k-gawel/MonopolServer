package org.california.monopolserver.model.ws_message.request;

import org.california.monopolserver.utils.annotations.JSONTypeInfo;
import org.california.monopolserver.utils.annotations.JSONSubTypes;
import org.california.monopolserver.model.ws_message.request.action.GameActionRequest;
import org.california.monopolserver.model.ws_message.request.transaction.TransactionRequest;
import org.california.monopolserver.model.ws_message.request.action.ChatMessageRequest;

@JSONTypeInfo(value = "request_message_type")
@JSONSubTypes({
        @JSONSubTypes.Type(value = TransactionRequest.class, name = "transaction"),
        @JSONSubTypes.Type(value = GameActionRequest.class, name = "action"),
        @JSONSubTypes.Type(value = ChatMessageRequest.class, name = "chat")
})
public abstract class RequestMessage {

    public String session;
    public String game;

}
