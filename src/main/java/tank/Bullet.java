package tank;

import java.awt.*;
import java.util.UUID;

public class Bullet {
     public static final int speed =10;
     private int x,y;
     private Direction dir;
     private boolean living;
     public static int WIDTH = ResourceMgr.bulletD.getWidth();
     public static int HEIGHT = ResourceMgr.bulletD.getHeight();
     private UUID playerId;
     private  TankFrame tf;
     private static final int SPEED=10;
    Rectangle rect = new Rectangle();
    public Bullet(UUID playerId, int x, int y, Direction dir, TankFrame tf) {
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;

    }
    public void collideWith(Tank tank) {
        if(this.living && tank.isLiving() && this.rect.intersects(tank.rect)) {
            tank.die();
            this.die();
        }
    }

    private void die() {
        tf.getBullets().remove(this);
    }

    public void paint(Graphics g) {
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
            rect.x=x;
            rect.y=y;
      if(x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) die();
    }
}
