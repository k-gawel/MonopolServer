package org.california.monopolserver.service.transaction.offer.utils.offer;

import org.california.monopolserver.instance.executable.transaction.BankTransaction;
import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.utils.GameInstance;
import org.california.monopolserver.model.interfaces.Transferable;
import org.california.monopolserver.service.utils.TransactionOfferUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class PTransactionRemoveService {


    private final PBankTransactionRemoveService btService;
    private final PPlayersTransactionRemoveService ptService;

    @Autowired
    public PTransactionRemoveService(PBankTransactionRemoveService btService, PPlayersTransactionRemoveService ptService) {
        this.btService = btService;
        this.ptService = ptService;
    }


    boolean canRemove(Transaction transaction, Player side, Transferable transferable) {
        return GameInstance.sameGame(transaction, side, transferable)
            && TransactionOfferUtils.bothOffersWithoutMoney(transaction).contains(transferable)
            && (transaction instanceof BankTransaction ?
                btService.canRemove((BankTransaction) transaction, side, transferable) :
                ptService.canRemove(transaction, side, transferable));
    }


    Optional<Transferable> remove(Transaction transaction, Player side, Transferable transferable) {
        return canRemove(transaction, side, transferable) && transaction.getOffer(side).remove(transferable) ?
                Optional.of(transferable) : Optional.empty();
    }

}
