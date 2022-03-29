package net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Server {
       public static ChannelGroup Clients =new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
     public void ServerStart(){
         EventLoopGroup boss=new NioEventLoopGroup(1);
         EventLoopGroup worker=new NioEventLoopGroup(2);
         try {
             new ServerBootstrap()
                     .group(boss,worker)
                     .channel(NioServerSocketChannel.class)
                     .childHandler(new ChannelInitializer<SocketChannel>() {
                         @Override
                         protected void initChannel(SocketChannel ch) throws Exception {
                                  ch.pipeline()
                                          .addLast(new MsgEncoder())
                                          .addLast(new MsgDecoder())
                                          .addLast(new ServerChildHandle());
                         }
                     })
                     .bind(8888)
                     .sync();
             ServerFrame.INSTANCE.updateServerMsg("server started!");
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }
}
class ServerChildHandle extends ChannelInboundHandlerAdapter{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Server.Clients.add(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ServerFrame.INSTANCE.updateClientMsg(msg.toString());
        Server.Clients.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        Server.Clients.remove(ctx.channel());
        ctx.close();
    }
}
