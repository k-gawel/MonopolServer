package org.california.monopolserver.controller;

import org.california.monopolserver.exceptions.GameException;
import org.california.monopolserver.service.game.NewPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@org.springframework.web.bind.annotation.RestController
@CrossOrigin
public class RestController {


    private final NewPlayerService newPlayerService;


    @GetMapping("/sample-controller")
    public String xd() {
        return "XDDD";
    }


    @Autowired
    public RestController(NewPlayerService newPlayerService) {
        this.newPlayerService = newPlayerService;
    }


    @GetMapping("/games-list")
    public ResponseEntity getGamesList() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(newPlayerService.getGamesList());
    }


    @GetMapping("/game/get")
    public ResponseEntity getGame(@RequestParam("session") String session) throws GameException {

        try {
            Object result = newPlayerService.getGame(session);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(result);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }

    }


    @PostMapping("/game/join")
    public ResponseEntity joinGame(@RequestParam("game_uuid") String gameUuid,
                                   @RequestParam("player_name") String playerName) throws GameException {

        Object result = newPlayerService.joinGame(gameUuid, playerName);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @PostMapping("/game/new")
    public ResponseEntity newGame(@RequestParam("player_name") String playerName) throws GameException {
        Object result = newPlayerService.createGame(playerName);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }


}
