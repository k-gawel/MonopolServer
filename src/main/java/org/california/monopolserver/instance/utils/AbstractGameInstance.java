package org.california.monopolserver.instance.utils;

import org.california.monopolserver.instance.game.Game;

public abstract class AbstractGameInstance extends Instance implements GameInstance {

    protected Game game;

    public AbstractGameInstance(Game game) {
        this.game = game;
    }

    protected AbstractGameInstance() {}

    @Override
    final public Game getGame() {
        return this.game;
    }

}
