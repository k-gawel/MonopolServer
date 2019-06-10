package org.california.monopolserver.service.transaction.offer.utils.offer;

import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.model.dto.transferable.TransactionOperations;
import org.california.monopolserver.model.interfaces.Transferable;
import org.california.monopolserver.service.utils.TransferableCollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionOfferService {

    private final PTransactionAddService addService;
    private final PTransactionRemoveService removeService;

    @Autowired
    public TransactionOfferService(PTransactionAddService addService, PTransactionRemoveService removeService) {
        this.addService = addService;
        this.removeService = removeService;
    }


    public boolean canAdd(Transaction transaction, Player side, Transferable transferable) {
        return addService.canAdd(transaction, side, transferable);
    }

    public Optional<Transferable> add(Transaction transaction, Player side, Transferable transferable) {
        return addService.add(transaction, side, transferable);
    }


    public boolean canRemove(Transaction transaction, Player side, Transferable transferable) {
        return removeService.canRemove(transaction, side, transferable);
    }

    public Optional<Transferable> remove(Transaction transaction, Player side, Transferable transferable) {
        return removeService.remove(transaction, side, transferable);
    }


    public Collection<TransactionOperations> createInfo(Transaction transaction) {
        return TransferableCollectionUtils.concatWithoutMoney(transaction.getInitiator().properties, transaction.getInvited().properties).stream()
                .map(t -> createInfo(transaction, t))
                .collect(Collectors.toSet());
    }


    public TransactionOperations createInfo(Transaction transaction, Transferable transferable) {
        boolean add    = canAdd(transaction, transferable);
        boolean remove = canRemove(transaction, transferable);

        return new TransactionOperations(transferable.getUUID(), add, remove);
    }


    private boolean canAdd(Transaction transaction, Transferable transferable) {
        return canAdd(transaction, transaction.getInitiator(), transferable)
            || canAdd(transaction, transaction.getInvited(), transferable);
    }


    private boolean canRemove(Transaction transaction, Transferable transferable) {
        return canRemove(transaction, transaction.getInitiator(), transferable)
            || canRemove(transaction, transaction.getInvited(), transferable);
    }



    static Method getMethod(Class<?> service, Class<? extends Transferable> type) {
        return Arrays.stream(service.getDeclaredMethods())
                .filter(TransactionOfferService::isProperMethod)
                .filter(m -> isSubclassOf(m.getParameterTypes()[2], type))
                .peek(m -> m.setAccessible(true))
                .findFirst()
                .orElse(null);
    }



    private static boolean isProperMethod(Method method) {
        try {
            return method.getParameters().length == 3
                && Arrays.stream(method.getParameterTypes()).noneMatch(Class::isInterface)
                && isSubclassOf(method.getParameterTypes()[0], Transaction.class)
                && isSubclassOf(method.getParameterTypes()[1], Player.class)
                && !Transferable.class.equals(method.getParameterTypes()[2])
                && method.getReturnType().equals(boolean.class);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isSubclassOf(Class<?> clazz, Class<?> superClass) {
        if (superClass.equals(Object.class))
            return true;

        if (clazz.equals(superClass))
            return true;
        else {
            clazz = clazz.getSuperclass();
            if (clazz.equals(Object.class))
                return false;

            return isSubclassOf(clazz, superClass);
        }
    }


}
