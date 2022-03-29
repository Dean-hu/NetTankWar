package tank;

import lombok.Getter;
import lombok.Setter;
import net.*;

import java.awt.*;
import java.util.UUID;

@Getter
@Setter
public class Tank {
    private int x,y,oldx,oldy;
    private static int SPEED=5;
    private boolean living=true;
    private boolean moving=false;
    private Direction dir;
    Rectangle rect=new Rectangle();
    UUID id=UUID.randomUUID();
    public static int WIDTH = ResourceMgr.goodTankU.getWidth();
    public static int HEIGHT = ResourceMgr.goodTankU.getHeight();

    public Tank(TankJoinMsg t){
        id=t.getId();
        x=t.getX();
        y=t.getY();
        dir=t.getDir();
        moving=t.isMoving();
        rect.x=x;
        rect.y=y;
        rect.width = WIDTH;
        rect.height = HEIGHT;
    }

    public Tank(int x, int y, Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        rect.x=x;
        rect.y=y;
        rect.width = WIDTH;
        rect.height = HEIGHT;
    }
    //边界判断
    private void boundsCheck() {
        if (this.x < 2) x = 2;
        if (this.y < 28) y = 28;
        if (this.x > TankFrame.GAME_WIDTH - Tank.WIDTH -2) x = TankFrame.GAME_WIDTH - Tank.WIDTH -2;
        if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT -2 ) y = TankFrame.GAME_HEIGHT -Tank.HEIGHT -2;
    }

    public void die() {
        living=false;
        TankFrame.INSTANCE.getTanks().remove(id);
    }
    public void paint(Graphics g){
         //uuid on head
        if(living){
        Color c =g.getColor();
        g.setColor(Color.blue);
        g.drawString(id.toString(),x,y-20);
        g.drawString("live=" + living, x, y-10);
        g.setColor(c);
            switch(dir) {
                case L: g.drawImage(ResourceMgr.goodTankL, x, y, null);break;
                case U: g.drawImage(ResourceMgr.goodTankU, x, y, null);break;
                case R: g.drawImage(ResourceMgr.goodTankR, x, y, null);break;
                case D: g.drawImage(ResourceMgr.goodTankD, x, y, null);break;
                case LU: g.drawImage(ResourceMgr.goodTankLU, x, y, null);break;
                case RU: g.drawImage(ResourceMgr.goodTankRU, x, y, null);break;
                case LD: g.drawImage(ResourceMgr.goodTankLD, x, y, null);break;
                case RD: g.drawImage(ResourceMgr.goodTankRD, x, y, null);break;
            }
            move();
        }
    }

    private void move() {
        oldx=x;
        oldy=y;
        if(moving){
            switch(dir){
                case L: x -= SPEED;break;
                case U: y -= SPEED;break;
                case R: x += SPEED;break;
                case D: y += SPEED;break;
                case LU: x -= SPEED;y -= SPEED;break;
                case RU: x += SPEED;y -= SPEED;break;
                case RD: x += SPEED;y += SPEED;break;
                case LD: x -= SPEED;y += SPEED;break;
            }
            boundsCheck();
            rect.x=this.x;
            rect.y=this.y;
        }
    }

    public void fire() {
        int bx =x + Tank.WIDTH/2 - Bullet.WIDTH/2;
        int by=y + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
        Bullet b = new Bullet(this.id, bx, by, this.dir,0);
        Client.INSTANCE.send(new BulletNewMsg(b));
        TankFrame.INSTANCE.bullets.add(b);
       new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
    }

    public void collideWith(Tank myTank) {
        if (this.living && myTank.isLiving() && this.rect.intersects(myTank.rect)) {
            x=oldx;
            y=oldy;
           myTank.setX(myTank.getOldx());
           myTank.setY(myTank.getOldy());
//           Client.INSTANCE.send(new TankStopMovingMsg(myTank));
        }
    }
}
