package net;

public class TankJoinMsg extends Msg{
    @Override
    public void handle() {

    }

    @Override
    public byte[] toByres() {
        return new byte[0];
    }

    @Override
    public void parse(byte[] bytes) {

    }

    @Override
    public MsgType getMsgType() {
        return null;
    }
}
