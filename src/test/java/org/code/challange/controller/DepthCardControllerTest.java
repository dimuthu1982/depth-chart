package org.code.challange.controller;

import java.util.ArrayList;
import java.util.List;

import org.code.challange.model.AddPlayer;
import org.code.challange.model.CreatePlayer;
import org.code.challange.model.RemovePlayer;
import org.code.challange.model.Result;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/*
Structure :
    Basket Ball (BB) Players:
        BB Player 1
        BB Player 2
        BB Player 3
        BB Player 4
    Positions and PLayers:
        FWD	1,3
        CTR	3,2,1
        SGRD 2

    NFL Players:
        NFL Player 1
        NFL Player 2
        NFL Player 3
    Positions and PLayers:
        QB	2,1
        RB	3,2
        C	1,2
 */

@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DepthCardControllerTest
{
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int randomServerPort;

    @Test
    @Order(1)
    void shouldReturnCreatedStatusWhenNFLPlayerSaved()
    {
        final String baseUrl = "http://localhost:" + randomServerPort + "/depth-card/NFL/player";

        CreatePlayer createPlayer = getPlayers("NFL", 1, 1).get(0);
        HttpEntity<CreatePlayer> request = new HttpEntity(createPlayer, new HttpHeaders());

        ResponseEntity<String> result = this.restTemplate.postForEntity(baseUrl, request, String.class);

        assertThat(201, is(result.getStatusCodeValue()));
    }

    @Test
    @Order(2)
    void shouldReturnCreatedStatusWhenNFLPlayersSaved()
    {
        final String baseUrl = "http://localhost:" + randomServerPort + "/depth-card/NFL/players";

        List<CreatePlayer> playerList = getPlayers("NFL", 2, 4);
        HttpEntity<CreatePlayer> request = new HttpEntity(playerList, new HttpHeaders());

        ResponseEntity<String> result = this.restTemplate.postForEntity(baseUrl, request, String.class);
        assertThat(201, is(result.getStatusCodeValue()));
    }

    @Test
    @Order(3)
    void shouldReturnCreatedStatusWhenPlayerAddedToNRLCard()
    {
        final String baseUrl = "http://localhost:" + randomServerPort + "/depth-card/NFL/players/addToCard";
        AddPlayer addPlayer1 = getAddPlayer(2, "QB", 1);
        AddPlayer addPlayer2 = getAddPlayer(1, "QB", 2);

        AddPlayer addPlayer3 = getAddPlayer(3, "RB", 1);
        AddPlayer addPlayer4 = getAddPlayer(2, "RB", 2);

        AddPlayer addPlayer5 = getAddPlayer(1, "C", 1);
        AddPlayer addPlayer6 = getAddPlayer(2, "C", 2);

        List<AddPlayer> addPlayers = new ArrayList();
        addPlayers.add(addPlayer1);
        addPlayers.add(addPlayer2);
        addPlayers.add(addPlayer3);
        addPlayers.add(addPlayer4);
        addPlayers.add(addPlayer5);
        addPlayers.add(addPlayer6);

        ResponseEntity<String> result = restTemplate.postForEntity(baseUrl, addPlayers, String.class);
        assertThat(200, is(result.getStatusCodeValue()));
    }

    @Test
    @Order(4)
    void shouldReturnBackupsForRBPositionPlayer3()
    {
        final String baseUrl = "http://localhost:" + randomServerPort + "/depth-card/NFL/player/3/position/RB/backups";

        ResponseEntity<Result> result = restTemplate.getForEntity(baseUrl, Result.class);

        List<String> backupPlayers = result.getBody().getResult();

        assertThat(200, is(result.getStatusCodeValue()));
        assertThat(1, is(backupPlayers.size()));
        assertThat("(#2, NFL Player 2)", is(backupPlayers.get(0)));
    }

    @Test
    @Order(1)
    void shouldReturnCreatedStatusWhenBBPlayersSaved()
    {
        final String baseUrl = "http://localhost:" + randomServerPort + "/depth-card/BB/players";

        List<CreatePlayer> players = getPlayers("BB", 1, 4);

        ResponseEntity<String> result = restTemplate.postForEntity(baseUrl, players, String.class);
        assertThat(201, is(result.getStatusCodeValue()));
    }

    @Test
    @Order(2)
    void shouldFailWhenAddingUndefinedPlayerToBBCard()
    {
        final String baseUrl = "http://localhost:" + randomServerPort + "/depth-card/BB/player/addToCard";

        AddPlayer addPlayer = getAddPlayer(100, "CTR", 3);

        List<AddPlayer> addPlayers = new ArrayList();
        addPlayers.add(addPlayer);

        ResponseEntity<String> result = restTemplate.postForEntity(baseUrl, addPlayers, String.class);
        assertThat(400, is(result.getStatusCodeValue()));
    }

    @Test
    @Order(3)
    void shouldFailWhenAddingPlayerToBBCardOutOfOrder()
    {
        final String baseUrl = "http://localhost:" + randomServerPort + "/depth-card/BB/player/addToCard";

        AddPlayer addPlayer = getAddPlayer(3, "CTR", 3);

        List<AddPlayer> addPlayers = new ArrayList();
        addPlayers.add(addPlayer);

        ResponseEntity<String> result = restTemplate.postForEntity(baseUrl, addPlayers, String.class);
        assertThat(400, is(result.getStatusCodeValue()));
    }

    @Test
    @Order(4)
    void shouldAddPlayersToCardWhenAddingPlayerToBBSequenceDepth()
    {
        final String baseUrl = "http://localhost:" + randomServerPort + "/depth-card/BB/players/addToCard";

        AddPlayer addPlayer1 = getAddPlayer(1, "FWD", 1);
        AddPlayer addPlayer2 = getAddPlayer(3, "FWD", 2);


        AddPlayer addPlayer3 = getAddPlayer(3, "CTR", 1);
        AddPlayer addPlayer4 = getAddPlayer(2, "CTR", 2);
        AddPlayer addPlayer5 = getAddPlayer(1, "CTR", 3);

        AddPlayer addPlayer6 = getAddPlayer(2, "SGRD", 1);

        List<AddPlayer> addPlayers = new ArrayList();
        addPlayers.add(addPlayer1);
        addPlayers.add(addPlayer2);
        addPlayers.add(addPlayer3);
        addPlayers.add(addPlayer4);
        addPlayers.add(addPlayer5);
        addPlayers.add(addPlayer6);

        ResponseEntity<String> result = restTemplate.postForEntity(baseUrl, addPlayers, String.class);
        assertThat(200, is(result.getStatusCodeValue()));
    }

    @Test
    @Order(5)
    void shouldFailWhenAddingPlayerToBBCardExceedingMaxDepth()
    {
        final String baseUrl = "http://localhost:" + randomServerPort + "/depth-card/BB/player/addToCard";
        AddPlayer addPlayer = getAddPlayer(4, "CTR", 4);
        ResponseEntity<String> result = restTemplate.postForEntity(baseUrl, addPlayer, String.class);
        assertThat(400, is(result.getStatusCodeValue()));
    }

    @Test
    @Order(6)
    void shouldReturnBackupsForBBPositionCTRPlayer3()
    {
        final String baseUrl = "http://localhost:" + randomServerPort + "/depth-card/BB/player/3/position/CTR/backups";

        ResponseEntity<Result> result = restTemplate.getForEntity(baseUrl, Result.class);

        List<String> backupPlayers = result.getBody().getResult();

        assertThat(200, is(result.getStatusCodeValue()));
        assertThat(2, is(backupPlayers.size()));
        assertThat("(#2, BB Player 2)", is(backupPlayers.get(0)));
        assertThat("(#1, BB Player 1)", is(backupPlayers.get(1)));
    }

    @Test
    @Order(7)
    void shouldReturnBlankWhenNoBackups()
    {
        final String baseUrl = "http://localhost:" + randomServerPort + "/depth-card/BB/player/1/position/CTR/backups";

        ResponseEntity<Result> result = restTemplate.getForEntity(baseUrl, Result.class);

        List<String> backupPlayers = result.getBody().getResult();

        assertThat(200, is(result.getStatusCodeValue()));
        assertThat(0, is(backupPlayers.size()));
    }

    @Test
    @Order(8)
    void shouldDeletePlayer2AndReturnBBPlayer()
    {
        final String baseUrl = "http://localhost:" + randomServerPort + "/depth-card/BB/player/removeFromCard";

        RemovePlayer removePlayer = new RemovePlayer();
        removePlayer.setPlayerId(2);
        removePlayer.setPosition("CTR");

        HttpEntity<RemovePlayer> request = new HttpEntity(removePlayer, new HttpHeaders());

        ResponseEntity<Result> result = restTemplate.exchange(baseUrl, HttpMethod.DELETE, request, Result.class);

        String deletedPlayer = result.getBody().getResult().get(0);

        assertThat(200, is(result.getStatusCodeValue()));
        assertThat("(#2, BB Player 2)", is(deletedPlayer));
    }

    @Test
    @Order(9)
    void shouldNotReturnPlayer2WhenBackupsForBBPositionCTRPlayer3()
    {
        final String baseUrl = "http://localhost:" + randomServerPort + "/depth-card/BB/player/3/position/CTR/backups";

        ResponseEntity<Result> result = restTemplate.getForEntity(baseUrl, Result.class);

        List<String> backupPlayers = result.getBody().getResult();

        assertThat(200, is(result.getStatusCodeValue()));
        assertThat(1, is(backupPlayers.size()));
        assertThat("(#1, BB Player 1)", is(backupPlayers.get(0)));
    }

    @Test
    @Order(10)
    void shouldShowFullDepthOfBB()
    {
        final String baseUrl = "http://localhost:" + randomServerPort + "/depth-card/BB/player/fullDepth";
        ResponseEntity<Result> result = restTemplate.getForEntity(baseUrl, Result.class);
        assertThat(200, is(result.getStatusCodeValue()));

        List<String> depth = result.getBody().getResult();


        assertThat(depth, IsIterableContainingInAnyOrder
            .containsInAnyOrder(
                "FWD - (#1, BB Player 1), (#3, BB Player 3)",
                "CTR - (#3, BB Player 3), (#1, BB Player 1)",
                "SGRD - (#2, BB Player 2)"));
    }

    private AddPlayer getAddPlayer(int playerId, String position, int depth)
    {
        AddPlayer addPlayer = new AddPlayer();
        addPlayer.setPlayerId(playerId);
        addPlayer.setPosition(position);
        addPlayer.setPositionInDepth(depth);
        return addPlayer;
    }

    private List<CreatePlayer> getPlayers(String sport, int startIndex, int endIndex)
    {
        CreatePlayer createPlayer;
        List<CreatePlayer> players = new ArrayList<>();

        for (int i = startIndex; i <= endIndex; i++)
        {
            createPlayer = new CreatePlayer();
            createPlayer.setName(sport + " Player " + i);
            createPlayer.setNumber(i);
            players.add(createPlayer);
        }
        return players;
    }
}