package org.california.monopolserver.service.transaction.offer.utils.offer;

import org.california.monopolserver.instance.executable.transaction.BankTransaction;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.instance.transferable.town.Town;
import org.california.monopolserver.model.interfaces.Transferable;
import org.california.monopolserver.service.utils.TransactionOfferUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Service
class PBankTransactionRemoveService {


    boolean canRemove(BankTransaction transaction, Player side, Transferable transferable) {
        Method method = TransactionOfferService.getMethod(getClass(), transferable.getClass());

        try {
            return (boolean) method.invoke(this, transaction, side, transferable);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            return true;
        }
    }

    private boolean canRemove(BankTransaction transaction, Player side, Town town) {
        return transaction.getInitiator().equals(side) || TransactionOfferUtils.regionImprovementsAfter(transaction, town.getGroup()).isEmpty();
    }


    private boolean canRemove(BankTransaction transaction, Player side, Money money) {
        return false;
    }


}
