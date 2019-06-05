package org.california.monopolserver.model.pattern.landable.town;

import org.california.monopolserver.model.interfaces.Compositable;
import org.california.monopolserver.model.interfaces.Groupable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class TownRegionPattern implements Compositable {

    private String name;
    private Collection<TownPattern> components = new HashSet<>();


    public TownRegionPattern(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TownRegionPattern that = (TownRegionPattern) o;
        return Objects.equals(name, that.name);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    @Override
    public Collection<TownPattern> getComponents() {
        return this.components;
    }


    @Override
    public <T extends Groupable> void addComponent(T townPattern) {
        if(!townPattern.getClass().equals(TownPattern.class))
            throw new IllegalArgumentException("Component in TownRegionPattern must be instance of TownPattern");

        this.components.add((TownPattern) townPattern);
    }


    public String getName() {
        return this.name;
    }


    public String getColor() {
        return this.name;
    }
}
