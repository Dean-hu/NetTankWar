package tank;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
@Setter
@Getter
public class TankFrame extends Frame {
    public static TankFrame INSTANCE = new TankFrame();
    static final int GAME_WIDTH = 1000, GAME_HEIGHT = 800;

    List<Explode> explodes=new ArrayList<Explode>();
    List<Bullet>   bullets=new ArrayList<Bullet>();
    Map<UUID,Tank> tanks=new HashMap<>();
    Random r=new Random();
    Tank MyTank=new Tank(r.nextInt(800),r.nextInt(800),Direction.U,this);

    private TankFrame() {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setVisible(true);
        setResizable(false);
        setTitle("NetTankWar");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.addKeyListener(new MyKeyListener());
    }

    Image offScreenImage = null;
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }
    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("bullets:" + bullets.size(), 10, 60);
        g.drawString("tanks:" + tanks.size(), 10, 80);
        g.drawString("explodes" + explodes.size(), 10, 100);
        g.setColor(c);
//        MyTank.paint(g);
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }
        //java8 stream api
        tanks.values().stream().forEach((e)->e.paint(g));

        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }
        //collision detect
        Collection<Tank> values = tanks.values();
        for(int i=0; i<bullets.size(); i++) {
            for(Tank t : values )
                bullets.get(i).collideWith(t);
        }
    }



    private class MyKeyListener extends KeyAdapter {
        boolean bL = false;
        boolean bU = false;
        boolean bR = false;
        boolean bD = false;

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT: {
                    bL = true;
                    break;
                }
                case KeyEvent.VK_UP: {
                    bU = true;
                    break;
                }
                case KeyEvent.VK_DOWN: {
                    bD = true;
                    break;
                }
                case KeyEvent.VK_RIGHT: {
                    bR = true;
                    break;
                }
            }
            setMainTankDri();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT: {
                    bL = false;
                    break;
                }
                case KeyEvent.VK_UP: {
                    bU = false;
                    break;
                }
                case KeyEvent.VK_DOWN: {
                    bD = false;
                    break;
                }
                case KeyEvent.VK_RIGHT: {
                    bR = false;
                    break;
                }
                case KeyEvent.VK_CONTROL: MyTank.fire();break;
            }
            setMainTankDri();
        }

        private void setMainTankDri() {
            if (!bL && !bR && !bU && !bD)
                MyTank.setMoving(false);
            else {
                MyTank.setMoving(true);
                Direction d = MyTank.getDir();
                if (bL) MyTank.setDir(Direction.L);
                if (bR) MyTank.setDir(Direction.R);
                if (bU) MyTank.setDir(Direction.U);
                if (bD) MyTank.setDir(Direction.D);
                if (bD && bU) MyTank.setMoving(false);
                if (bD && bL) MyTank.setDir(Direction.LD);
                if (bU && bL) MyTank.setDir(Direction.LU);
                if (bD && bR) MyTank.setDir(Direction.RD);
                if (bU && bR) MyTank.setDir(Direction.RU);
            }
        }

    }
}

