package org.california.monopolserver.service.transaction.offer.utils.offer;


import org.california.monopolserver.instance.executable.transaction.BankTransaction;
import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.instance.utils.GameInstance;
import org.california.monopolserver.model.interfaces.Transferable;
import org.california.monopolserver.service.utils.TransactionOfferUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class PTransactionAddService {

    private final PBankTransactionAddService btAddService;
    private final PPlayersTransactionAddService ptAddService;

    @Autowired
    PTransactionAddService(PBankTransactionAddService btAddService, PPlayersTransactionAddService ptAddService) {
        this.btAddService = btAddService;
        this.ptAddService = ptAddService;
    }


    public Optional<Transferable> add(Transaction transaction, Player side, Transferable transferable) {
        if(!canAdd(transaction, side, transferable))
            return Optional.empty();

        boolean result = transferable instanceof Money ?
                transaction.getOffer(side).setMoney((Money) transferable) : transaction.getOffer(side).add(transferable);


        return result ? Optional.of(transferable) : Optional.empty();
    }


    public boolean canAdd(Transaction transaction, Player side, Transferable transferable)  {
//        boolean a = GameInstance.sameGame(transaction, side, transferable);
//        boolean b = side.properties.contains(transferable);
//        boolean c = !TransactionOfferUtils.bothOffersWithoutMoney(transaction).contains(transferable);
//        boolean d = (transaction instanceof BankTransaction ?
//                btAddService.canAdd((BankTransaction) transaction, side, transferable) :
//                ptAddService.canAdd(transaction, side, transferable));
//
//        System.out.println(a + ", " + b + ", " + c + ", " + d);
//
//        return a && b && c && d;
        return GameInstance.sameGame(transaction, side, transferable)
                && side.properties.contains(transferable)
                && !TransactionOfferUtils.bothOffersWithoutMoney(transaction).contains(transferable)
                && (transaction instanceof BankTransaction ?
                btAddService.canAdd((BankTransaction) transaction, side, transferable) :
                ptAddService.canAdd(transaction, side, transferable));
    }


}
