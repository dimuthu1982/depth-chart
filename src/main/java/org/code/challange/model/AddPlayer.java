package org.code.challange.model;

public class AddPlayer
{
    private String position;
    private int playerId;
    private int positionInDepth;

    public String getPosition()
    {
        return position;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    public int getPlayerId()
    {
        return playerId;
    }

    public void setPlayerId(int playerId)
    {
        this.playerId = playerId;
    }

    public int getPositionInDepth()
    {
        return positionInDepth;
    }

    public void setPositionInDepth(int positionInDepth)
    {
        this.positionInDepth = positionInDepth;
    }
}
