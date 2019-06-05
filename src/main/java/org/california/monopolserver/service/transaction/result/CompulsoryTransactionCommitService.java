package org.california.monopolserver.service.transaction.result;

import org.california.monopolserver.instance.executable.transaction.CompulsoryTransaction;
import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.executable.transaction.VoluntaryTransaction;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.model.interfaces.Transferable;
import org.california.monopolserver.service.transaction.offer.utils.offer.TransactionOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CompulsoryTransactionCommitService {


    private final TransactionOfferService offerService;


    @Autowired
    public CompulsoryTransactionCommitService(TransactionOfferService offerService) {
        this.offerService = offerService;
    }


    public Transaction getFinalTransaction(CompulsoryTransaction transaction)  {

        return transaction.isAccepted() != null && transaction.isAccepted() ?
                transaction : getForcedTransaction(transaction);
    }


    private Transaction getForcedTransaction(CompulsoryTransaction transaction)  {
        Transaction forcedTransaction = new VoluntaryTransaction(transaction);
        Player target = transaction.getInvited();

        int demand = transaction.getDemand().intValue();

        Transferable transferable = getMoneyPlayerCanPay(demand, target);

        do {
            forcedTransaction.add(target, transferable);
            demand = demand - transferable.getBasicPrice().intValue();
            transferable = getCheapestTransferable(forcedTransaction, target).orElse(null);
        } while (demand > 0 && transferable != null);

        return forcedTransaction;
    }



    private Money getMoneyPlayerCanPay(int money, Player player) {
        int amount = player.properties.getMoney().intValue() >= money ?
                money : player.properties.getMoney().intValue();

        return new Money(player.getGame(), amount);
    }


    private Optional<Transferable> getCheapestTransferable(Transaction transaction, Player player) {
        return player.properties.stream()
                .filter(t -> !(t instanceof Money))
                .filter(t -> t.getBasicPrice().intValue() > 0)
                .filter(t -> offerService.canAdd(transaction, player, t))
                .sorted()
                .findFirst();
    }


}
