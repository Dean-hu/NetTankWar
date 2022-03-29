package tank;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.DefaultByteBufHolder;
import net.Client;

import java.nio.charset.StandardCharsets;

public class TankMain {
    public static void main(String[] args) {
      TankFrame tf= TankFrame.INSTANCE;

      new Thread(()->new Audio("audio/war1.wav").loop()).start();

      new Thread(()->{
         while(true){
             try {
                 Thread.currentThread().sleep(25);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
             tf.repaint();
         }
      }).start();

      Client.INSTANCE.connect();
    }
}
