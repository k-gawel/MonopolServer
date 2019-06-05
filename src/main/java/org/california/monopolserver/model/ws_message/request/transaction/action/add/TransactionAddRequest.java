package org.california.monopolserver.model.ws_message.request.transaction.action.add;

import org.california.monopolserver.utils.annotations.JSONSubTypes;
import org.california.monopolserver.utils.annotations.JSONTypeInfo;
import org.california.monopolserver.model.ws_message.request.transaction.action.TransactionActionRequest;

@JSONTypeInfo("transaction_add_type")
@JSONSubTypes({
        @JSONSubTypes.Type(value = TransactionAddExistingRequest.class, name = "existing"),
        @JSONSubTypes.Type(value = TransactionAddMoneyRequest.class, name = "money"),
        @JSONSubTypes.Type(value = TransactionAddNewDiscountRequest.class, name = "new_discount"),
        @JSONSubTypes.Type(value = TransactionAddNewImprovementRequest.class, name = "new_improvement")
})
public abstract class TransactionAddRequest extends TransactionActionRequest {


}
