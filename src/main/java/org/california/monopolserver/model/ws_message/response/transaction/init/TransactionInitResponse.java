package org.california.monopolserver.model.ws_message.response.transaction.init;

import org.california.monopolserver.exceptions.WrongTypeException;
import org.california.monopolserver.instance.executable.transaction.BankTransaction;
import org.california.monopolserver.instance.executable.transaction.CompulsoryTransaction;
import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.executable.transaction.VoluntaryTransaction;
import org.california.monopolserver.model.dto.transferable.TransactionOperations;
import org.california.monopolserver.utils.annotations.JSONSubTypes;
import org.california.monopolserver.utils.annotations.JSONTypeInfo;
import org.california.monopolserver.model.ws_message.response.transaction.TransactionResponse;

import java.util.Collection;


@JSONTypeInfo("transaction_init_type")
@JSONSubTypes({
        @JSONSubTypes.Type(value = BankTransactionInitResponse.class, name = "bank"),
        @JSONSubTypes.Type(value = CompulsoryTransactionInitResponse.class, name = "compulsory"),
        @JSONSubTypes.Type(value = VoluntaryTransactionInitResponse.class, name = "voluntary")
})
public abstract class TransactionInitResponse extends TransactionResponse {

    public String initiator;
    public String invited;

    public Collection<TransactionOperations> operations;

    protected TransactionInitResponse(Transaction transaction, Collection<TransactionOperations> operations) {
        super(transaction);
        this.operations = operations;
        this.initiator  = transaction.getInitiator().getUUID();
        this.invited    = transaction.getInvited().getUUID();
    }

    public static TransactionInitResponse createMessage(Transaction transaction, Collection<TransactionOperations> operations) throws WrongTypeException {
        TransactionInitResponse result;

        if(transaction instanceof CompulsoryTransaction)
            result = new CompulsoryTransactionInitResponse((CompulsoryTransaction) transaction, operations);
        else if(transaction instanceof VoluntaryTransaction)
            result = new VoluntaryTransactionInitResponse((VoluntaryTransaction) transaction, operations);
        else if (transaction instanceof BankTransaction)
            result = new BankTransactionInitResponse((BankTransaction) transaction, operations);
        else
            throw new IllegalArgumentException("Wrong transaction type");

        return result;
    }

}
