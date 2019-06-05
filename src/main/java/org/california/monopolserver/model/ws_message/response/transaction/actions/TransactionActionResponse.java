package org.california.monopolserver.model.ws_message.response.transaction.actions;

import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.model.dto.transferable.TransactionOperations;
import org.california.monopolserver.utils.annotations.JSONSubTypes;
import org.california.monopolserver.utils.annotations.JSONTypeInfo;
import org.california.monopolserver.model.ws_message.response.transaction.TransactionResponse;
import org.california.monopolserver.model.ws_message.response.transaction.actions.item.TransactionItemResponse;
import org.california.monopolserver.model.ws_message.response.transaction.actions.status.TransactionStatusResponse;

import java.util.Collection;

@JSONTypeInfo("transaction_action_type")
@JSONSubTypes({
        @JSONSubTypes.Type(value = TransactionStatusResponse.class, name = "status"),
        @JSONSubTypes.Type(value = TransactionItemResponse.class, name = "item")
})
public abstract class TransactionActionResponse extends TransactionResponse {

    final public String side;
    public Collection<TransactionOperations> operations;

    public TransactionActionResponse(Transaction transaction, Player side, Collection<TransactionOperations> operations) {
        super(transaction);
        this.operations = operations;
        this.side = side.getUUID();
    }

}
