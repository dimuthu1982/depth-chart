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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/depth-chart/{sport}")
public class DepthCardController
{
    private CardOperationHandler cardOperationHandler;

    @Autowired
    public DepthCardController(CardOperationHandler cardOperationHandler)
    {
        this.cardOperationHandler = cardOperationHandler;
    }

    @PostMapping("/player")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPlayer(@PathVariable("sport") String sport, @RequestBody CreatePlayer createPlayer)
    {
        createPlayer(sport, Collections.singletonList(createPlayer));
    }

    @PostMapping("/players")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPlayers(@PathVariable("sport") String sport, @RequestBody List<CreatePlayer> createPlayer)
    {
        createPlayer(sport, createPlayer);
    }

    @DeleteMapping("/player/removeFromChart")
    public Result removePlayer(@PathVariable("sport") String sport, @RequestBody PlayerPosition removePlayer)
    {
        return new Result(cardOperationHandler.removePlayerFromChart(sport, removePlayer));
    }

    @PostMapping("/players/addToChart")
    @ResponseStatus(HttpStatus.OK)
    public void addPlayerToChart(@PathVariable("sport") String sport, @RequestBody List<AddPlayer> addPlayer)
    {
        cardOperationHandler.addPlayerToChart(sport, addPlayer);
    }

    @PostMapping("/player/addToChart")
    @ResponseStatus(HttpStatus.OK)
    public void addPlayerToChart(@PathVariable("sport") String sport, @RequestBody AddPlayer addPlayer)
    {
        addPlayerToCard(sport, Collections.singletonList(addPlayer));
    }

    @GetMapping(value = "/player/{payerId}/position/{position}/backups", produces = "application/json")
    public Result getBackups(@PathVariable("sport") String sport, @PathVariable("payerId") int payerId, @PathVariable("position") String position)
    {
        PlayerPosition playerPosition = new PlayerPosition();
        playerPosition.setPlayerId(payerId);
        playerPosition.setPosition(position);
        return new Result(cardOperationHandler.getBackupPlayers(sport, playerPosition));
    }

    @GetMapping("/player/fullDepth")
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
