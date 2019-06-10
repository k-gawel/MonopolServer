package org.california.monopolserver.instance.game;

import org.california.monopolserver.instance.board.board.BoardBuilder;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.transferable.money.Money;
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


    public static void addPlayer(Player player) {
        Game game = player.getGame();

        if(game.players.isEmpty())
            game.admin = player;
        game.players.add(player);

        Money startMoney = new Money(player, game.startMoney);
        player.properties.add(startMoney);

        game.board.putPlayer(player);
    }

}
