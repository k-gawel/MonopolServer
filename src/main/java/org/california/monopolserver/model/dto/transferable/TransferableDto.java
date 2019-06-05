package org.california.monopolserver.model.dto.transferable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.california.monopolserver.instance.transferable.discount.Discount;
import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.instance.transferable.town.Improvement;
import org.california.monopolserver.instance.transferable.town.Town;
import org.california.monopolserver.instance.transferable.utility.Utility;
import org.california.monopolserver.model.dto.InstanceDto;
import org.california.monopolserver.model.dto.town.ImprovementDto;
import org.california.monopolserver.model.dto.town.TownRegionDto;
import org.california.monopolserver.model.dto.utility.UtilityRegionDto;
import org.california.monopolserver.model.dto.town.TownDto;
import org.california.monopolserver.model.dto.utility.UtilityDto;
import org.california.monopolserver.model.interfaces.Transferable;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "transferable_type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MoneyDto.class, name = "money"),
        @JsonSubTypes.Type(value = DiscountDto.class, name = "discount"),
        @JsonSubTypes.Type(value = ImprovementDto.class, name = "improvement"),
        @JsonSubTypes.Type(value = TownRegionDto.class, name = "town_region"),
        @JsonSubTypes.Type(value = TownDto.class, name = "town"),
        @JsonSubTypes.Type(value = UtilityRegionDto.class, name = "utility_region"),
        @JsonSubTypes.Type(value = UtilityDto.class, name = "utility")
})
public abstract class TransferableDto extends InstanceDto {

    public TransferableDto(Transferable instance) {
        super(instance);
    }

    public static TransferableDto create(Transferable transferable) {
        if(transferable instanceof Money)
            return new MoneyDto((Money) transferable);
        else if(transferable instanceof Town)
            return new TownDto((Town) transferable);
        else if(transferable instanceof Utility)
            return new UtilityDto((Utility) transferable);
        else if(transferable instanceof Improvement)
            return new ImprovementDto((Improvement) transferable);
        else if(transferable instanceof Discount)
            return new DiscountDto((Discount) transferable);
        else if(transferable == null)
            return null;
        else
            throw new IllegalArgumentException("Wrong tranferable class " + transferable.getClass().toString());
    }

}
