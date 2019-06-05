package org.california.monopolserver.service.transaction.init;

import org.california.monopolserver.exceptions.GameException;
import org.california.monopolserver.exceptions.TourException;
import org.california.monopolserver.instance.executable.transaction.BankTransaction;
import org.california.monopolserver.instance.executable.transaction.CompulsoryTransaction;
import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.executable.transaction.VoluntaryTransaction;
import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.model.dto.transferable.TransactionOperations;
import org.california.monopolserver.model.ws_message.request.transaction.init.TransactionInitRequest;
import org.california.monopolserver.model.ws_message.response.ResponseMessage;
import org.california.monopolserver.model.ws_message.response.transaction.init.TransactionInitResponse;
import org.california.monopolserver.service.CustomMessageTemplate;
import org.california.monopolserver.utils.annotations.RequestProcessor;
import org.california.monopolserver.service.game.GameRegistry;
import org.california.monopolserver.service.transaction.offer.utils.offer.TransactionOfferService;
import org.california.monopolserver.service.transaction.result.TransactionCommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TransactionInitService {

    private final CustomMessageTemplate messageTemplate;
    private final TransactionCommitService commitService;
    private final TransactionOfferService offerService;

    @Autowired
    public TransactionInitService(CustomMessageTemplate messageTemplate, TransactionCommitService commitService, TransactionOfferService offerService) {
        this.messageTemplate = messageTemplate;
        this.commitService = commitService;
        this.offerService = offerService;
    }


    @RequestProcessor(TransactionInitRequest.class)
    public void run(TransactionInitRequest request) throws GameException {
        Player initiator = GameRegistry.getPlayerByUuid(request.initiator);
        Player invited   = GameRegistry.getPlayerByUuid(request.invited);

        Transaction transaction = invited.isBank() ?
                new BankTransaction(initiator) : new VoluntaryTransaction(initiator, invited);

        createTransaction(transaction);
    }


    public void createTransaction(Transaction transaction) {
        setGameTransaction(transaction);
        sendMessage(transaction);
    }


    public void createTransaction(Player executor, Player target, Money demand) throws GameException {
        CompulsoryTransaction transaction = new CompulsoryTransaction(executor, target, demand);
        if(!executor.isBank()) {
            setGameTransaction(transaction);
            sendMessage(transaction);
        }
        else
            commitService.commitTransaction(transaction);
    }


    private void setGameTransaction(Transaction transaction) {
        Game game = transaction.getGame();

        if(!available(transaction))
            throw new TourException(transaction.getInitiator());

        if(game.currentTransaction != null)
            throw new IllegalStateException("Another transaction in progress. Game: " + game + " Transaction: " + game.currentTransaction);

        game.currentTransaction = transaction;
    }


    private boolean available(Transaction transaction) {
        Player tourPlayer = transaction.getGame().currentTour.player;
        Player initiator  = transaction.getInitiator();
        Player invited    = transaction.getInvited();

        return transaction instanceof CompulsoryTransaction ?
                initiator.equals(tourPlayer) || invited.equals(tourPlayer) :
                initiator.equals(tourPlayer);
    }


    private void sendMessage(Transaction transaction) {
        Collection<TransactionOperations> operations = offerService.createInfo(transaction);
        ResponseMessage message = TransactionInitResponse.createMessage(transaction, operations);
        messageTemplate.sendMessage(message);
    }


}
