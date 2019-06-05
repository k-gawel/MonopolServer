package org.california.monopolserver.service.transaction.offer.service;

import org.california.monopolserver.exceptions.PlayerNotFoundException;
import org.california.monopolserver.exceptions.TransactionNotFoundException;
import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.player.Session;
import org.california.monopolserver.model.dto.transferable.TransactionOperations;
import org.california.monopolserver.model.ws_message.request.transaction.action.status.TransactionStatusRequest;
import org.california.monopolserver.model.ws_message.response.transaction.actions.status.TransactionStatusResponse;
import org.california.monopolserver.service.CustomMessageTemplate;
import org.california.monopolserver.utils.annotations.RequestProcessor;
import org.california.monopolserver.service.game.GameRegistry;
import org.california.monopolserver.service.transaction.offer.utils.offer.TransactionOfferService;
import org.california.monopolserver.service.transaction.result.TransactionCommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TransactionStatusService {

    private final TransactionCommitService commitService;
    private final CustomMessageTemplate    messageTemplate;
    private final TransactionOfferService  offerService;

    @Autowired
    public TransactionStatusService(TransactionCommitService commitService, CustomMessageTemplate messageTemplate, TransactionOfferService offerService) {
        this.commitService = commitService;
        this.messageTemplate = messageTemplate;
        this.offerService = offerService;
    }


    @RequestProcessor(TransactionStatusRequest.class)
    public void set(TransactionStatusRequest request) throws IllegalAccessException, TransactionNotFoundException, PlayerNotFoundException {
        Transaction transaction = GameRegistry.getTransaction(request.transaction);
        Player side   = GameRegistry.getPlayerByUuid(request.side);
        Player player = GameRegistry.getPlayerByUuid(new Session(request.session).playerUuid);

        if(!side.equals(player)) {
            throw new IllegalAccessException("Player can only set his own status");
        }


        setStatus(transaction, side, request.status);
    }


    void setStatus(Transaction transaction, Player side, boolean status) {
        transaction.setStatus(side, status);

        if(transaction.isAccepted() != null)
            commitService.commitTransaction(transaction);

        sendMessage(transaction, side, status);
    }


    void sendMessage(Transaction transaction, Player side, boolean status) {
        Collection<TransactionOperations> operations = offerService.createInfo(transaction);
        TransactionStatusResponse response = new TransactionStatusResponse(transaction, side, status, operations);
        messageTemplate.sendMessage(response);
    }

}
