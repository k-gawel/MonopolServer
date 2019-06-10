package org.california.monopolserver.service.game;

import org.california.monopolserver.exceptions.PlayerNotFoundException;
import org.california.monopolserver.exceptions.TourException;
import org.california.monopolserver.instance.executable.tour.Tour;
import org.california.monopolserver.instance.executable.tour.TourBuilder;
import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.player.Session;
import org.california.monopolserver.model.ws_message.request.action.EndTourRequest;
import org.california.monopolserver.model.ws_message.response.game.NewTourResponse;
import org.california.monopolserver.service.CustomMessageTemplate;
import org.california.monopolserver.utils.annotations.RequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourService {

    private final CustomMessageTemplate messageTemplate;

    @Autowired
    public TourService(CustomMessageTemplate messageTemplate) {
        this.messageTemplate = messageTemplate;
    }



    @RequestProcessor(EndTourRequest.class)
    public void init(EndTourRequest request) throws IllegalAccessException, PlayerNotFoundException {
        Session session = new Session(request.session);
        Player player = GameRegistry.getPlayerByUuid(session.playerUuid);
        Game game = player.getGame();

        Tour currentTour = game.currentTour;
        if(currentTour == null)
            startGame(player, game);
        else
            endTour(player, game);
    }


    private void startGame(Player player, Game game) throws IllegalAccessException {
        if(!game.players.get(0).equals(player))
            throw new IllegalAccessException("Game admin must start game.");
        if(game.players.size() <= 1)
            throw new IllegalStateException("You need at least two players to start the game.");
        createAndSend(game);
    }


    private void endTour(Player player, Game game) throws IllegalAccessException {
        if(!game.currentTour.player.equals(player))
            throw new TourException(player);
        createAndSend(game);
    }


    private void createAndSend(Game game) {
        Tour tour = TourBuilder.nexTour(game);
        NewTourResponse response = new NewTourResponse(tour);
        messageTemplate.sendMessage(response);
    }


    private void forcedEndTour(Game game) {

    }

}
