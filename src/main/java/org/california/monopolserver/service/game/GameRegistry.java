package org.california.monopolserver.service.game;

import org.california.monopolserver.exceptions.PlayerNotFoundException;
import org.california.monopolserver.exceptions.TransactionNotFoundException;
import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.instance.player.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class GameRegistry {

    public static Collection<Game> games = new HashSet<>();

    public static void add(Game game) {
        games.add(game);
    }

    public static Optional<Transaction> getTransactionO(String uuid) {
        return games.stream()
                .filter(g -> g.currentTransaction != null)
                .map(g -> g.currentTransaction)
                .filter(t -> t.getUUID().equals(uuid))
                .findFirst();
    }


    public static Transaction getTransaction(String uuid) throws TransactionNotFoundException {
        return getTransactionO(uuid)
                .orElseThrow(() -> new TransactionNotFoundException(uuid));
    }


    public static Optional<Game> getByUuidO(String uuid) {
        return games.stream()
                .filter(g -> g.getUUID().equals(uuid))
                .findFirst();
    }


    public static Game getByUuid(String uuid) {
        return getByUuidO(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));
    }


    public static Collection<Game> getPublicGames() {
        return games;
    }


    public static Optional<Player> getPlayerByUuidO(String uuid) {
        return games.stream()
                .flatMap(g -> g.getPlayersWithBank().stream())
                .filter(p -> p.getUUID().equals(uuid))
                .findFirst();
    }


    public static Player getPlayerByUuid(String uuid) throws PlayerNotFoundException {
        return getPlayerByUuidO(uuid)
                .orElseThrow(() -> new PlayerNotFoundException(uuid));
    }

}
