package org.california.monopolserver.exceptions;

import org.california.monopolserver.instance.player.Player;

public class TourException extends GameException {

    public TourException(Player accessing) {
        super("Current tour player is " + accessing.getGame().currentTour.player + ". " + accessing + " can't make this offer.");
    }

}
