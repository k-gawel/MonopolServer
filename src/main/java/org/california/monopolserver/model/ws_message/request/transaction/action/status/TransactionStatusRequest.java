package org.california.monopolserver.model.ws_message.request.transaction.action.status;

import org.california.monopolserver.utils.annotations.JSONTypeInfo;
import org.california.monopolserver.model.ws_message.request.transaction.action.TransactionActionRequest;

@JSONTypeInfo
public class TransactionStatusRequest extends TransactionActionRequest {

    public boolean status;

}
