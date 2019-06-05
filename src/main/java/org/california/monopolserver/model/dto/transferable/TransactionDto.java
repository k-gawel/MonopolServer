package org.california.monopolserver.model.dto.transferable;

import org.california.monopolserver.exceptions.GameException;
import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.utils.TransferableCollection;
import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.model.interfaces.Identifiable;
import org.california.monopolserver.model.ws_message.response.transaction.init.TransactionInitResponse;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransactionDto {

    public TransactionInitResponse init;

    public int initiator_money;
    public int invited_money;

    public Collection<String> offer;

    public TransactionDto(Transaction transaction, Collection<TransactionOperations> operations) throws GameException {
        this.init = TransactionInitResponse.createMessage(transaction, operations);

        TransferableCollection initiatorOffer = transaction.getOffer(transaction.getInitiator());
        TransferableCollection invitedOffer = transaction.getOffer(transaction.getInvited());

        this.initiator_money = initiatorOffer.getMoney().intValue();
        this.invited_money = initiatorOffer.getMoney().intValue();

        this.offer = serializeOffers(initiatorOffer, invitedOffer);
    }

    private Collection<String> serializeOffers(TransferableCollection offer1, TransferableCollection offer2) {
        return Stream.of(offer1, offer2)
                .flatMap(Collection::stream)
                .filter(t -> !(t instanceof Money))
                .map(Identifiable::getUUID)
                .collect(Collectors.toSet());
    }

}
