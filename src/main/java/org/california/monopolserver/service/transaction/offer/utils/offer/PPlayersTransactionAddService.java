package org.california.monopolserver.service.transaction.offer.utils.offer;

import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.transferable.discount.Discount;
import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.instance.transferable.town.Town;
import org.california.monopolserver.model.interfaces.Transferable;
import org.california.monopolserver.service.utils.TransactionOfferUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Service
class PPlayersTransactionAddService {


    boolean canAdd(Transaction transaction, Player side, Transferable transferable) {
        Class<? extends Transferable> tClass = transferable.getClass();
        Method method = TransactionOfferService.getMethod(getClass(), tClass);

        try {
            return (boolean) method.invoke(this, transaction, side, tClass.cast(transferable));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            return true;
        }
    }


    private boolean canAdd(Transaction transaction, Player side, Town town) {
        return TransactionOfferUtils.regionImprovementsAfter(transaction, town.getGroup()).isEmpty()
            && transaction.getOffer(side).getDiscount(town).isEmpty();
    }

    private boolean canAdd(Transaction transaction, Player side, Money money) {
        return side.properties.contains(money);
    }

    boolean canAdd(Transaction transaction, Player side, Discount discount) {
        return !transaction.getOffer(side).contains(discount.getChargeable());
    }


}
