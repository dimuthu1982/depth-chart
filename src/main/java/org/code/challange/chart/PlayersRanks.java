package org.code.challange.chart;

import java.util.List;

import org.code.challange.model.Player;

/**
 * Holds the players ranks
 */
public interface PlayersRanks
{
    /**
     * Returns all the players
     * @return a list of all the players.
     */
    List<Player> getRanks();

    /**
     * Removed a player from the rank
     * @param player to be removed from ranks
     * @return true if the player found in the ranks, false otherwise
     */
    boolean removeFromRank(Player player);

    /**
     * Add players to the given rank
     * @param index rank number
     * @param player to be added to the rank
     */
    void addToRanks(int index, Player player);

    /**
     * Return number of players in the rank.
     * @return number of players in the rank
     */
    int countPlayersInRanks();

    /**
     * Removes the last player in the rank
     */
    void removeLastPlayer();

    /**
     * Returns players lined up after the given player.
     * @param player player backups look for
     * @return subsequent players to the given player.
     */
    List<Player> getBackupPlayers(Player player);
}
