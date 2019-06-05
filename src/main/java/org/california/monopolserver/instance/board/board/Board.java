package org.california.monopolserver.instance.board.board;

import org.california.monopolserver.instance.utils.GameInstance;
import org.california.monopolserver.instance.board.field.Field;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.model.interfaces.Identifiable;
import org.california.monopolserver.model.interfaces.Landable;

import java.util.ArrayList;
import java.util.UUID;

public class Board extends ArrayList<Field> implements Identifiable, GameInstance {

    private final Game game;
    String uuid = UUID.randomUUID().toString();


    Board(Game game) {
        this.game = game;
    }

    @Override
    public Field get(int index) {
        for(Field field : this)
            if(field.getNumber() == index)
                return field;

        throw new ArrayIndexOutOfBoundsException("There is no field on this boeard of number " + index);
    }

    public Field get(Player player) {
        for(Field field : this)
            if(field.getPlayers().contains(player))
                return field;
        return null;
    }

    public Field get(Landable landable) {
        return this.stream()
                .filter(f -> f.getLandable().equals(landable))
                .findFirst()
                .get();
    }


    public Field movePlayer(Player player, int distance) {
        Field startField = get(player);
        Field destinationField = goOf(startField, distance);

        removePlayer(player);
        putPlayer(player, destinationField);

        return destinationField;
    }


    public Field putPlayer(Player player) {
        return putPlayer(player, getStart());
    }


    public Field putPlayer(Player player, Field field) {
        field.getPlayers().add(player);
        return field;
    }


    public boolean removePlayer(Player player) {
        Field field = get(player);
        return field != null && field.removePlayer(player);
    }


    private Field getStart() {
        return get(0);
    }


    public boolean isStartBetween(Field start, Field end) {
        return start.getNumber() >= end.getNumber();
    }


    public Field goOf(Field start, int distance) {
        if(distance > size())
            throw new IllegalStateException("Board cannot be doubled");

        int distanceToStart = size() - start.getNumber();

        if(distanceToStart > distance)
            return get(start.getNumber() + distance);
        else
            return get(distance - distanceToStart);
    }


    @Override
    public int size() {
        int max = 0;

        for(Field field : this)
            max = field.getNumber() > max ? field.getNumber() : max;

        return max + 1;
    }


    @Override
    public String getUUID() {
        return this.uuid;
    }

    @Override
    public Game getGame() {
        return game;
    }

}
