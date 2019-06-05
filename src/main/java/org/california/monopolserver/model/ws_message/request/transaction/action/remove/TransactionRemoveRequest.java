package org.california.monopolserver.model.ws_message.request.transaction.action.remove;

import org.california.monopolserver.utils.annotations.JSONTypeInfo;
import org.california.monopolserver.utils.annotations.JSONSubTypes;
import org.california.monopolserver.model.ws_message.request.transaction.action.TransactionActionRequest;

@JSONTypeInfo("transaction_remove_type")
@JSONSubTypes({
        @JSONSubTypes.Type(value = TransactionRemoveItemRequest.class, name = "item")
})
public abstract class TransactionRemoveRequest extends TransactionActionRequest {

}
