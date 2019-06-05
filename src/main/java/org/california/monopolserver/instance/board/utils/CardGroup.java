package org.california.monopolserver.instance.board.utils;

import org.california.monopolserver.instance.utils.AbstractGameInstance;
import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.model.interfaces.Landable;
import org.california.monopolserver.model.pattern.landable.card.CardGroupPattern;
import org.california.monopolserver.model.pattern.landable.card.Surprisable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardGroup extends AbstractGameInstance implements Landable {

    public CardGroupPattern pattern;


    public CardGroup(Game game, CardGroupPattern pattern) {
        super(game);
        this.pattern = pattern;
    }


    public Surprisable randomCard() {
        List<Surprisable> cards = new ArrayList<>(pattern.getComponents());
        int randomIndex = new Random().nextInt(cards.size());
        return cards.get(randomIndex);
    }


}
