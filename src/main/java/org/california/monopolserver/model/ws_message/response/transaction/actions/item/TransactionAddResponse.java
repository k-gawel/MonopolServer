package org.california.monopolserver.model.ws_message.response.transaction.actions.item;


import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.model.dto.transferable.TransactionOperations;
import org.california.monopolserver.model.dto.transferable.TransferableDto;
import org.california.monopolserver.model.interfaces.Transferable;

import java.util.Collection;

public class TransactionAddResponse extends TransactionItemResponse {

    public final TransferableDto transferable;

    public TransactionAddResponse(Transaction transaction, Player side, Transferable transferable, Collection<TransactionOperations> operations) {
        super(transaction, side, operations);
        this.transferable = TransferableDto.create(transferable);
    }
}
