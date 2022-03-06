package org.code.challange.service.card;

import java.util.List;

import org.code.challange.chart.RankingChart;
import org.code.challange.exception.UnsupportedChartOperationException;
import org.code.challange.model.AddPlayer;
import org.code.challange.model.CreatePlayer;
import org.code.challange.model.PlayerPosition;
import org.code.challange.service.SportDepthCardFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardOperationHandlerService implements CardOperationHandler
{
    private SportDepthCardFactory sportDepthCardFactory;

    @Autowired
    public CardOperationHandlerService(SportDepthCardFactory sportDepthCardFactory)
    {
        this.sportDepthCardFactory = sportDepthCardFactory;
    }

    @Override
    public void createPlayer(String sport, List<CreatePlayer> createPlayers)
    {
        getDepthCard(sport).createPlayer(createPlayers);
    }

    @Override
    public void addPlayerToChart(String sport, List<AddPlayer> addPlayer)
    {
        getDepthCard(sport).addPlayerToCard(addPlayer);
    }

    @Override
    public List<String> getFullDepth(String sport)
    {
        return getDepthCard(sport).getFullDepth();
    }

    @Override
    public String removePlayerFromChart(String sport, PlayerPosition playerPosition)
    {
        return getDepthCard(sport).removePlayerFromCard(playerPosition);
    }

    @Override
    public List<String> getBackupPlayers(String sport, PlayerPosition playerPosition)
    {
        return getDepthCard(sport).getBackupPlayers(playerPosition);
    }

    private RankingChart getDepthCard(String sport)
    {
        RankingChart depthCard = sportDepthCardFactory.getDepthCard(sport);
        if (depthCard == null)
        {
            throw new UnsupportedChartOperationException(String.format("Unsupported sport type %s", sport));
        }
        return depthCard;
    }
}
