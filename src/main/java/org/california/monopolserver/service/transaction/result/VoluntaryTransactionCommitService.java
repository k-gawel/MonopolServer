package org.california.monopolserver.service.transaction.result;

import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.executable.transaction.VoluntaryTransaction;
import org.springframework.stereotype.Service;

@Service
public class VoluntaryTransactionCommitService {


    public Transaction getFinalTransaction(VoluntaryTransaction transaction) {
        Boolean accepted = transaction.isAccepted();

        return accepted == null || accepted ?
                transaction : new VoluntaryTransaction(transaction);
    }


}
