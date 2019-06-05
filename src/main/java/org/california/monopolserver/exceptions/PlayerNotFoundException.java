package org.california.monopolserver.exceptions;

public class PlayerNotFoundException extends GameException {

    public PlayerNotFoundException(String playerUuid) {
        super("Can't find player with uuid: " + playerUuid);
    }

    public PlayerNotFoundException(String playerUuid, String gameUuid) {
        super("Can't find player of uuid: " + playerUuid + " in game of uuid: " + gameUuid);
    }

}
