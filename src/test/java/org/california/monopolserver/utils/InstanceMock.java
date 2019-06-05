package org.california.monopolserver.utils;

import org.california.monopolserver.instance.board.board.BoardBuilder;
import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.instance.game.GameBuilder;
import org.california.monopolserver.instance.transferable.town.Town;
import org.california.monopolserver.instance.transferable.town.TownRegion;
import org.california.monopolserver.model.pattern.landable.town.TownPattern;
import org.california.monopolserver.model.pattern.landable.town.TownRegionPattern;

public class InstanceMock {


    public static Game getGame() {
        return GameBuilder.create(BoardBuilder.BoardType.BASIC_BOARD);
    }


    public static TownRegion getTownRegion(Game game) {

        TownRegionPattern regionPattern = new TownRegionPattern("TOWN_REGION");
        TownPattern town1pattern = new TownPattern(regionPattern, "TOWN_1", 100);
        TownPattern town2pattern = new TownPattern(regionPattern, "TOWN_2", 150);
        TownPattern town3pattern = new TownPattern(regionPattern, "TOWN_2", 200);

        TownRegion townRegion = new TownRegion(game, regionPattern);
        Town town1 = new Town(town1pattern, townRegion);
        Town town2 = new Town(town2pattern, townRegion);
        Town town3 = new Town(town3pattern, townRegion);

        return townRegion;
    }

}
