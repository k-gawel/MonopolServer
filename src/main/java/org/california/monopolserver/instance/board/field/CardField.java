package org.california.monopolserver.instance.board.field;

import org.california.monopolserver.exceptions.WrongTypeException;
import org.california.monopolserver.instance.board.utils.CardGroup;
import org.california.monopolserver.instance.executable.Executable;
import org.california.monopolserver.instance.executable.move.Move;
import org.california.monopolserver.instance.executable.transaction.BankGift;
import org.california.monopolserver.instance.executable.transaction.CompulsoryTransaction;
import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.model.pattern.landable.card.ChargeCard;
import org.california.monopolserver.model.pattern.landable.card.GiftCard;
import org.california.monopolserver.model.pattern.landable.card.MoveCard;
import org.california.monopolserver.model.pattern.landable.card.Surprisable;

public class CardField extends Field {

    private CardGroup cardGroup;

    public CardField(CardGroup cardGroup, int number) {
        super(cardGroup.getGame(), number);
        this.cardGroup = cardGroup;
    }


    @Override
    public CardGroup getLandable() {
        return cardGroup;
    }


    public Executable land(Player player) {
        player.getBoard().putPlayer(player, this);
        System.out.println(player + " LANDS ON " + this.number + " CURRENT PLAYER POSITION IS " + player.field());

        Surprisable surprisable = cardGroup.randomCard();

        if(surprisable instanceof ChargeCard)
            return land(player, (ChargeCard) surprisable);
        if(surprisable instanceof GiftCard)
            return land(player, (GiftCard) surprisable);
        if(surprisable instanceof MoveCard)
            return land(player, (MoveCard) surprisable);
        else
            throw new WrongTypeException(Surprisable.class, surprisable);

    }


    public Transaction land(Player player, ChargeCard card) {
        Money charge = new Money(player.getGame(), card.getProperty());
        Transaction transaction = new CompulsoryTransaction(player.getGame().bank, player, charge);
        return transaction;
    }


    public Transaction land(Player player, GiftCard card) {
        Money gift   = new Money(player.getGame(), card.getProperty());
        Transaction transaction = new BankGift(player, gift);
        return transaction;
    }


    public Move land(Player player, MoveCard card) {
        return new Move(player, card.getProperty());
    }


}
