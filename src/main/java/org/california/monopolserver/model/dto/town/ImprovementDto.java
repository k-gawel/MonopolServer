package org.california.monopolserver.model.dto.town;

import org.california.monopolserver.instance.transferable.town.Improvement;
import org.california.monopolserver.model.dto.transferable.TransferableDto;

public class ImprovementDto extends TransferableDto {

    public final String instance_type = "improvement";

    public String owner;
    public String town;

    public ImprovementDto(Improvement improvement) {
        super(improvement);

        this.owner = improvement.getOwner().getUUID();
        this.town = improvement.getGroup().getUUID();
    }

}
