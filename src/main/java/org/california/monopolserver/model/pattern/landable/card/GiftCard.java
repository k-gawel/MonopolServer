package org.california.monopolserver.model.pattern.landable.card;

import java.util.Objects;

public class GiftCard implements Surprisable {

    private int gift;
    private String description;
    private CardGroupPattern group;


    public GiftCard(CardGroupPattern group, String description, int gift) {
        this.gift = gift;
        this.description = description;
        this.group = group;
        this.group.addComponent(this);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCard giftCard = (GiftCard) o;
        return gift == giftCard.gift &&
                Objects.equals(description, giftCard.description) &&
                Objects.equals(group, giftCard.group);
    }


    @Override
    public int hashCode() {
        return Objects.hash(gift, description, group);
    }


    @Override
    public int getProperty() {
        return this.gift;
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
