package org.california.monopolserver.model.ws_message.request.transaction.action.remove;

import org.california.monopolserver.utils.annotations.JSONTypeInfo;

@JSONTypeInfo
public class TransactionRemoveItemRequest extends TransactionRemoveRequest {

    public String transferable;

}
