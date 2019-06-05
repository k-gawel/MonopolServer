package org.california.monopolserver.model.pattern.landable.card;

import java.util.Objects;

public class ChargeCard implements Surprisable {

    private String description;
    private CardGroupPattern group;
    private int charge;


    public ChargeCard(CardGroupPattern group, String description, int charge) {
        this.description = description;
        this.charge = charge;
        this.group = group;
        this.group.addComponent(this);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChargeCard that = (ChargeCard) o;
        return charge == that.charge &&
                Objects.equals(description, that.description) &&
                Objects.equals(group, that.group);
    }


    @Override
    public int hashCode() {
        return Objects.hash(description, group, charge);
    }


    @Override
    public int getProperty() {
        return this.charge;
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
