package org.california.monopolserver.instance.board.field;

import org.california.monopolserver.instance.board.utils.CardGroup;

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




}
