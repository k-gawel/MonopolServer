package org.california.monopolserver.model.ws_message.response.transaction.actions.status;

import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.model.dto.transferable.TransactionOperations;
import org.california.monopolserver.utils.annotations.JSONTypeInfo;
import org.california.monopolserver.model.ws_message.response.transaction.actions.TransactionActionResponse;

import java.util.Collection;

@JSONTypeInfo
public class TransactionStatusResponse extends TransactionActionResponse {

    final public boolean status;

    public TransactionStatusResponse(Transaction transaction, Player side, boolean status, Collection<TransactionOperations> operations) {
        super(transaction, side, operations);
        this.status = status;
    }

}
