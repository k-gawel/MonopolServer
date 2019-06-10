package org.california.monopolserver.instance.executable.move;

import org.california.monopolserver.instance.board.board.Board;
import org.california.monopolserver.instance.board.field.Field;
import org.california.monopolserver.instance.executable.Executable;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.utils.AbstractGameInstance;

import java.util.Random;

public class Move extends AbstractGameInstance implements Executable {

    public Field start;
    public Field destination;
    public Player player;
    public boolean crossedStart;


    public Move(Player player, int destination) {
        Board board = player.getBoard();

        this.player = player;
        this.start = player.field();
        this.destination = board.get(destination);

        this.crossedStart = board.isStartBetween(player.field(), this.destination);

        System.out.println(player.toString() + " GOUES FROM " + this.start + " TO " + this.destination);
    }


    public static Move rollTheDice(Player player) {
        int distance = new Random().nextInt(11) + 1;

        int currentIndex = player.field().number;
        int maxIndex = player.getBoard().size();

        int distanceToStart = maxIndex - currentIndex;

        int destination = distanceToStart >= distance ?
                currentIndex + distance : distance - distanceToStart;

        return new Move(player, destination);
    }


    @Override
    public String getUUID() {
        return null;
    }

}
