package org.california.monopolserver.model.pattern.landable.card;

import java.util.Objects;

public class MoveCard implements Surprisable {

    private CardGroupPattern group;
    private String description;
    private int field;


    public MoveCard(CardGroupPattern group, String description, int field) {
        this.description = description;
        this.field = field;
        this.group = group;
        this.group.addComponent(this);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoveCard moveCard = (MoveCard) o;
        return field == moveCard.field &&
                Objects.equals(group, moveCard.group) &&
                Objects.equals(description, moveCard.description);
    }


    @Override
    public int hashCode() {
        return Objects.hash(group, description, field);
    }


    @Override
    public int getProperty() {
        return this.field;
    }


    @Override
    public String getDescription() {
        return this.description;
    }


    @Override
    public CardGroupPattern getGroup() {
        return this.group;
    }


}
