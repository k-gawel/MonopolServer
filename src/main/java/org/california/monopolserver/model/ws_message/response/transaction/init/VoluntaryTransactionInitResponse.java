package org.california.monopolserver.model.ws_message.response.transaction.init;

import org.california.monopolserver.instance.executable.transaction.VoluntaryTransaction;
import org.california.monopolserver.model.dto.transferable.TransactionOperations;
import org.california.monopolserver.utils.annotations.JSONTypeInfo;

import java.util.Collection;

@JSONTypeInfo("voluntary")
public class VoluntaryTransactionInitResponse extends TransactionInitResponse {

    VoluntaryTransactionInitResponse(VoluntaryTransaction transaction, Collection<TransactionOperations> operations) {
        super(transaction, operations);
    }

}
