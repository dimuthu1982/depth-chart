package org.code.challange.exception;

public class IlligalCardOperationException extends RuntimeException
{
    public static final String PLAYER_DOES_NOT_EXIST = "PLAYER_DOES_NOT_EXIST";
    public static final String MAXIMUM_CARD_DEPTH_INCREASED = "MAXIMUM_CARD_DEPTH_INCREASED";
    public static final String UNSUPPORTED_OPERATION = "UNSUPPORTED_OPERATION";

    private final String type;

    public IlligalCardOperationException(String type, String msg)
    {
        super(msg);
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
}
