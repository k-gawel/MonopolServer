package org.california.monopolserver.model.ws_message.request.transaction.action.add;

import org.california.monopolserver.utils.annotations.JSONTypeInfo;

@JSONTypeInfo
public class TransactionAddMoneyRequest extends TransactionAddRequest {

    public int amount;

}
