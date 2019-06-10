package org.california.monopolserver.service.action;

import org.california.monopolserver.exceptions.PlayerNotFoundException;
import org.california.monopolserver.instance.board.field.Field;
import org.california.monopolserver.instance.executable.Executable;
import org.california.monopolserver.instance.executable.move.Move;
import org.california.monopolserver.instance.executable.tour.Tour;
import org.california.monopolserver.instance.executable.transaction.BankGift;
import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.player.Session;
import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.model.ws_message.request.action.DiceRollRequest;
import org.california.monopolserver.model.ws_message.response.player.PlayerMoveResponse;
import org.california.monopolserver.service.CustomMessageTemplate;
import org.california.monopolserver.service.game.GameRegistry;
import org.california.monopolserver.service.transaction.init.TransactionInitService;
import org.california.monopolserver.service.transaction.result.TransactionCommitService;
import org.california.monopolserver.utils.annotations.RequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameActionService {

    private final CustomMessageTemplate messageTemplate;
    private final TransactionInitService initService;
    private final TransactionCommitService commitService;

    @Autowired
    public GameActionService(CustomMessageTemplate messageTemplate, TransactionInitService initService, TransactionCommitService commitService) {
        this.messageTemplate = messageTemplate;
        this.initService = initService;
        this.commitService = commitService;
    }



    @RequestProcessor(DiceRollRequest.class)
    public void diceRoll(DiceRollRequest message) throws PlayerNotFoundException {
        Session session = new Session(message.session);
        Player player   = GameRegistry.getPlayerByUuid(session.playerUuid);
        Game game       = player.getGame();

        Tour tour = game.currentTour;
        if(!player.equals(tour.player) || tour.dice_rolled || game.currentTransaction != null)
            return;

        tour.dice_rolled = true;
        execute(Move.rollTheDice(player));
    }



    private void execute(Executable executable) {
        if(executable instanceof Transaction)
            executeTransaction((Transaction) executable);
        else if(executable instanceof Move)
            executeMove((Move) executable);
    }


    private void executeTransaction(Transaction transaction) {
        if(transaction.isCompulsory() && transaction.getInitiator().isBank())
            commitService.commitTransaction(transaction);
        else if(transaction.isCompulsory() && !transaction.getInitiator().isBank())
            initService.createTransaction(transaction);
        else if(transaction.isBankGift())
            commitService.commitTransaction(transaction);
    }


    private void executeMove(Move move) {
        Player player = move.player;
        Field  destination = move.destination;

        Transaction transaction = move.crossedStart ? new BankGift(player, new Money(player.getGame(), 400)) : null;
        execute(transaction);

        execute(destination.land(player));

        messageTemplate.sendMessage(new PlayerMoveResponse(move));
    }



}
