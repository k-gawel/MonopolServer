package org.california.monopolserver.instance.game;

import org.california.monopolserver.instance.board.board.BoardBuilder;
import org.california.monopolserver.service.game.GameRegistry;
import org.california.monopolserver.instance.player.Bank;

public class GameBuilder {

    public static Game create(BoardBuilder.BoardType boardType) {
        Game game = new Game();
        game.bank = new Bank(game);

        BoardBuilder boardBuilder = new BoardBuilder(game, boardType);
        game.board = boardBuilder.build();



        GameRegistry.add(game);
        return game;
    }

}
