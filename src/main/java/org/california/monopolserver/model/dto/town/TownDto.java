package org.california.monopolserver.model.dto.town;

import org.california.monopolserver.instance.transferable.town.Town;
import org.california.monopolserver.model.dto.transferable.TransferableDto;

import java.util.Collection;
import java.util.stream.Collectors;

public class TownDto extends TransferableDto {

    public final String instance_type = "town";

    public String name;
    public int price;
    public String region;
    public String owner;
    public Collection<ImprovementDto> improvements;

    public TownDto(Town town) {
        super(town);

        this.price = town.getPattern().getBasicPrice();
        this.region = town.getGroup().getUUID();
        this.owner = town.getOwner().getUUID();
        this.name = town.getPattern().getName();
        this.improvements = town.getComponents().stream()
                .map(ImprovementDto::new)
                .collect(Collectors.toSet());
    }

}
