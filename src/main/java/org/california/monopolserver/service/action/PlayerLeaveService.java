package org.california.monopolserver.service.action;

import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.executable.transaction.VoluntaryTransaction;
import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.player.Session;
import org.california.monopolserver.instance.transferable.town.Town;
import org.california.monopolserver.instance.transferable.utility.Utility;
import org.california.monopolserver.model.ws_message.request.action.PlayerLeaveRequest;
import org.california.monopolserver.model.ws_message.response.player.PlayerLeaveResponse;
import org.california.monopolserver.service.CustomMessageTemplate;
import org.california.monopolserver.service.game.GameRegistry;
import org.california.monopolserver.service.transaction.result.TransactionCommitService;
import org.california.monopolserver.utils.annotations.RequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerLeaveService {

    private final TransactionCommitService commitService;
    private final CustomMessageTemplate messageTemplate;

    @Autowired
    public PlayerLeaveService(TransactionCommitService commitService, CustomMessageTemplate messageTemplate) {
        this.commitService = commitService;
        this.messageTemplate = messageTemplate;
    }

    @RequestProcessor(PlayerLeaveRequest.class)
    public void leave(PlayerLeaveRequest request) {
        Player player = GameRegistry.getPlayerByUuid(new Session(request.session).playerUuid);
        removePlayer(player);
    }


    public void removePlayer(Player player) {
        Game game = player.getGame();

        if(game.currentTour != null && game.currentTour.player.equals(player))
            return;

        if(game.players.size() <= 2) {
            abortGame(player);
            return;
        }

        setNextAdmin(game);
        sellAllPlayersProperties(player);
        game.players.remove(player);
        game.board.get(player).removePlayer(player);

        game.players.stream().map(p -> p.name).forEach(System.out::println);

        PlayerLeaveResponse response = new PlayerLeaveResponse(player, null, null, false);
        messageTemplate.sendMessage(response);
    }


    private void setNextAdmin(Game game) {
        game.admin = game.players.get(0);
    }


    private void sellAllPlayersProperties(Player player) {
        Transaction transaction = new VoluntaryTransaction(player, player.getGame().bank);

        player.properties.get(Town.class).forEach(t -> transaction.add(player, t));
        player.properties.get(Utility.class).forEach(t -> transaction.add(player, t));

        commitService.commitTransaction(transaction);
    }


    private void abortGame(Player player) {
        List<Player> players = player.getGame().players;
        players.remove(player);
        Player winner = players.isEmpty() ? null : players.get(0);
        Player loser  = player;
        GameRegistry.games.remove(player.getGame());
        PlayerLeaveResponse response = new PlayerLeaveResponse(player, winner, loser, true);
        messageTemplate.sendMessage(response);
    }


}
