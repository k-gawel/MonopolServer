package org.california.monopolserver.model.pattern.landable.town;

import org.california.monopolserver.model.interfaces.Groupable;
import org.california.monopolserver.model.pattern.landable.ChargeablePattern;
import org.california.monopolserver.model.pattern.landable.LandablePattern;

import java.util.Objects;

public class TownPattern extends LandablePattern implements ChargeablePattern, Groupable {

    private TownRegionPattern regionPattern;
    private String name;
    private int basicPrice;


    public TownPattern(TownRegionPattern regionPattern, String name, int basicPrice, int[] color) {
        super(color);
        this.name = name;
        this.basicPrice = basicPrice;
        this.regionPattern = regionPattern;
        this.regionPattern.addComponent(this);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TownPattern that = (TownPattern) o;
        return basicPrice == that.basicPrice &&
                Objects.equals(regionPattern, that.regionPattern) &&
                Objects.equals(name, that.name);
    }


    @Override
    public int hashCode() {
        return Objects.hash(regionPattern, name, basicPrice);
    }


    @Override
    public TownRegionPattern getGroup() {
        return regionPattern;
    }


    @Override
    public String getName() {
        return this.name;
    }


    @Override
    public int getBasicPrice() {
        return this.basicPrice;
    }


}
