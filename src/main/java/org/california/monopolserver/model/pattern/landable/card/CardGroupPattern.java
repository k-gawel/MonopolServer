package org.california.monopolserver.model.pattern.landable.card;

import org.california.monopolserver.model.interfaces.Compositable;
import org.california.monopolserver.model.interfaces.Groupable;
import org.california.monopolserver.model.pattern.landable.LandablePattern;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class CardGroupPattern implements Compositable, LandablePattern {

    private String name;
    private Collection<Surprisable> cards;
    public String description;


    public CardGroupPattern(String name, String description) {
        this.name = name;
        this.description = description;
        this.cards = new HashSet<>();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardGroupPattern that = (CardGroupPattern) o;
        return Objects.equals(name, that.name);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    @Override
    public Collection<Surprisable> getComponents() {
        return this.cards;
    }


    @Override
    public <T extends Groupable> void addComponent(T card) {
        if(!(card instanceof Surprisable))
            throw new IllegalArgumentException("Component of CardGroup must be instance of Surprisable");

        this.cards.add((Surprisable) card);
    }


    public String getName() {
        return this.name;
    }

}
