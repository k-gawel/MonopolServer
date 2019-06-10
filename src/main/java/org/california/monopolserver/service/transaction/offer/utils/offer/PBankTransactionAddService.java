package org.california.monopolserver.service.transaction.offer.utils.offer;

import org.california.monopolserver.instance.executable.transaction.BankTransaction;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.transferable.town.Improvement;
import org.california.monopolserver.instance.transferable.town.Town;
import org.california.monopolserver.instance.transferable.town.TownRegion;
import org.california.monopolserver.instance.transferable.utility.Utility;
import org.california.monopolserver.instance.utils.TransferableCollection;
import org.california.monopolserver.model.interfaces.Transferable;
import org.california.monopolserver.service.utils.TransactionOfferUtils;
import org.california.monopolserver.service.utils.TransferableCollectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.california.monopolserver.service.utils.TransactionOfferUtils.regionImprovementsAfter;

@Service
class PBankTransactionAddService {


    boolean canAdd(BankTransaction transaction, Player side, Transferable transferable) {
        Class<? extends Transferable> tClass = transferable.getClass();
        Method method = TransactionOfferService.getMethod(getClass(), tClass);

        try {
            return (boolean) method.invoke(this, transaction, side, transferable);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            return true;
        }
    }


    private boolean canAdd(BankTransaction transaction, Player side, Town town) {
        return transaction.getInitiator().equals(side) ?
             regionImprovementsAfter(transaction, town.getGroup()).isEmpty() : town.standsOn(transaction.getInitiator());
    }


    private boolean canAdd(BankTransaction transaction, Player side, Improvement improvement) {
        return transaction.getInitiator().equals(side) ?
                canSell(transaction, improvement) : canBuy(transaction, improvement);
    }

    private boolean canBuy(BankTransaction transaction, Improvement improvement) {
        Town       town   = improvement.getGroup();
        TownRegion region = town.getGroup();
        Player     player = transaction.getInitiator();

        return TransactionOfferUtils.sideWillOwnRegion(transaction, player, region)                             // player owns region or will be after transaction
            && TransactionOfferUtils.townImprovementsAfter(transaction, town).size() < 5                        // max capacity not reached
            && TransferableCollectionUtils.getTownImprovements(transaction.getOffer(player), town).isEmpty();   // no towns improvements on sell
    }

    private boolean canSell(BankTransaction transaction, Improvement improvement) {
        Town                   town      = improvement.getGroup();
        TransferableCollection bankOffer = transaction.getInvitedOffer();

        return TransferableCollectionUtils.getTownImprovements(bankOffer, town).isEmpty();     // no town improvements on buy
    }


    private boolean canAdd(BankTransaction transaction, Player side, Utility utility) {
        return transaction.getInitiator().equals(side) ?
                true : utility.standsOn(transaction.getInitiator());
    }

}
