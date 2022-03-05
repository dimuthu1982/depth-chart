package org.code.challange.card;

import java.util.LinkedList;
import java.util.List;

import org.code.challange.model.Player;

public class PlayersRank
{
    private LinkedList<Player> ranks;

    public PlayersRank()
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

    public void addToTanks(int index, Player player)
    {
        ranks.add(index,player);
    }

    public int playersInRanks()
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
