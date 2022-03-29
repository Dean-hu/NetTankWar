package tank;

import java.awt.*;
import java.util.Random;

public class Wall {
    private int x, y;
    public static int WIDTH = ResourceMgr.squares[0].getWidth(),
            HEIGHT = ResourceMgr.squares[0].getHeight();
    private Rectangle rect = new Rectangle();

    public Wall(int x, int y) {
        this.x = x;
        this.y = y;
        rect.x = x;
        rect.y = y;
        rect.width = WIDTH;
        rect.height = HEIGHT;
    }

    Random r = new Random();

    void paint(Graphics g) {
        g.drawImage(ResourceMgr.squares[r.nextInt(7)], x, y, null);
    }

    void collideWithTank(Tank t) {
        if (t.isLiving() && rect.intersects(t.getRect())) {
            t.setX(t.getOldx());
            t.setY(t.getOldy());
        }
    }

    void collideWithBullet(Bullet b) {
        if (b.isLiving() && rect.intersects(b.getRect())) {
            b.setLiving(false);
        }
    }
}
