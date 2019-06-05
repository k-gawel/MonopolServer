package org.california.monopolserver.instance.transferable.utility;

import org.california.monopolserver.instance.utils.AbstractGameInstance;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.model.interfaces.Compositable;
import org.california.monopolserver.model.interfaces.Groupable;
import org.california.monopolserver.model.pattern.landable.utility.UtilityRegionPattern;

import java.util.Collection;
import java.util.HashSet;

public class UtilityRegion extends AbstractGameInstance implements Compositable {

    private Collection<Utility> utilities = new HashSet<>();
    private UtilityRegionPattern pattern;


    public UtilityRegion(Game game, UtilityRegionPattern pattern) {
        super(game);
        this.pattern = pattern;
    }


    public UtilityRegionPattern getPattern() {
        return pattern;
    }


    public int playersUtilities(Player player) {
        return (int) utilities.stream()
                .filter(u -> u.getOwner().equals(player))
                .count();
    }


    @Override
    public Collection<Utility> getComponents() {
        return utilities;
    }


    @Override
    public <T extends Groupable> void addComponent(T component) {
        if(!(component instanceof Utility))
            throw new IllegalArgumentException("Component of UtilityRegion must be instance of Utility");

        this.utilities.add((Utility) component);
    }


}
