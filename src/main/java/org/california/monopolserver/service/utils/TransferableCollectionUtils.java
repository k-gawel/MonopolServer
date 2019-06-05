package org.california.monopolserver.service.utils;

import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.instance.transferable.town.Improvement;
import org.california.monopolserver.instance.transferable.town.Town;
import org.california.monopolserver.instance.transferable.town.TownRegion;
import org.california.monopolserver.instance.utils.TransferableCollection;
import org.california.monopolserver.model.interfaces.Transferable;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class TransferableCollectionUtils {

    public static Collection<Transferable> concatWithoutMoney(TransferableCollection... offers) {
        return Arrays.stream(offers).flatMap(Collection::stream)
                .filter(t -> !(t instanceof Money))
                .collect(Collectors.toSet());
    }


    public static Collection<Improvement> getTownImprovements(TransferableCollection transferables, Town town) {
        return transferables.get(Improvement.class).stream()
                .filter(i -> i.getGroup().equals(town))
                .collect(Collectors.toSet());
    }


    public static Collection<Improvement> getRegionImprovements(TransferableCollection transferables, TownRegion region) {
        return transferables.get(Improvement.class).stream()
                .filter(i -> i.getGroup().getGroup().equals(region))
                .collect(Collectors.toSet());
    }


    public static Collection<Town> getRegionTowns(TransferableCollection transferables, TownRegion region) {
        return transferables.get(Town.class).stream()
                .filter(t -> t.getGroup().equals(region))
                .collect(Collectors.toSet());
    }

}
