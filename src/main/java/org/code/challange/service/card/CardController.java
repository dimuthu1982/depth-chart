package org.code.challange.service.card;

import java.util.List;

import org.code.challange.model.AddPlayer;
import org.code.challange.model.Backups;
import org.code.challange.model.CreatePlayer;
import org.code.challange.model.RemovePlayer;

public interface CardController
{
    void createPlayer(String sport, List<CreatePlayer> createPlayer);

    void addPlayerToCard(String sport, List<AddPlayer> addPlayer);

    List<String> getFullDepth(String sport);

    String removePlayerFromCard(String sport, RemovePlayer removePlayer);

    List<String> getBackupPlayers(String sport, Backups backupsCommand);
}
