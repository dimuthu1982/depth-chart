package org.code.challange.service.card;

import java.util.List;

import org.code.challange.card.DepthCard;
import org.code.challange.exception.IlligalCardOperationException;
import org.code.challange.model.AddPlayer;
import org.code.challange.model.Backups;
import org.code.challange.model.CreatePlayer;
import org.code.challange.model.RemovePlayer;
import org.code.challange.service.SportDepthCardFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.code.challange.exception.IlligalCardOperationException.UNSUPPORTED_OPERATION;

@Service
public class CardOperationHandlerService implements CardController
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
    public void addPlayerToCard(String sport, List<AddPlayer> addPlayer)
    {
        getDepthCard(sport).addPlayerToCard(addPlayer);
    }

    @Override
    public List<String> getFullDepth(String sport)
    {
        return getDepthCard(sport).getFullDepth();
    }

    @Override
    public String removePlayerFromCard(String sport, RemovePlayer removePlayer)
    {
        return getDepthCard(sport).removePlayerFromCard(removePlayer);
    }

    @Override
    public List<String> getBackupPlayers(String sport, Backups backupsCommand)
    {
        return getDepthCard(sport).getBackupPlayers(backupsCommand);
    }

    private DepthCard getDepthCard(String sport)
    {
        DepthCard depthCard = sportDepthCardFactory.getDepthCard(sport);
        if (depthCard == null)
        {
            throw new IlligalCardOperationException(UNSUPPORTED_OPERATION, String.format("Unsupported sport type %s", sport));
        }
        return depthCard;
    }
}
