package org.california.monopolserver.instance.utils;

import org.california.monopolserver.model.interfaces.Identifiable;

import java.util.Objects;

public abstract class Instance implements Identifiable {

    protected final String UUID = java.util.UUID.randomUUID().toString();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instance instance = (Instance) o;
        return Objects.equals(UUID, instance.UUID);
    }


    @Override
    public int hashCode() {
        return Objects.hash(UUID);
    }


    @Override
    public String getUUID() {
        return this.UUID;
    }



}
