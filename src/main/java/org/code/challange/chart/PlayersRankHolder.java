package org.code.challange.chart;

import java.util.LinkedList;
import java.util.List;

import org.code.challange.model.Player;

public class PlayersRankHolder implements PlayersRanks
{
    private LinkedList<Player> ranks;

    public PlayersRankHolder()
    {
        ranks = new LinkedList();
    }

    public List<Player> getRanks()
    {
        return ranks;
    }

    public boolean removeFromRank(Player player)
    {
        return ranks.remove(player);
    }

    public void addToRanks(int index, Player player)
    {
        ranks.add(index,player);
    }

    public int countPlayersInRanks()
    {
        return ranks.size();
    }

    public void removeLastPlayer()
    {
        ranks.remove(ranks.size() - 1);
    }

    public List<Player> getBackupPlayers(Player player)
    {
        int playersRank = ranks.indexOf(player);
        return ranks.subList(playersRank + 1, ranks.size());
    }
}
