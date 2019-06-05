package org.california.monopolserver.instance.transferable.town;

import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.instance.player.Bank;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.utils.AbstractGameInstance;
import org.california.monopolserver.model.interfaces.Compositable;
import org.california.monopolserver.model.interfaces.Groupable;
import org.california.monopolserver.model.pattern.landable.town.TownRegionPattern;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class TownRegion extends AbstractGameInstance implements Compositable {

    private Collection<Town> towns = new HashSet<>();
    private final TownRegionPattern pattern;


    public TownRegion(Game game, TownRegionPattern pattern) {
        super(game);

        this.pattern = pattern;
    }


    public TownRegionPattern getPattern() {
        return this.pattern;
    }


    @Override
    public Collection<Town> getComponents() {
        return this.towns;
    }


    @Override
    public <T extends Groupable> void addComponent(T town) {
        if(!(town instanceof Town))
            throw new IllegalArgumentException("Component in TownRegion must be instance of Town");

        this.towns.add((Town) town);
    }


    public Player getOwner() {
        Collection<Player> owners = towns.stream().map(Town::getOwner).collect(Collectors.toSet());
        Player player = owners.stream().findFirst().get();

        return owners.size() == 1 && !(player instanceof Bank) ? player : null;
    }


    public Collection<Improvement> getImprovements() {
        return getComponents().stream()
                .map(Town::getComponents)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

}
