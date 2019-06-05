package org.california.monopolserver.model.dto.game;

import org.california.monopolserver.exceptions.GameException;
import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.model.dto.InstanceDto;
import org.california.monopolserver.model.dto.card.CardGroupDto;
import org.california.monopolserver.model.dto.landable.BoardDto;
import org.california.monopolserver.model.dto.town.TownRegionDto;
import org.california.monopolserver.model.dto.transferable.DiscountDto;
import org.california.monopolserver.model.dto.transferable.TransactionDto;
import org.california.monopolserver.model.dto.utility.UtilityRegionDto;

import java.util.Collection;

public class GameDto extends InstanceDto {

    public PlayerDto bank;
    public String admin;
    public Collection<PlayerDto> players;
    public Collection<TownRegionDto> town_regions;
    public Collection<UtilityRegionDto> utility_regions;
    public Collection<CardGroupDto> card_groups;
    public Collection<DiscountDto>  discounts;
    public BoardDto board;
    public TourDto current_tour;
    public TransactionDto current_transaction;


    public GameDto(Game game) throws GameException {
        super(game);
    }


}
