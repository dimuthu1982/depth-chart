package org.code.challange.chart;

import java.util.List;

import org.code.challange.model.AddPlayer;
import org.code.challange.model.CreatePlayer;
import org.code.challange.model.PlayerPosition;

/**
 * Holds the chard for the players.
 */
public interface RankingChart
{
    /**
     * Create players
     * @param players to be created.
     */
    void createPlayer(List<CreatePlayer> players);

    /**
     * Add players to the chart
     * @param addPlayers to be added to the chart and to the position.
     */
    void addPlayerToCard(List<AddPlayer> addPlayers);

    /**
     * Remove player from the chart
     * @param playerPosition player and his position
     * @return removed players number and name.
     */
    String removePlayerFromCard(PlayerPosition playerPosition);

    /**
     * Returns the players along with the positions they play.
     * @return positions and their players lined up.
     */
    List<String> getFullDepth();

    /**
     * Backup players to the player provided.
     * @param playerPosition players backups
     * @return backup players name and numbers if has any, empty otherwise.
     */
    List<String> getBackupPlayers(PlayerPosition playerPosition);
}
