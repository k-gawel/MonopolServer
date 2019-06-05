package org.california.monopolserver.instance.executable.tour;

import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.game.Game;

public class TourBuilder {

    public static Tour nexTour(Game game) {
        Tour newTour = createNewTour(game);
        setNewTour(newTour);
        return newTour;
    }


    private static Tour createNewTour(Game game) {
        Player nextPlayer = getNextPlayer(game);
        int nextIndex = getNextIndex(game);
        return new Tour(nextPlayer, nextIndex);
    }


    private static Player getNextPlayer(Game game) {
        Tour currentTour = game.currentTour;

        if(currentTour == null)
            return game.players.get(0);
        else {
            int currentPlayerIndex = game.players.indexOf(currentTour.player);
            return currentPlayerIndex == game.players.size() - 1 ?
                    game.players.get(0) : game.players.get(currentPlayerIndex + 1);
        }
    }


    private static int getNextIndex(Game game) {
        return game.currentTour == null ? 0 : game.currentTour.index + 1;
    }


    private static void setNewTour(Tour newTour) {
        Game game = newTour.getGame();

        if(game.currentTour != null)
            game.previousTours.add(game.currentTour);

        game.currentTour = newTour;
    }


}
