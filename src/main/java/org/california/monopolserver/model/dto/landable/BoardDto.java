package org.california.monopolserver.model.dto.landable;

import org.california.monopolserver.instance.board.board.Board;
import org.california.monopolserver.model.dto.InstanceDto;

import java.util.Collection;
import java.util.stream.Collectors;

public class BoardDto extends InstanceDto {

    public Collection<FieldDto> fields;

    public BoardDto(Board board) {
        super(board);
        this.fields = board.stream()
                .map(FieldDto::new)
                .collect(Collectors.toSet());
    }
}
