package net;

import tank.Direction;
import tank.Explode;
import tank.TankFrame;

import java.io.*;
import java.security.spec.MGF1ParameterSpec;
import java.util.UUID;

public class ExplodeNewMsg extends Msg {
    private  int x ,y;

    public ExplodeNewMsg(){}

    public ExplodeNewMsg(int x ,int y){
        this.x=x;
        this.y=y;
    }
    @Override
    public void handle() {
        TankFrame.INSTANCE.getExplodes().add(new Explode(x,y));
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
        return MsgType.ExplodeNew;
    }
}
