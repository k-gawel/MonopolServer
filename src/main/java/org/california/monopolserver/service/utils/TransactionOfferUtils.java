package org.california.monopolserver.service.utils;

import org.california.monopolserver.exceptions.NotSideOfTransactionException;
import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.transferable.town.Improvement;
import org.california.monopolserver.instance.transferable.town.Town;
import org.california.monopolserver.instance.transferable.town.TownRegion;
import org.california.monopolserver.model.interfaces.Transferable;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.california.monopolserver.service.utils.TransferableCollectionUtils.*;


public class TransactionOfferUtils {


    public static Collection<Transferable> bothOffersWithoutMoney(Transaction transaction) {
        return concatWithoutMoney(transaction.getInitiatorOffer(), transaction.getInvitedOffer());
    }


    public static boolean sideWillOwnRegion(Transaction transaction, Player side, TownRegion region) {
        Collection<Town> sideTowns   = getRegionTowns(side.properties, region);
        Collection<Town> townsOnSell = getRegionTowns(transaction.getOffer(side), region);
        Collection<Town> townsOnBuy  = getRegionTowns(transaction.getOffer(transaction.getOppositeSide(side)), region);

        Collection<Town> townsAfter  = Stream.of(sideTowns, townsOnBuy).flatMap(Collection::stream)
                .filter(t -> !townsOnSell.contains(t))
                .collect(Collectors.toSet());

        return townsAfter.containsAll(region.getComponents());
    }


    public static Collection<Improvement> townImprovementsAfter(Transaction tr, Town town) {
        try {
            tr.getOffer(town.getOwner());
        } catch (NotSideOfTransactionException e) {
            return Collections.emptySet();
        }

        Collection<Improvement> currentImprovements = town.getComponents();
        Collection<Improvement> sellImprovements = getTownImprovements(tr.getOffer(town.getOwner()), town);
        Collection<Improvement> buyImprovements = getTownImprovements(tr.getOffer(tr.getOppositeSide(town.getOwner())), town);

        return Stream.of(currentImprovements, buyImprovements).flatMap(Collection::stream)
                .filter(t -> !sellImprovements.contains(t))
                .collect(Collectors.toSet());
    }


    public static Collection<Improvement> regionImprovementsAfter(Transaction t, TownRegion region) {
        return region.getComponents().stream()
                .map(town -> townImprovementsAfter(t, town))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

}
