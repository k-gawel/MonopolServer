package org.california.monopolserver.model.interfaces;

import java.util.Collection;

public interface Compositable {

    public <T extends Groupable> Collection<T> getComponents();
    public <T extends Groupable> void addComponent(T component);

}
