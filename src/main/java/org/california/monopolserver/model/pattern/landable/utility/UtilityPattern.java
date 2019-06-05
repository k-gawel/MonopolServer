package org.california.monopolserver.model.pattern.landable.utility;

import org.california.monopolserver.model.interfaces.Groupable;
import org.california.monopolserver.model.pattern.landable.ChargeablePattern;
import org.california.monopolserver.model.pattern.landable.LandablePattern;

import java.util.Objects;

public class UtilityPattern implements ChargeablePattern, Groupable, LandablePattern {

    private UtilityRegionPattern region;
    private String name;
    private int basicPrice;


    public UtilityPattern(UtilityRegionPattern region, String name, int basicPrice) {
        this.region = region;
        this.name = name;
        this.basicPrice = basicPrice;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtilityPattern that = (UtilityPattern) o;
        return basicPrice == that.basicPrice &&
                Objects.equals(region, that.region) &&
                Objects.equals(name, that.name);
    }


    @Override
    public int hashCode() {
        return Objects.hash(region, name, basicPrice);
    }


    @Override
    public UtilityRegionPattern getGroup() {
        return region;
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
