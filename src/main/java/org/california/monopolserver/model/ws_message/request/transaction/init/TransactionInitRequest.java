package org.california.monopolserver.model.ws_message.request.transaction.init;

import org.california.monopolserver.utils.annotations.JSONTypeInfo;
import org.california.monopolserver.model.ws_message.request.transaction.TransactionRequest;

@JSONTypeInfo
public class TransactionInitRequest extends TransactionRequest {

    public String initiator;
    public String invited;

}
