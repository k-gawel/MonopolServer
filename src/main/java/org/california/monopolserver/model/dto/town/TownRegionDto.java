package org.california.monopolserver.model.dto.town;

import org.california.monopolserver.instance.transferable.town.TownRegion;
import org.california.monopolserver.model.dto.InstanceDto;

import java.util.Collection;
import java.util.stream.Collectors;

public class TownRegionDto extends InstanceDto {

    public final String instance_type = "town_region";

    public String name;
    public String color;
    public Collection<TownDto> towns;

    public TownRegionDto(TownRegion townRegion) {
        super(townRegion);

        this.name = townRegion.getPattern().getName();
        this.color = townRegion.getPattern().getColor();
        this.towns = townRegion.getComponents().stream()
                .map(TownDto::new)
                .collect(Collectors.toSet());
    }


}
