package org.california.monopolserver.model.dto.utility;

import org.california.monopolserver.instance.transferable.utility.Utility;
import org.california.monopolserver.model.dto.transferable.TransferableDto;

public class UtilityDto extends TransferableDto {

    public final String instance_type = "utility";

    public String owner;
    public String region;
    public String name;
    public int price;
    public int[] color;

    public UtilityDto(Utility utility) {
        super(utility);

        this.owner = utility.getOwner().getUUID();
        this.region = utility.getGroup().getUUID();
        this.name = utility.getPattern().getName();
        this.price = utility.getBasicPrice().getBasicPrice().intValue();
        this.color = utility.getPattern().color;
    }

}
