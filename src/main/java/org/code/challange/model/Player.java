package org.code.challange.model;

public class Player
{
    private int number;
    private String name;

    public Player(int number, String name)
    {
        this.number = number;
        this.name = name;
    }

    public int getNumber()
    {
        return number;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return String.format("(#%d, %s)", number, name);
    }
}
