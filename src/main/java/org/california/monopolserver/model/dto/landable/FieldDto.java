package org.california.monopolserver.model.dto.landable;

import org.california.monopolserver.instance.board.field.ChargeableField;
import org.california.monopolserver.instance.board.field.Field;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.transferable.town.Town;
import org.california.monopolserver.model.dto.InstanceDto;

import java.util.Collection;
import java.util.stream.Collectors;


public class FieldDto extends InstanceDto {

    public int number;
    public String type;
    public String landable;
    public Collection<String> players;

    public FieldDto(Field field) {
        super(field);

        if(field instanceof ChargeableField)
            type = field.getLandable() instanceof Town ?
                    "town_field" : "utility_field";
        else
            type = "card_group_field";

        this.landable = field.getLandable().getUUID();

        this.number = field.getNumber();

        this.players = field.getPlayers().stream()
                .map(Player::getUUID)
                .collect(Collectors.toSet());
    }

}
