package org.california.monopolserver.instance.game;

import org.california.monopolserver.instance.board.board.Board;
import org.california.monopolserver.instance.executable.tour.Tour;
import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.player.Bank;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.utils.GameInstance;
import org.california.monopolserver.instance.utils.Instance;
import org.california.monopolserver.model.interfaces.Transferable;

import java.util.*;
import java.util.stream.Stream;

public class Game extends Instance implements GameInstance {

    private static int currentIndex = 0;

    public final int number;
    public List<Player> players = new ArrayList<>();
    public Bank bank;
    public Board board;
    public int startMoney = 1500;
    public Transaction currentTransaction;
    public Player admin;

    public Tour currentTour;
    public List<Tour> previousTours = new ArrayList<>();


    public Game() {
        this.number = currentIndex++;
    }

    public Collection<Player> getPlayersWithBank() {
        Collection<Player> result = new HashSet<>(players);
        result.add(bank);
        return result;
    }

    public boolean maxPlayers() {
        return this.players.size() > 6;
    }


    private Stream<Transferable> getAllTransferablesStream() {
        return getPlayersWithBank().stream()
                .flatMap(p -> p.properties.stream());
    }


    public Optional<Transferable> getTransferable(String uuid) {
        return getAllTransferablesStream()
                .filter(t -> t.getUUID().equals(uuid))
                .findFirst();
    }


    public <T extends Transferable> Optional<T> getTransferable(String uuid, Class<T> type) {
        return getAllTransferablesStream()
                .filter(t -> t.getUUID().equals(uuid))
                .filter(type::isInstance)
                .map(type::cast)
                .findFirst();
    }


    public void setTransaction(Transaction transaction) throws IllegalStateException {
        this.currentTransaction = transaction;
    }


    public void removeTransaction() {
        this.currentTransaction = null;
    }


    @Override
    public Game getGame() {
        return this;
    }


    public int getNumber() {
        return this.number;
    }


    public void addPlayer(Player player) {
        if(maxPlayers())
            throw new IllegalStateException("Max 5 players in game");

        if(players.size() == 0)
            this.admin = player;
        this.players.add(player);

        this.board.putPlayer(player);
    }

}
