package net;

import tank.Bullet;
import tank.Direction;
import tank.Tank;
import tank.TankFrame;

import java.io.*;
import java.util.UUID;

public class TankDieMsg extends Msg{

    private  UUID id;
    private UUID playId;
    public  TankDieMsg(){}

    public  TankDieMsg(UUID playId,Tank t){
        id=t.getId();
        this.playId=playId;
    }

    @Override
    public void handle() {
        Bullet b=TankFrame.INSTANCE.findBulletByUUID(playId);
         if(b!=null)
             TankFrame.INSTANCE.getBullets().remove(b);
         System.out.println("死亡id为"+id);
         if(id.equals(TankFrame.INSTANCE.getMyTank().getId()))
        {
             TankFrame.INSTANCE.getMyTank().setLiving(false);
        }
        else {
            if(TankFrame.INSTANCE.getTanks().get(id)==null)
                        return;
            Tank t = TankFrame.INSTANCE.getTanks().get(id);
             t.die();
        }

    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos=null;
        DataOutputStream dos=null;
        byte[] bytes=null;
        try {
            baos=new ByteArrayOutputStream();
            dos=new DataOutputStream(baos);
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
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
            id=new UUID(dis.readLong(),dis.readLong());
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
        return MsgType.TankDie;
    }
}
