package net;

import java.util.UUID;

public class TankDieMsg extends Msg{
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
