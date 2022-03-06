package org.code.challange.chart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.code.challange.exception.IllegalChartOperationException;
import org.code.challange.model.AddPlayer;
import org.code.challange.model.CreatePlayer;
import org.code.challange.model.Player;
import org.code.challange.model.PlayerPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RankingChartHolder implements RankingChart
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RankingChartHolder.class);

    private final Map<Integer, Player> playerRegister = new LinkedHashMap();

    private final Map<String, PlayersRanks> playersRankRegister = new HashMap();

    private final String sport;

    private final int maximumChartDepth;

    public RankingChartHolder(String sport, int maximumChartDepth)
    {
        this.sport = sport;
        this.maximumChartDepth = maximumChartDepth;
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
            if (addPlayer.getPositionInDepth() > maximumChartDepth)
            {
                throw new IllegalChartOperationException(String.format("Maximum card depth supported is %d. Cannot add player to depth %d",
                    maximumChartDepth,
                    addPlayer.getPositionInDepth()));

            }
            else if (!playerRegister.containsKey(addPlayer.getPlayerId()))
            {
                throw new IllegalChartOperationException(String.format("Unable to find a player by the id %d", addPlayer.getPlayerId()));
            }

            Player player = getPlayer(addPlayer.getPlayerId());
            playersRankRegister.putIfAbsent(addPlayer.getPosition(), new PlayersRankHolder());
            PlayersRanks ranks = playersRankRegister.get(addPlayer.getPosition());

            if (ranks.getRanks().size() < addPlayer.getPositionInDepth() - 1)
            {
                throw new IllegalChartOperationException(
                    String.format("Unable to add player %s (%d), Position %s needs to be filled sequentially. Index %d not accepted",
                        player.getName(), player.getNumber(), addPlayer.getPosition(), addPlayer.getPositionInDepth()));
            }

            addPlayerToCard(player, ranks, addPlayer.getPositionInDepth());
            LOGGER.info("{} - Player #{} - {} added to position {} ranked {}", sport, player.getNumber(), player.getName(), addPlayer.getPosition(),
                addPlayer.getPositionInDepth());
        }
    }

    private void addPlayerToCard(Player player, PlayersRanks playersRank, int depth)
    {
        playersRank.removeFromRank(player);
        playersRank.addToRanks(depth - 1, player);
        if (playersRank.countPlayersInRanks() > maximumChartDepth)
        {
            playersRank.removeLastPlayer();
        }
    }

    public String removePlayerFromCard(PlayerPosition playerPosition)
    {
        PlayersRanks playersRank = playersRankRegister.get(playerPosition.getPosition());

        if (playersRank == null)
        {
            throw new IllegalChartOperationException(String.format("No position configured by  %s", playerPosition.getPosition()));
        }

        Player player = getPlayer(playerPosition.getPlayerId());
        if (playersRank.removeFromRank(player))
        {
            LOGGER.info("{} - Player #{} - {} removed from the card", sport, player.getNumber(), player.getName());
            return player.toString();
        }
        return null;
    }


    public List<String> getFullDepth()
    {
        List<String> path = new ArrayList(playersRankRegister.keySet().size());
        String depth;

        for (Map.Entry<String, PlayersRanks> entry : playersRankRegister.entrySet())
        {
            depth = getFormattedPayers(entry.getValue().getRanks());
            path.add(String.format("%s - %s", entry.getKey(), depth));
        }
        return path;
    }

    public List<String> getBackupPlayers(PlayerPosition playerPosition)
    {
        Player player = getPlayer(playerPosition.getPlayerId());

        if (player == null)
        {
            return Collections.emptyList();
        }

        PlayersRanks playersRank = playersRankRegister.get(playerPosition.getPosition());
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
