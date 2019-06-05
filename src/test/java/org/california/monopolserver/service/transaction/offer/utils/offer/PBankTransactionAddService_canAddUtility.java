package org.california.monopolserver.service.transaction.offer.utils.offer;

import org.california.monopolserver.instance.board.field.Field;
import org.california.monopolserver.instance.executable.transaction.BankTransaction;
import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.transferable.utility.Utility;
import org.junit.Before;
import org.junit.Test;
import org.california.monopolserver.utils.InstanceMock;

import static org.junit.jupiter.api.Assertions.*;

public class PBankTransactionAddService_canAddUtility {

    Game game;
    Player player;
    Utility utility;

    PBankTransactionAddService service = new PBankTransactionAddService();

    @Before
    public void setInstances() {
        game = InstanceMock.getGame();
        player = new Player(game, "PLAYER");
        utility = game.bank.properties.get(Utility.class).stream().findFirst().orElseThrow(() -> new IllegalStateException("No utility"));
    }

    @Test
    public void playerDoesntStandsOnField() {
        BankTransaction transaction = new BankTransaction(player);

        assertEquals(utility.getOwner(), game.bank);
        assertFalse(utility.standsOn(player));
        assertFalse(service.canAdd(transaction, game.bank, utility));
    }


    @Test
    public void playerStandsOnField() {
        Field field = game.board.get(utility);
        game.board.putPlayer(player, field);

        BankTransaction transaction = new BankTransaction(player);

        assertEquals(utility.getOwner(), game.bank);
        assertTrue(utility.standsOn(player));
        assertTrue(service.canAdd(transaction, game.bank, utility));
    }




}
