package net;

public abstract class Msg {
    public  abstract void handle();
    public  abstract byte[] toByres();
    public  abstract void parse(byte[] bytes);
    public  abstract MsgType getMsgType();
}
