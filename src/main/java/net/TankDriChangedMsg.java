package net;

import tank.Direction;
import tank.Tank;
import tank.TankFrame;

import java.io.*;
import java.util.UUID;

public class TankDriChangedMsg extends Msg{
    public int x ,y;
    Direction dir;
    UUID id;
    public TankDriChangedMsg(Tank t){
        dir=t.getDir();
        id=t.getId();
    }

    public TankDriChangedMsg(){}

    @Override
    public void handle() {
        if(id.equals(TankFrame.INSTANCE.getMyTank().getId())||
                TankFrame.INSTANCE.getTanks().get(this.id)==null)
            return;
      Tank t= TankFrame.INSTANCE.getTanks().get(this.id);
      System.out.println(dir.toString());
      t.setDir(dir);
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos=null;
        DataOutputStream dos=null;
        byte[] bytes=null;
        try {
            baos=new ByteArrayOutputStream();
            dos=new DataOutputStream(baos);
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
        return MsgType.TankDriChanged;
    }
}
