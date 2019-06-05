package org.california.monopolserver.model.pattern.landable.utility;

import org.california.monopolserver.model.interfaces.Compositable;
import org.california.monopolserver.model.interfaces.Groupable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class UtilityRegionPattern implements Compositable {

    private String name;
    private Collection<UtilityPattern> components;


    public UtilityRegionPattern(String name) {
        this.name = name;
        this.components = new HashSet<>();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtilityRegionPattern that = (UtilityRegionPattern) o;
        return Objects.equals(name, that.name);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    @Override
    public Collection<UtilityPattern> getComponents() {
        return components;
    }


    @Override
    public <T extends Groupable> void addComponent(T utility) {
        if(!utility.getClass().equals(UtilityPattern.class))
            throw new IllegalArgumentException("Component in UtilityRegionPattern must instance of UtilityPattern");

        this.components.add((UtilityPattern) utility);
    }


    public String getName() {
        return this.name;
    }


}
