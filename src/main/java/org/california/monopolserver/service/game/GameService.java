package org.california.monopolserver.service.game;

import org.california.monopolserver.instance.board.field.Field;
import org.california.monopolserver.instance.board.utils.CardGroup;
import org.california.monopolserver.instance.executable.tour.Tour;
import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.instance.transferable.discount.Discount;
import org.california.monopolserver.instance.transferable.town.Town;
import org.california.monopolserver.instance.transferable.utility.Utility;
import org.california.monopolserver.model.dto.card.CardGroupDto;
import org.california.monopolserver.model.dto.game.GameDto;
import org.california.monopolserver.model.dto.game.PlayerDto;
import org.california.monopolserver.model.dto.game.TourDto;
import org.california.monopolserver.model.dto.landable.BoardDto;
import org.california.monopolserver.model.dto.town.TownRegionDto;
import org.california.monopolserver.model.dto.transferable.DiscountDto;
import org.california.monopolserver.model.dto.transferable.TransactionDto;
import org.california.monopolserver.model.dto.transferable.TransactionOperations;
import org.california.monopolserver.model.dto.utility.UtilityRegionDto;
import org.california.monopolserver.service.transaction.offer.utils.offer.TransactionOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class GameService {


    private final TransactionOfferService transactionOfferService;

    @Autowired
    public GameService(TransactionOfferService transactionOfferService) {
        this.transactionOfferService = transactionOfferService;
    }


    public GameDto getGameDto(Game game) {
        GameDto result = new GameDto(game);

        result.bank    = new PlayerDto(game.bank);
        result.admin   = game.admin.getUUID();
        result.players = game.players.stream().map(PlayerDto::new).collect(Collectors.toSet());;

        result.town_regions    = getTownRegions(game);
        result.utility_regions = getUtilityRegions(game);
        result.card_groups     = getCardGroups(game);

        result.board               = new BoardDto(game.board);
        result.current_transaction = getTransactionDto(game.currentTransaction);
        result.current_tour        = getTourDto(game.currentTour);

        result.discounts = getDiscounts(game);

        return result;
    }


    private TourDto getTourDto(Tour tour) {
        if(tour == null) return null;

        return new TourDto(tour);
    }


    private TransactionDto getTransactionDto(Transaction transaction) {
        if(transaction == null) return null;

        Collection<TransactionOperations> operations = transactionOfferService.createInfo(transaction);
        return new TransactionDto(transaction, operations);
    }

    private Collection<TownRegionDto> getTownRegions(Game game) {
        return game.board.stream()
                .map(Field::getLandable)
                .filter(l -> l instanceof Town)
                .map(l -> (Town) l)
                .map(Town::getGroup)
                .map(TownRegionDto::new)
                .collect(Collectors.toSet());
    }


    private Collection<UtilityRegionDto> getUtilityRegions(Game game) {
        return game.board.stream()
                .map(Field::getLandable)
                .filter(l -> l instanceof Utility)
                .map(l -> (Utility) l)
                .map(Utility::getGroup)
                .map(UtilityRegionDto::new)
                .collect(Collectors.toSet());
    }


    private Collection<CardGroupDto> getCardGroups(Game game) {
        return game.board.stream()
                .map(Field::getLandable)
                .filter(l -> l instanceof CardGroup)
                .map(c -> (CardGroup) c)
                .map(CardGroupDto::new)
                .collect(Collectors.toSet());
    }


    private Collection<DiscountDto> getDiscounts(Game game) {
        return game.players.stream()
                .flatMap(p -> p.properties.get(Discount.class).stream())
                .map(DiscountDto::new)
                .collect(Collectors.toSet());
    }

}
