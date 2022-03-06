package org.code.challange.service.card;

import java.util.List;

import org.code.challange.model.AddPlayer;
import org.code.challange.model.CreatePlayer;
import org.code.challange.model.PlayerPosition;

public interface CardOperationHandler
{
    /**
     * Creates a player in the respective sport context.
     *
     * @param sport  the players listed against.
     * @param player player to  be created.
     */
    void createPlayer(String sport, List<CreatePlayer> player);

    /**
     * Adds a player to the chart
     *
     * @param sport  the players listed against.
     * @param player added to the chart
     */
    void addPlayerToChart(String sport, List<AddPlayer> player);

    /**
     * Returns the full depth of the chart
     *
     * @param sport the players listed against.
     * @return chart of the positions and players lined up for it
     */
    List<String> getFullDepth(String sport);

    /**
     * Removes a player playing a specific sport from the chart.
     *
     * @param sport  the players listed against.
     * @param player to be removed
     * @return removed player, empty if no player found in the position
     */
    String removePlayerFromChart(String sport, PlayerPosition player);

    /**
     * Backup players of the given player
     *
     * @param sport  the players listed against.
     * @param player backups of the player
     * @return backups players or empty if no backups or player is not found in the position requested.
     */
    List<String> getBackupPlayers(String sport, PlayerPosition player);
}
