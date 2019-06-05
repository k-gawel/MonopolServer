package org.california.monopolserver.model.ws_message.response.transaction.init;

import org.california.monopolserver.instance.executable.transaction.BankTransaction;
import org.california.monopolserver.model.dto.transferable.TransactionOperations;
import org.california.monopolserver.utils.annotations.JSONTypeInfo;

import java.util.Collection;

@JSONTypeInfo("bank")
public class BankTransactionInitResponse extends TransactionInitResponse {

    BankTransactionInitResponse(BankTransaction transaction, Collection<TransactionOperations> operations) {
        super(transaction, operations);
    }

}
