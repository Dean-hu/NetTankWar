package net;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import lombok.Getter;
import sun.plugin.dom.DOMObject;
import tank.Direction;
import tank.Tank;
import tank.TankFrame;

import java.awt.geom.AffineTransform;
import java.io.*;
import java.util.UUID;
@Getter
public class TankJoinMsg extends Msg{
    private int x , y;
    Direction dir;
    UUID id;
    public boolean moving;
    //发送TankJoinMsg时需要用到的
    public TankJoinMsg(Tank t){
        x=t.getX();
        y=t.getY();
        dir=t.getDir();
        moving=t.isMoving();
        id=t.getId();
    }
    public TankJoinMsg(){}

    @Override
    public void handle() {
       if(id.equals(TankFrame.INSTANCE.getMyTank().getId())||
         TankFrame.INSTANCE.getTanks().get(this.id)!=null)
               return;
         Tank t =new Tank(this);
         //如果接收到别人坦克的信息，需要把自己的信息发给别人更新
         TankFrame.INSTANCE.getTanks().put(id,t);
         if(TankFrame.INSTANCE.getMyTank().isLiving())
         Client.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getMyTank()));
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
            dos.writeBoolean(moving);
            System.out.println(id);
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
            moving=dis.readBoolean();
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
        return MsgType.TankJoin;
    }

}
