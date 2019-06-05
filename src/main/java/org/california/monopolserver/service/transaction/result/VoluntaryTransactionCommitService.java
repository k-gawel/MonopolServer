package org.california.monopolserver.service.transaction.result;

import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.executable.transaction.VoluntaryTransaction;
import org.springframework.stereotype.Service;

@Service
public class VoluntaryTransactionCommitService {


    public Transaction getFinalTransaction(VoluntaryTransaction transaction) {
        return transaction.isAccepted() ?
                transaction : new VoluntaryTransaction(transaction);
    }


}
