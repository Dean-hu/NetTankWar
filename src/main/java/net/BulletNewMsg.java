package net;

import tank.Bullet;
import tank.Direction;
import tank.Tank;
import tank.TankFrame;

import java.awt.*;
import java.io.*;
import java.util.UUID;

public class BulletNewMsg extends Msg{
    private int x,y;
    private Direction dir;
    private boolean living;
    private UUID playId;
    public  BulletNewMsg(Bullet b){
        living =b.isLiving();
        x=b.getX();
         y=b.getY();
         playId =b.getPlayerId();
         dir=b.getDir();
    }
    public BulletNewMsg(){}
    @Override
    public void handle() {
        //坦克是本机则不做处理
        if(playId.equals(TankFrame.INSTANCE.getMyTank().getId()))
                return;
        Bullet b=new Bullet(playId,x,y,dir,0);
        TankFrame.INSTANCE.getBullets().add(b);
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
            dos.writeBoolean(living);
            dos.writeLong(playId.getMostSignificantBits());
            dos.writeLong(playId.getLeastSignificantBits());
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
            living=dis.readBoolean();
            playId=new UUID(dis.readLong(),dis.readLong());
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
        return MsgType.BulletNew;
    }
}
