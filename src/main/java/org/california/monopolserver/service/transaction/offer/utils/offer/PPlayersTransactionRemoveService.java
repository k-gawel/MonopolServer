package org.california.monopolserver.service.transaction.offer.utils.offer;

import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.transferable.discount.Discount;
import org.california.monopolserver.instance.transferable.town.Improvement;
import org.california.monopolserver.instance.transferable.town.Town;
import org.california.monopolserver.instance.transferable.utility.Utility;
import org.california.monopolserver.model.interfaces.Transferable;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Service
class PPlayersTransactionRemoveService  {


    boolean canRemove(Transaction transaction, Player side, Transferable transferable) {
        Class<? extends Transferable> tClass = transferable.getClass();
        Method method = TransactionOfferService.getMethod(getClass(), tClass);

        try {
            return (boolean) method.invoke(this, transaction, side, tClass.cast(transferable));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException | InvocationTargetException e) {
            return true;
        }
    }


    boolean canRemove(Transaction transaction, Player side, Town town) {
        return transaction.getOffer(transaction.getOppositeSide(side)).getDiscount(town).isEmpty();
    }


    boolean canRemove(Transaction transaction, Player side, Improvement improvement) {
        return true;
    }


    boolean canRemove(Transaction transaction, Player side, Utility utility) {
        return true;
    }


    boolean canRemove(Transaction transaction, Player side, Discount discount) {
        return true;
    }

}
