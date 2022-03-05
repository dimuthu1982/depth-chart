package org.code.challange.controller;

import java.util.Collections;
import java.util.List;

import org.code.challange.model.AddPlayer;
import org.code.challange.model.Backups;
import org.code.challange.model.CreatePlayer;
import org.code.challange.model.RemovePlayer;
import org.code.challange.model.Result;
import org.code.challange.service.card.CardOperationHandlerService;
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
    private CardOperationHandlerService cardControllerService;

    @Autowired
    public DepthCardController(CardOperationHandlerService cardControllerService)
    {
        this.cardControllerService = cardControllerService;
    }

    @PostMapping("/depth-card/{sport}/player")
    public ResponseEntity createPlayer(@PathVariable("sport") String sport, @RequestBody CreatePlayer createPlayer)
    {
        createPlayer(sport, Collections.singletonList(createPlayer));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/depth-card/{sport}/players")
    public ResponseEntity createPlayers(@PathVariable("sport") String sport, @RequestBody List<CreatePlayer> createPlayer)
    {
        createPlayer(sport, createPlayer);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/depth-card/{sport}/player/removeFromCard")
    public Result removePlayer(@PathVariable("sport") String sport, @RequestBody RemovePlayer removePlayer)
    {
        return new Result(cardControllerService.removePlayerFromCard(sport, removePlayer));
    }

    @PostMapping("/depth-card/{sport}/players/addToCard")
    public ResponseEntity addPlayerToChart(@PathVariable("sport") String sport, @RequestBody List<AddPlayer> addPlayer)
    {
        cardControllerService.addPlayerToCard(sport, addPlayer);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/depth-card/{sport}/player/addToCard")
    public ResponseEntity addPlayerToChart(@PathVariable("sport") String sport, @RequestBody AddPlayer addPlayer)
    {
        addPlayerToCard(sport, Collections.singletonList(addPlayer));
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/depth-card/{sport}/player/{payerId}/position/{position}/backups", produces = "application/json")
    public Result getBackups(@PathVariable("sport") String sport, @PathVariable("payerId") int payerId, @PathVariable("position") String position)
    {
        Backups backup = new Backups();
        backup.setPlayerId(payerId);
        backup.setPosition(position);
        return new Result(cardControllerService.getBackupPlayers(sport, backup));
    }

    @GetMapping("/depth-card/{sport}/player/fullDepth")
    public Result getFullDepthChart(@PathVariable("sport") String sport)
    {
        return new Result(cardControllerService.getFullDepth(sport));
    }

    private void createPlayer(String sport, List<CreatePlayer> createPlayer)
    {
        cardControllerService.createPlayer(sport, createPlayer);
    }

    private void addPlayerToCard(String sport, List<AddPlayer> addPlayers)
    {
        cardControllerService.addPlayerToCard(sport, addPlayers);
    }
}
