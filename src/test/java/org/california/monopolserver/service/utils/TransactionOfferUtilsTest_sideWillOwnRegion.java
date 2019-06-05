package org.california.monopolserver.service.utils;

import org.california.monopolserver.instance.executable.transaction.BankTransaction;
import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.instance.player.Bank;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.transferable.town.Town;
import org.california.monopolserver.instance.transferable.town.TownRegion;
import org.junit.Before;
import org.junit.Test;
import org.california.monopolserver.utils.InstanceMock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionOfferUtilsTest_sideWillOwnRegion {

    Game game;
    Player player;
    Bank bank;
    TownRegion region;
    List<Town> towns;
    final int regionSize = 3;

    @Before
    public void setInstances() {
        game = InstanceMock.getGame();
        player = new Player(game, "PLAYER");
        bank = game.bank;
        region = bank.properties.get(Town.class).stream()
                .filter(t -> t.getGroup().getComponents().size() == regionSize)
                .map(Town::getGroup)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Regon not found"));
        towns = new ArrayList<>(region.getComponents());
    }


    @Test
    public void alreadyOwnsAndNoneWillBeTransferred() {
        Transaction transaction = new BankTransaction(player);
        assertTrue(TransactionOfferUtils.sideWillOwnRegion(transaction, bank, region));
    }


    @Test
    public void alreadyOwnsButAllWillBeTransferred() {
        Transaction transaction = new BankTransaction(player);
        transaction.add(bank, towns.get(0));
        transaction.add(bank, towns.get(1));
        transaction.add(bank, towns.get(2));
        assertFalse(TransactionOfferUtils.sideWillOwnRegion(transaction, bank, region));
    }


    @Test
    public void doesntOwnsAndNoneIsAddedToTransaction() {
        Transaction transaction = new BankTransaction(player);

        assertTrue(TransferableCollectionUtils.getRegionTowns(player.properties, region).isEmpty());

        assertFalse(TransactionOfferUtils.sideWillOwnRegion(transaction, player, region));
    }


    @Test
    public void doesntOwnsAntButWillGetAllAfter() {
        Transaction transaction = new BankTransaction(player);
        transaction.add(bank, towns.get(0));
        transaction.add(bank, towns.get(1));
        transaction.add(bank, towns.get(2));

        assertTrue(TransactionOfferUtils.sideWillOwnRegion(transaction, player, region));
    }


    @Test
    public void ownsPartAndAnotherPartWillBeSended() {
        List<Town> towns = new ArrayList<>(region.getComponents());
        towns.get(0).transfer(bank, player);
        towns.get(1).transfer(bank, player);

        Transaction transaction = new BankTransaction(player);
        transaction.add(bank, towns.get(2));

        assertTrue(TransactionOfferUtils.sideWillOwnRegion(transaction, player, region));
    }


    @Test
    public void ownsPartAndAnotherPartWillBeSendedButWillSellSome() {
        List<Town> towns = new ArrayList<>(region.getComponents());
        towns.get(0).transfer(bank, player);
        towns.get(1).transfer(bank, player);

        Transaction transaction = new BankTransaction(player);
        transaction.add(bank, towns.get(2));
        transaction.add(player, towns.get(1));

        assertFalse(TransactionOfferUtils.sideWillOwnRegion(transaction, player, region));
    }

}
