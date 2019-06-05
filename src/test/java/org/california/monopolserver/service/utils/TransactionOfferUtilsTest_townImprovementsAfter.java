package org.california.monopolserver.service.utils;

import org.california.monopolserver.instance.executable.transaction.BankTransaction;
import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.instance.player.Bank;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.transferable.town.Improvement;
import org.california.monopolserver.instance.transferable.town.Town;
import org.junit.Before;
import org.junit.Test;
import org.california.monopolserver.utils.InstanceMock;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionOfferUtilsTest_townImprovementsAfter {


    Game game;
    Bank bank;
    Player player;
    Town town;

    @Before
    public void setInstances() {
        game = InstanceMock.getGame();
        bank = game.bank;
        player = new Player(game, "PLAYER");
        town = bank.properties.get(Town.class).stream().findFirst().get();
    }

    public void setTownToPlayerAndAddImprovements(int size) {
        town.getGroup().getComponents().forEach(t -> t.transfer(bank, player));

        for(int i = 0; i < size; i++) {
            Improvement improvement = new Improvement(town);
            improvement.transfer(bank, player);
        }
    }


    @Test
    public void noneWillBeSelled() {
        setTownToPlayerAndAddImprovements(5);

        Transaction transaction = new BankTransaction(player);
        Collection<Improvement> improvements = new HashSet<>(town.getComponents());

        assertTrue(TransactionOfferUtils.townImprovementsAfter(transaction, town).containsAll(improvements));
    }


    @Test
    public void allWillBeSelled() {
        setTownToPlayerAndAddImprovements(5);

        Transaction transaction = new BankTransaction(player);
        town.getComponents().forEach(i -> transaction.add(player, i));

        assertTrue(TransactionOfferUtils.townImprovementsAfter(transaction, town).isEmpty());
    }


    @Test
    public void someWillBeBought() {
        Improvement i1 = new Improvement(town);
        Improvement i2 = new Improvement(town);

        Collection<Improvement> improvements = Arrays.asList(i1, i2);

        Transaction transaction = new BankTransaction(player);
        improvements.forEach(i -> transaction.add(bank, i));

        assertEquals(TransactionOfferUtils.townImprovementsAfter(transaction, town), improvements);
    }


}
