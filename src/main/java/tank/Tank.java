package tank;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.UUID;

@Getter
@Setter
public class Tank {
    private int x,y;
    private static int SPEED=5;
    private boolean living=true;
    private boolean moving=false;
    private Direction dir=Direction.U;
    Rectangle rect=new Rectangle();
    TankFrame tf=null;
    UUID id=UUID.randomUUID();
    public static int WIDTH = ResourceMgr.goodTankU.getWidth();
    public static int HEIGHT = ResourceMgr.goodTankU.getHeight();
    public Tank(int x, int y, Direction dir,TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf=tf;

        rect.width=WIDTH;
        rect.height=HEIGHT;
        rect.x=x;
        rect.y=y;
        //加入坦克
        tf.getTanks().put(id,this);
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
        tf.getTanks().remove(id);
    }
    public void paint(Graphics g){
         //uuid on head
        Color c =g.getColor();
        g.setColor(Color.blue);
        g.drawString(id.toString(),x,y-20);
        g.drawString("live=" + living, x, y-10);
        g.setColor(c);
        if(living){
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
            rect.x=x;
            rect.y=y;
        }
    }

    public void fire() {
        int bX = this.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
        int bY = this.y + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
        Bullet b = new Bullet(this.id, bX, bY, this.dir, this.tf);
        tf.bullets.add(b);
       new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
    }
}
