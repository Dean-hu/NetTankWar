package net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
             if(in.readableBytes()<8) return;
               in.markReaderIndex();
               //解码消息类型
           MsgType msgType=MsgType.values()[in.readInt()];
     int length=in.readInt();
      if(in.readableBytes()<length){
          in.resetReaderIndex();
          return;
      }
      byte[] bytes=new byte[length];
      in.readBytes(bytes);
      Msg msg=null;
        System.out.println("net."+msgType.toString()+"Msg");
      msg=(Msg)Class.forName("net."+msgType.toString()+"Msg")
              .getDeclaredConstructor().newInstance();
      msg.parse(bytes);
      out.add(msg);
    }
}
