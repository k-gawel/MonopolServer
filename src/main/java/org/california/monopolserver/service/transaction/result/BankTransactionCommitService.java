package org.california.monopolserver.service.transaction.result;

import org.california.monopolserver.instance.executable.transaction.BankTransaction;
import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.instance.utils.TransferableCollection;
import org.california.monopolserver.service.transaction.offer.utils.offer.TransactionOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankTransactionCommitService {


    private final TransactionOfferService offerService;

    @Autowired
    public BankTransactionCommitService(TransactionOfferService offerService) {
        this.offerService = offerService;
    }


    public Transaction getFinalTransaction(BankTransaction transaction) {
        boolean isAccepted = transaction.isAccepted();
        boolean canAfford  = addDifferenceToTransaction(transaction);

        return isAccepted && canAfford ?
                transaction : new BankTransaction(transaction.getInitiator());
    }


    //returns false if player can not afford transaction
    private boolean addDifferenceToTransaction(BankTransaction transaction) {
        TransferableCollection playerOffer = transaction.getInitiatorOffer();
        TransferableCollection bankOffer   = transaction.getInvitedOffer();


        int difference = playerOffer.getPrice().intValue() - bankOffer.getPrice().intValue();

        return addDifferenceToTransaction(transaction, difference);
    }


    private boolean addDifferenceToTransaction(BankTransaction transaction, int difference) {
        Money money = new Money(transaction.getGame(), Math.abs(difference));

        return difference > 0 ?
                addDifferenceToBankSide(transaction, money) :
                addDifferenceToPlayerSide(transaction, money);
    }


    private boolean addDifferenceToBankSide(BankTransaction transaction, Money money) {
        return offerService.add(transaction, transaction.getInvited(), money).isPresent();
    }

    private boolean addDifferenceToPlayerSide(BankTransaction transaction, Money money) {
        return offerService.add(transaction, transaction.getInitiator(), money).isPresent();
    }



}
