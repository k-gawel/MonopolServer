package org.california.monopolserver.model.dto.card;

import org.california.monopolserver.instance.board.utils.CardGroup;
import org.california.monopolserver.model.dto.InstanceDto;

public class CardGroupDto extends InstanceDto {

    public String name;
    public String description;

    public CardGroupDto(CardGroup cardGroup) {
        super(cardGroup);
        this.name = cardGroup.pattern.getName();
        this.description = cardGroup.pattern.description;
    }

}
