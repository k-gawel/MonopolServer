package org.california.monopolserver.model.pattern.landable;

public abstract class LandablePattern {

    public final int[] color;

    protected LandablePattern(int[] color) {
        this.color = color;
    }
}
