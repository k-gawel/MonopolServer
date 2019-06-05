package org.california.monopolserver.instance.board.board;

import org.california.monopolserver.instance.board.field.CardField;
import org.california.monopolserver.instance.board.field.ChargeableField;
import org.california.monopolserver.instance.transferable.town.TownRegion;
import org.california.monopolserver.instance.transferable.utility.UtilityRegion;
import org.california.monopolserver.instance.board.utils.CardGroup;
import org.california.monopolserver.instance.transferable.town.Town;
import org.california.monopolserver.instance.transferable.utility.Utility;
import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.model.pattern.board.BasicBoardPatternBuilder;
import org.california.monopolserver.model.pattern.board.BoardPattern;
import org.california.monopolserver.model.pattern.board.BoardPatternBuilder;
import org.california.monopolserver.model.pattern.landable.card.CardGroupPattern;
import org.california.monopolserver.model.pattern.landable.town.TownRegionPattern;
import org.california.monopolserver.model.pattern.landable.utility.UtilityRegionPattern;
import org.california.monopolserver.model.pattern.landable.LandablePattern;
import org.california.monopolserver.model.pattern.landable.town.TownPattern;
import org.california.monopolserver.model.pattern.landable.utility.UtilityPattern;

import java.util.Collection;
import java.util.HashSet;

public class BoardBuilder {

    private Board result;
    private final BoardPattern pattern;
    private final Game game;

    private Collection<TownRegion> townRegions = new HashSet<>();
    private Collection<UtilityRegion> utilityRegions = new HashSet<>();

    public enum BoardType {
        BASIC_BOARD(BasicBoardPatternBuilder.class);

        private final BoardPatternBuilder patternBuilder;

        BoardType(Class<BasicBoardPatternBuilder> pattern)  {
            BoardPatternBuilder patternBuilder1;
            try {
                patternBuilder1 = pattern.getConstructor().newInstance();
            } catch (Throwable t) {
                patternBuilder1 = new BasicBoardPatternBuilder();
            }

            this.patternBuilder = patternBuilder1;
        }

        public BoardPattern getBoardPattern() {
            return this.patternBuilder.build();
        }
    }


    public BoardBuilder(Game game, BoardType type) {
        this.pattern = type.getBoardPattern();
        this.game = game;
        this.result = new Board(game);
    }


    public Board build() {

        for(Integer key : pattern.fields.keySet()) {
            LandablePattern fieldPattern = pattern.fields.get(key);

            if(fieldPattern instanceof TownPattern) {
                Town town = buildTown((TownPattern) fieldPattern);
                result.add(new ChargeableField(town, key));
            }
            else if (fieldPattern instanceof UtilityPattern) {
                Utility utility = buildUtility((UtilityPattern) fieldPattern);
                result.add(new ChargeableField(utility, key));
            }
            else if (fieldPattern instanceof CardGroupPattern) {
                CardGroup cardGroup = buildCardGroup((CardGroupPattern) fieldPattern);
                result.add(new CardField(cardGroup, key));
            }
            else
                throw new IllegalStateException("Wrong field pattern");

        }

        return this.result;
    }


    private CardGroup buildCardGroup(CardGroupPattern pattern) {
        return new CardGroup(game, pattern);
    }


    private Town buildTown(TownPattern pattern) {
        TownRegion region = getTownRegion(pattern.getGroup());
        return new Town(pattern, region);
    }


    private Utility buildUtility(UtilityPattern pattern) {
        UtilityRegion region = getUtilityRegion(pattern.getGroup());
        return new Utility(pattern, region);
    }


    private TownRegion getTownRegion(TownRegionPattern pattern) {
        TownRegion result = townRegions.stream()
                .filter(r -> r.getPattern().equals(pattern))
                .findFirst()
                .orElse(new TownRegion(game, pattern));

        this.townRegions.add(result);
        return result;
    }


    private UtilityRegion getUtilityRegion(UtilityRegionPattern pattern) {
        UtilityRegion result = utilityRegions.stream()
                .filter(r -> r.getPattern().equals(pattern))
                .findFirst()
                .orElse(new UtilityRegion(game, pattern));

        utilityRegions.add(result);
        return result;
    }

}
