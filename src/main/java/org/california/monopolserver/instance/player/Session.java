package org.california.monopolserver.instance.player;

public class Session {

    public String gameUuid;
    public String playerUuid;
    public String session;


    public Session(String string) {
        String[] parts = string.split("\\|");
        if(parts.length != 3)
            throw new IllegalArgumentException("Session string must contains two '|'.");

        this.gameUuid = parts[0];
        this.playerUuid = parts[1];
        this.session = parts[2];
    }

    public Session(Player player) {
        this.gameUuid = player.getGame().getUUID();
        this.playerUuid = player.getUUID();
        this.session = player.session;
    }

    public String toString() {
        return gameUuid + "|" + playerUuid + "|" + session;
    }


}
