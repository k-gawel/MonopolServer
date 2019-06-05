package org.california.monopolserver.service.utils.TransferableCollectionUtilsTest;

import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.instance.transferable.town.Town;
import org.california.monopolserver.instance.transferable.town.TownRegion;
import org.california.monopolserver.instance.utils.TransferableCollection;
import org.california.monopolserver.model.interfaces.Transferable;
import org.california.monopolserver.utils.InstanceMock;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.california.monopolserver.service.utils.TransferableCollectionUtils.concatWithoutMoney;
import static org.junit.jupiter.api.Assertions.*;


public class concatWithoutMoneyTest {


    @Test
    public void nullableOrNoArgs() {

        assertTrue(concatWithoutMoney().isEmpty());
        assertTrue(concatWithoutMoney(null).isEmpty());

    }


    @Test
    public void doesntHaveMoney() {
        Game game = new Game();

        TransferableCollection collection1 = new TransferableCollection(game);
        TransferableCollection collection2 = new TransferableCollection(game);

        assertNotNull(collection1.getMoney());
        assertNotNull(collection2.getMoney());

        Collection<Transferable> result = concatWithoutMoney(collection1, collection2);

        long resultMoney = result.stream().filter(t -> t instanceof Money).count();

        assertEquals(resultMoney, 0);
    }


    @Test
    public void containsAllElements() {
        Game game = InstanceMock.getGame();
        TownRegion region = InstanceMock.getTownRegion(game);

        List<Town> towns = new ArrayList<>(region.getComponents());


        TransferableCollection collection1 = new TransferableCollection(game);
        collection1.add(towns.get(0));
        collection1.add(towns.get(1));

        TransferableCollection collection2 = new TransferableCollection(game);
        collection2.add(towns.get(2));

        int size = collection1.size() + collection2.size();

        Collection<Transferable> result = concatWithoutMoney(collection1, collection2);

        assertEquals(result.size(), size -2);
    }


}
