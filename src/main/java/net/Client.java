package net;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import tank.TankFrame;

import java.awt.*;

public class Client {
    public  static  final  Client INSTANCE =new Client();
    Channel channel=null;
    EventLoopGroup e=new NioEventLoopGroup(1);
    private Client(){}
    public void connect(){
        try{
            channel =new Bootstrap()
                     .group(e)
                     .channel(NioSocketChannel.class)
                     .handler(new ClientChannelInitializer())
                     .connect("localhost",8888)
                    .addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            if(channelFuture.isSuccess()) {
                                System.out.println("connect.....");
                            }
                            else
                                System.out.println("not connect....");
                        }
                    }).sync().channel();

            channel.closeFuture().addListener(future -> {
                e.shutdownGracefully();
            });
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    public void send(Msg msg){
        System.out.println("SEND:"+msg.toString());
        channel.writeAndFlush(msg);
    }
}
class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
           ChannelPipeline pl=socketChannel.pipeline();
                 pl.addLast(new MsgDecoder())
                   .addLast(new MsgEncoder())
                   .addLast(new ClientHandler());
    }
}
class ClientHandler   extends  SimpleChannelInboundHandler<Msg>{

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Msg msg) throws Exception {
           msg.handle();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("new tank");
        ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMyTank()));
    }
}

