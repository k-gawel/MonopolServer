package org.california.monopolserver.model.ws_message.response.transaction.actions.item;

import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.model.dto.transferable.TransactionOperations;
import org.california.monopolserver.utils.annotations.JSONSubTypes;
import org.california.monopolserver.utils.annotations.JSONTypeInfo;
import org.california.monopolserver.model.ws_message.response.transaction.actions.TransactionActionResponse;

import java.util.Collection;

@JSONTypeInfo("transaction_item_action")
@JSONSubTypes({
        @JSONSubTypes.Type(value = TransactionAddResponse.class, name = "add"),
        @JSONSubTypes.Type(value = TransactionRemoveResponse.class, name = "remove")
})
public abstract class TransactionItemResponse extends TransactionActionResponse {


    TransactionItemResponse(Transaction transaction, Player side, Collection<TransactionOperations> operations ) {
        super(transaction, side, operations);
    }

}
