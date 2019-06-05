package org.california.monopolserver.service.transaction.offer.service;

import org.california.monopolserver.exceptions.GameException;
import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.model.dto.transferable.TransactionOperations;
import org.california.monopolserver.model.interfaces.Transferable;
import org.california.monopolserver.model.ws_message.request.transaction.action.remove.TransactionRemoveItemRequest;
import org.california.monopolserver.model.ws_message.response.transaction.actions.item.TransactionRemoveResponse;
import org.california.monopolserver.service.CustomMessageTemplate;
import org.california.monopolserver.utils.annotations.RequestProcessor;
import org.california.monopolserver.service.game.GameRegistry;
import org.california.monopolserver.service.transaction.offer.utils.offer.TransactionOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TransactionRemoveService {

    private final CustomMessageTemplate   messageTemplate;
    private final TransactionOfferService offerService;

    @Autowired
    public TransactionRemoveService(CustomMessageTemplate messageTemplate, TransactionOfferService offerService) {
        this.messageTemplate = messageTemplate;
        this.offerService = offerService;
    }


    @RequestProcessor(TransactionRemoveItemRequest.class)
    public void remove(TransactionRemoveItemRequest request) throws GameException {
        Transaction transaction = GameRegistry.getTransaction(request.transaction);
        Player side = GameRegistry.getPlayerByUuid(request.side);

        transaction.getOffer(side).get(request.transferable)
                .ifPresent(t -> remove(transaction, side, t));
    }


    private void remove(Transaction transaction, Player side, Transferable transferable) {
        offerService.remove(transaction, side, transferable)
                .ifPresent(t -> sendMessage(transaction, side, t));
    }


    private void sendMessage(Transaction transaction, Player side, Transferable transferable) {
        Collection<TransactionOperations> operations = offerService.createInfo(transaction);

        TransactionRemoveResponse response = new TransactionRemoveResponse(transaction, side, transferable, operations);

        messageTemplate.sendMessage(response);
    }


}
