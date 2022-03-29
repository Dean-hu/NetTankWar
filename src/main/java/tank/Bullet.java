package tank;

import com.sun.org.apache.bcel.internal.generic.InstructionConstants;
import lombok.Getter;
import lombok.Setter;
import net.Client;
import net.ExplodeNewMsg;
import net.TankDieMsg;

import java.awt.*;
import java.util.UUID;
@Getter
@Setter
public class Bullet {
     public static final int speed =10;
     private int num=0;
     private int x,y;
     private Direction dir;
     private boolean living=true;
     public static int WIDTH = ResourceMgr.bulletD.getWidth();
     public static int HEIGHT = ResourceMgr.bulletD.getHeight();
     private UUID playerId;
     private UUID id=UUID.randomUUID();
     private  TankFrame tf=TankFrame.INSTANCE;
     private static final int SPEED=10;
    Rectangle rect = new Rectangle();
    public Bullet(UUID playerId, int x, int y, Direction dir,int num) {
        this.x=x;
        this.y=y;
        this.playerId = playerId;
        this.dir = dir;
        rect.x = x;
        rect.y = y;
        rect.width = WIDTH;
        rect.height = HEIGHT;
        this.num=num;
    }
    public void collideWith(Tank tank) {
        if(tank.getId().equals(playerId)) return;
        if (this.living && tank.isLiving() && this.rect.intersects(tank.rect)) {
            tank.die();
            this.die();
            int bx =tank.getX() + Tank.WIDTH/2 - Explode.WIDTH/2;
            int by=tank.getY() + Tank.HEIGHT/2 - Explode.HEIGHT/2;
            TankFrame.INSTANCE.getExplodes().add(new Explode(bx,by));
            Client.INSTANCE.send(new ExplodeNewMsg(bx,by));
            Client.INSTANCE.send(new TankDieMsg(playerId,tank));
        }
    }


    private void die() {
        living=false;
        tf.getBullets().remove(this);
    }

    public void paint(Graphics g) {
        if(living)
        switch(dir) {
            case L: g.drawImage(ResourceMgr.bulletL, x, y, null);break;
            case U: g.drawImage(ResourceMgr.bulletU, x, y, null);break;
            case R: g.drawImage(ResourceMgr.bulletR, x, y, null);break;
            case D: g.drawImage(ResourceMgr.bulletD, x, y, null);break;
            case LU: g.drawImage(ResourceMgr.bulletLU, x, y, null);break;
            case RU: g.drawImage(ResourceMgr.bulletRU, x, y, null);break;
            case LD: g.drawImage(ResourceMgr.bulletLD, x, y, null);break;
            case RD: g.drawImage(ResourceMgr.bulletRD, x, y, null);break;
        }
        move();
    }

    private void move() {
            switch(dir) {
                case L: x -= SPEED;break;
                case U: y -= SPEED;break;
                case R: x += SPEED;break;
                case D: y += SPEED;break;
                case LU:x -= SPEED;y -= SPEED;break;
                case RU: x += SPEED;y -= SPEED;break;
                case RD: x += SPEED;y += SPEED;break;
                case LD: x -= SPEED;y += SPEED;break;
            }
            rect.x=this.x;
            rect.y=this.y;
      if(x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) die();
    }
}
