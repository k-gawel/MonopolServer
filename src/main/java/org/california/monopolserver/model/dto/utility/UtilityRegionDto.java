package org.california.monopolserver.model.dto.utility;

import org.california.monopolserver.instance.transferable.utility.UtilityRegion;
import org.california.monopolserver.model.dto.InstanceDto;

import java.util.Collection;
import java.util.stream.Collectors;

public class UtilityRegionDto extends InstanceDto {

    public String name;
    public Collection<UtilityDto> utilities;

    public UtilityRegionDto(UtilityRegion region) {
        super(region);

        this.name = region.getPattern().getName();
        this.utilities = region.getComponents().stream()
                .map(UtilityDto::new)
                .collect(Collectors.toList());
    }

}
