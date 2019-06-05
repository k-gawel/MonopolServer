package org.california.monopolserver.model.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.california.monopolserver.instance.board.field.Field;
import org.california.monopolserver.model.dto.card.CardGroupDto;
import org.california.monopolserver.model.dto.game.PlayerDto;
import org.california.monopolserver.model.dto.landable.BoardDto;
import org.california.monopolserver.model.interfaces.Identifiable;
import org.california.monopolserver.model.interfaces.Transferable;

import java.io.Serializable;
import java.util.Objects;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "instance_type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PlayerDto.class, name = "player"),
        @JsonSubTypes.Type(value = BoardDto.class, name = "board"),
        @JsonSubTypes.Type(value = CardGroupDto.class, name = "card_group"),
        @JsonSubTypes.Type(value = Transferable.class, name = "transferable"),
        @JsonSubTypes.Type(value = Field.class, name = "field")
})
public abstract class InstanceDto implements Serializable  {

    public String uuid;

    public InstanceDto(Identifiable instance) {
        this.uuid = instance.getUUID();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstanceDto that = (InstanceDto) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }


}
