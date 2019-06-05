package org.california.monopolserver.model.interfaces;

public interface Groupable {
    public <T extends Compositable> T getGroup();
}
