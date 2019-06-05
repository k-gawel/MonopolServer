package org.california.monopolserver.service.action;

import org.california.monopolserver.exceptions.PlayerNotFoundException;
import org.california.monopolserver.instance.board.field.CardField;
import org.california.monopolserver.instance.board.field.ChargeableField;
import org.california.monopolserver.instance.board.field.Field;
import org.california.monopolserver.instance.board.utils.CardGroup;
import org.california.monopolserver.instance.executable.move.Move;
import org.california.monopolserver.instance.executable.tour.Tour;
import org.california.monopolserver.instance.executable.transaction.BankGift;
import org.california.monopolserver.instance.executable.transaction.CompulsoryTransaction;
import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.player.Session;
import org.california.monopolserver.instance.transferable.discount.Discount;
import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.model.interfaces.Chargeable;
import org.california.monopolserver.model.pattern.landable.card.ChargeCard;
import org.california.monopolserver.model.pattern.landable.card.GiftCard;
import org.california.monopolserver.model.pattern.landable.card.MoveCard;
import org.california.monopolserver.model.pattern.landable.card.Surprisable;
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
        Player player = GameRegistry.getPlayerByUuid(session.playerUuid);
        Game game = player.getGame();

        Tour tour = game.currentTour;
        if(!player.equals(tour.player) || tour.dice_rolled || game.currentTransaction != null)
            return;

        Move move = Move.makeMove(player);
        tour.dice_rolled = true;

        makeMove(move);
    }


    private void makeMove(Move move) {
        PlayerMoveResponse response = new PlayerMoveResponse(move);
        messageTemplate.sendMessage(response);

        land(move);

        if(move.crossedStart)
            commitService.sendGift(move.player, new Money(move.player.getGame(), 400));
    }


    private void land(Move move) {
        Field field   = move.destination;
        Player player = move.player;

        if(field instanceof ChargeableField)
            land(player, ((ChargeableField) field).getLandable());
        else
            land(player, ((CardField) field).getLandable());
    }


    private void land(Player player, Chargeable chargeable) {
        if (chargeable.getOwner().isBank() || chargeable.getOwner().equals(player))
            return;

        Money charge = player.properties.getDiscount(chargeable)
                .map(Discount::getCharge)
                .orElseGet(chargeable::getCharge);

        Transaction transaction = new CompulsoryTransaction(chargeable.getOwner(), player, charge);

        commitService.commitTransaction(transaction);
    }


    private void land(Player player, CardGroup cardGroup) {
        Surprisable card = cardGroup.randomCard();

        if(card instanceof MoveCard)
            land((MoveCard) card, player);
        else if(card instanceof ChargeCard)
            land((ChargeCard) card, player);
        else if(card instanceof GiftCard)
            land((GiftCard) card, player);
    }


    private void land(MoveCard card, Player player) {
        Move move = Move.makeMove(player, card.getProperty());
        makeMove(move);
    }


    private void land(ChargeCard card, Player player) {
        Money charge = new Money(player.getGame(), card.getProperty());
        Transaction transaction = new CompulsoryTransaction(player.getGame().bank, player, charge);
        commitService.commitTransaction(transaction);
    }


    private void land(GiftCard card, Player player) {
        Money money = new Money(player.getGame(), card.getProperty());
        BankGift gift = new BankGift(player, money);
        this.commitService.commitTransaction(gift);
    }

}
