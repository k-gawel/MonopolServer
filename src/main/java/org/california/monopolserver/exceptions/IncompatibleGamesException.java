package org.california.monopolserver.exceptions;

public class IncompatibleGamesException extends GameException {

    public IncompatibleGamesException() {
        super("Instances must be in the same game");
    }
}
