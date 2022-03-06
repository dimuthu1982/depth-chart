package org.code.challange.controller;

import java.util.Collections;
import java.util.List;

import org.code.challange.model.AddPlayer;
import org.code.challange.model.CreatePlayer;
import org.code.challange.model.PlayerPosition;
import org.code.challange.model.Result;
import org.code.challange.service.card.CardOperationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepthCardController
{
    private CardOperationHandler cardOperationHandler;

    @Autowired
    public DepthCardController(CardOperationHandler cardOperationHandler)
    {
        this.cardOperationHandler = cardOperationHandler;
    }

    @PostMapping("/depth-chart/{sport}/player")
    public ResponseEntity createPlayer(@PathVariable("sport") String sport, @RequestBody CreatePlayer createPlayer)
    {
        createPlayer(sport, Collections.singletonList(createPlayer));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/depth-chart/{sport}/players")
    public ResponseEntity createPlayers(@PathVariable("sport") String sport, @RequestBody List<CreatePlayer> createPlayer)
    {
        createPlayer(sport, createPlayer);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/depth-chart/{sport}/player/removeFromChart")
    public Result removePlayer(@PathVariable("sport") String sport, @RequestBody PlayerPosition removePlayer)
    {
        return new Result(cardOperationHandler.removePlayerFromChart(sport, removePlayer));
    }

    @PostMapping("/depth-chart/{sport}/players/addToChart")
    public ResponseEntity addPlayerToChart(@PathVariable("sport") String sport, @RequestBody List<AddPlayer> addPlayer)
    {
        cardOperationHandler.addPlayerToChart(sport, addPlayer);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/depth-chart/{sport}/player/addToChart")
    public ResponseEntity addPlayerToChart(@PathVariable("sport") String sport, @RequestBody AddPlayer addPlayer)
    {
        addPlayerToCard(sport, Collections.singletonList(addPlayer));
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/depth-chart/{sport}/player/{payerId}/position/{position}/backups", produces = "application/json")
    public Result getBackups(@PathVariable("sport") String sport, @PathVariable("payerId") int payerId, @PathVariable("position") String position)
    {
        PlayerPosition playerPosition = new PlayerPosition();
        playerPosition.setPlayerId(payerId);
        playerPosition.setPosition(position);
        return new Result(cardOperationHandler.getBackupPlayers(sport, playerPosition));
    }

    @GetMapping("/depth-chart/{sport}/player/fullDepth")
    public Result getFullDepthChart(@PathVariable("sport") String sport)
    {
        return new Result(cardOperationHandler.getFullDepth(sport));
    }

    private void createPlayer(String sport, List<CreatePlayer> createPlayer)
    {
        cardOperationHandler.createPlayer(sport, createPlayer);
    }

    private void addPlayerToCard(String sport, List<AddPlayer> addPlayers)
    {
        cardOperationHandler.addPlayerToChart(sport, addPlayers);
    }
}
