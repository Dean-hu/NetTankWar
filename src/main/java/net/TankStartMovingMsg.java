package net;

import tank.Direction;
import tank.Tank;
import tank.TankFrame;

import java.io.*;
import java.util.UUID;

public class TankStartMovingMsg extends Msg{

    UUID id;
    int x,y;
    Direction dir;
    public TankStartMovingMsg(){}
    public TankStartMovingMsg(Tank t){
        id=t.getId();
        x=t.getX();
        y=t.getY();
        dir=t.getDir();
    }
    @Override
    public void handle() {
        if(id.equals(TankFrame.INSTANCE.getMyTank().getId())
                ||TankFrame.INSTANCE.getTanks().get(id)==null)return;
        Tank t=TankFrame.INSTANCE.getTanks().get(id);
        t.setX(x);
        t.setY(y);
        t.setDir(dir);
        t.setMoving(true);

    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos=null;
        DataOutputStream dos=null;
        byte[] bytes=null;
        try {
            baos=new ByteArrayOutputStream();
            dos=new DataOutputStream(baos);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.flush();
            bytes=baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  bytes;
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis=new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            x=dis.readInt();
            y=dis.readInt();
            dir=Direction.values()[dis.readInt()];
            id=new UUID(dis.readLong(),dis.readLong());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankStartMoving;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName())
                .append("[")
                .append("uuid=" + id + " | ")
                .append("x=" + x + " | ")
                .append("y=" + y + " | ")
                .append("dir=" + dir + " | ")
                .append("]");
        return builder.toString();
    }
}
