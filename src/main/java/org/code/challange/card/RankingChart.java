package org.code.challange.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.code.challange.exception.IlligalCardOperationException;
import org.code.challange.model.AddPlayer;
import org.code.challange.model.Backups;
import org.code.challange.model.CreatePlayer;
import org.code.challange.model.Player;
import org.code.challange.model.RemovePlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.code.challange.exception.IlligalCardOperationException.MAXIMUM_CARD_DEPTH_INCREASED;
import static org.code.challange.exception.IlligalCardOperationException.PLAYER_DOES_NOT_EXIST;
import static org.code.challange.exception.IlligalCardOperationException.UNSUPPORTED_OPERATION;

public class RankingChart
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RankingChart.class);

    private final Map<Integer, Player> playerRegister = new LinkedHashMap();

    private final Map<String, PlayersRank> sportPlayerRankRegister = new HashMap();

    private final String sport;
    private final int maximumCardDepth;

    public RankingChart(String sport, int maximumCardDepth)
    {
        this.sport = sport;
        this.maximumCardDepth = maximumCardDepth;
    }

    public void createPlayer(List<CreatePlayer> players)
    {
        for (CreatePlayer newPlayer : players)
        {
            if (playerRegister.containsKey(newPlayer.getNumber()))
            {
                LOGGER.info("{} - Player #{} - {} already created.", sport, newPlayer.getNumber(), newPlayer.getName());
                continue;
            }
            Player player = new Player(newPlayer.getNumber(), newPlayer.getName());
            playerRegister.put(player.getNumber(), player);

            LOGGER.info("{} - Player #{} - {} created.", sport, player.getNumber(), player.getName());
        }
    }

    public void addPlayerToCard(List<AddPlayer> addPlayers)
    {
        for (AddPlayer addPlayer : addPlayers)
        {
            if (addPlayer.getPositionInDepth() > maximumCardDepth)
            {
                throw new IlligalCardOperationException(MAXIMUM_CARD_DEPTH_INCREASED,
                    String.format("Maximum card depth supported is %d. Cannot add player to depth %d", maximumCardDepth,
                        addPlayer.getPositionInDepth()));

            }
            else if (!playerRegister.containsKey(addPlayer.getPlayerId()))
            {
                throw new IlligalCardOperationException(PLAYER_DOES_NOT_EXIST,
                    String.format("Unable to find a player by the id %d", addPlayer.getPlayerId()));
            }

            Player player = getPlayer(addPlayer.getPlayerId());
            sportPlayerRankRegister.putIfAbsent(addPlayer.getPosition(), new PlayersRank());
            PlayersRank ranks = sportPlayerRankRegister.get(addPlayer.getPosition());

            if (ranks.getRanks().size() < addPlayer.getPositionInDepth() - 1)
            {
                throw new IlligalCardOperationException(UNSUPPORTED_OPERATION,
                    String.format("Unable to add player %s (%d), Position %s needs to be filled sequentially. Index %d not accepted",
                        player.getName(), player.getNumber(), addPlayer.getPosition(), addPlayer.getPositionInDepth()));
            }

            addPlayerToCard(player, ranks, addPlayer.getPositionInDepth());
            LOGGER.info("{} - Player #{} - {} added to position {} ranked {}", sport, player.getNumber(), player.getName(), addPlayer.getPosition(),
                addPlayer.getPositionInDepth());
        }
    }

    private void addPlayerToCard(Player player, PlayersRank playersRank, int depth)
    {
        playersRank.removeFromRank(player);
        playersRank.addToTanks(depth - 1, player);
        if (playersRank.playersInRanks() > maximumCardDepth)
        {
            playersRank.removeLastPlayer();
        }
    }

    public String removePlayerFromCard(RemovePlayer removePlayerCommand)
    {
        PlayersRank playersRank = sportPlayerRankRegister.get(removePlayerCommand.getPosition());

        if (playersRank == null)
        {
            throw new IlligalCardOperationException(PLAYER_DOES_NOT_EXIST,
                String.format("No position configured by  %s", removePlayerCommand.getPosition()));
        }

        Player player = getPlayer(removePlayerCommand.getPlayerId());
        if (playersRank.removeFromRank(player))
        {
            LOGGER.info("{} - Player #{} - {} removed from the card", sport, player.getNumber(), player.getName());
            return player.toString();
        }
        return null;
    }


    public List<String> getFullDepth()
    {
        List<String> path = new ArrayList(sportPlayerRankRegister.keySet().size());
        String depth;

        for (Map.Entry<String, PlayersRank> entry : sportPlayerRankRegister.entrySet())
        {
            depth = getFormattedPayers(entry.getValue().getRanks());
            path.add(String.format("%s - %s", entry.getKey(), depth));
        }
        return path;
    }

    public List<String> getBackupPlayers(Backups backupsCommand)
    {
        Player player = getPlayer(backupsCommand.getPlayerId());

        if (player == null)
        {
            return Collections.emptyList();
        }

        PlayersRank playersRank = sportPlayerRankRegister.get(backupsCommand.getPosition());
        if (playersRank == null)
        {
            return Collections.emptyList();
        }
        return playersRank.getBackupPlayers(player).stream().map(Player::toString).collect(Collectors.toList());
    }

    private Player getPlayer(int playerId)
    {
        return playerRegister.get(playerId);
    }

    private String getFormattedPayers(List<Player> players)
    {
        return players.stream()
            .map(Player::toString)
            .collect(Collectors.joining(", "));
    }
}
