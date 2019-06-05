package org.california.monopolserver.model.ws_message.response.transaction.init;

import org.california.monopolserver.instance.executable.transaction.CompulsoryTransaction;
import org.california.monopolserver.model.dto.transferable.TransactionOperations;
import org.california.monopolserver.utils.annotations.JSONTypeInfo;

import java.util.Collection;

@JSONTypeInfo("compulsory")
public class CompulsoryTransactionInitResponse extends TransactionInitResponse {

    public int demand;

    CompulsoryTransactionInitResponse(CompulsoryTransaction transaction, Collection<TransactionOperations> operations) {
        super(transaction, operations);
        this.demand = transaction.getDemand().intValue();
    }

}
