package org.california.monopolserver.service.game;

import org.california.monopolserver.exceptions.GameException;
import org.california.monopolserver.instance.board.board.BoardBuilder;
import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.instance.game.GameBuilder;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.player.Session;
import org.california.monopolserver.instance.transferable.town.Town;
import org.california.monopolserver.instance.transferable.town.TownRegion;
import org.california.monopolserver.model.dto.game.GameDto;
import org.california.monopolserver.model.dto.game.GameLink;
import org.california.monopolserver.model.ws_message.response.game.NewPlayerResponse;
import org.california.monopolserver.service.CustomMessageTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Service
public class NewPlayerService {


    private final CustomMessageTemplate messageTemplate;
    private final GameService gameService;

    @Autowired
    public NewPlayerService(CustomMessageTemplate messageTemplate, GameService gameService) {
        this.messageTemplate = messageTemplate;
        this.gameService = gameService;
    }




    // Session Id, GameDto
    public Entry<String, GameDto> getGame(Player player, Game game) throws GameException {
        String session  = new Session(player).toString();
        GameDto gameDto = gameService.getGameDto(game);

        return new AbstractMap.SimpleEntry<>(session, gameDto);
    }


    public Entry<String, GameDto> getGame(String sessionString) throws IllegalAccessException, GameException {
        Session session = new Session(sessionString);

        Player player = GameRegistry.getPlayerByUuid(session.playerUuid);

        if(!player.session.equals(session.session))
            throw new IllegalAccessException("Wrong session for player: [ " + player.toString() + " ]"
                        + " expected: [ " + player.session + " ] found: [ " + session.session + " ] ");

        return getGame(player, player.getGame());
    }


    public Entry<String, GameDto> createGame(@NotEmpty String playerName) throws GameException {
        Game game = GameBuilder.create(BoardBuilder.BoardType.BASIC_BOARD);
        Player player = new Player(game, playerName);

        ///TODO DELETE AFTER TESTS

        /////
        TownRegion freeRegion = game.bank.properties.get(Town.class).stream().map(Town::getGroup).filter(g -> g.getOwner() == null).findFirst().get();
        freeRegion.getComponents().forEach(t -> t.transfer(game.bank, player));
        /////


        return getGame(player, game);
    }


    public Entry<String, GameDto> joinGame(@NotEmpty String gameUuid, @NotEmpty String playerName) throws GameException {
        Game game = GameRegistry.getByUuidO(gameUuid)
                .orElseThrow(() -> new IllegalArgumentException("Game doesn't exists. " + gameUuid));
        Player player = new Player(game, playerName);

        NewPlayerResponse message = new NewPlayerResponse(player);
        messageTemplate.sendMessage(message);

        return getGame(player, game);
    }


    public Collection<GameLink> getGamesList() {
        return GameRegistry.getPublicGames().stream()
                .filter(g -> g.currentTour == null)
                .filter(g -> !g.maxPlayers())
                .map(GameLink::new)
                .collect(Collectors.toSet());
    }

}
