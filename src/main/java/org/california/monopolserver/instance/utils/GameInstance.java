package org.california.monopolserver.instance.utils;

import org.california.monopolserver.instance.board.board.Board;
import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.model.interfaces.Identifiable;

import java.util.Arrays;

public interface GameInstance extends Identifiable {

    Game getGame();

    static boolean sameGame(GameInstance... instances) {
        Game firstGame = instances[0].getGame();

        return Arrays.stream(instances).allMatch(g -> g.getGame().equals(firstGame));
    }

    default Money newMoney(int amount) {
        return new Money(getGame(), amount);
    }


    default Board getBoard() {
        return getGame().board;
    }

}
