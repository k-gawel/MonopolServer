package org.california.monopolserver.instance.executable.move;

import org.california.monopolserver.instance.board.board.Board;
import org.california.monopolserver.instance.board.field.Field;
import org.california.monopolserver.instance.player.Player;

import java.util.Random;

public class Move {

    public Field destination;
    public Player player;
    public boolean crossedStart;

    private Move(Player player) {
        this.player = player;
    }


    public static Move makeMove(Player player) {
        Move result = new Move(player);

        Board board = player.getGame().board;
        Field start = board.get(player);

        int diceRoll = new Random().nextInt(12) + 1;
        result.destination = board.movePlayer(player, diceRoll);

        result.crossedStart = board.isStartBetween(start, result.destination);

        return result;
    }


    public static Move makeMove(Player player, int destination) {
        Move result = new Move(player);

        Board board = player.getGame().board;
        Field start = board.get(player);
        result.destination = board.get(destination);

        start.removePlayer(player);
        board.putPlayer(player, result.destination);

        result.crossedStart = board.isStartBetween(start, result.destination);

        return result;
    }

}
