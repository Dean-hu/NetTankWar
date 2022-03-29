import org.junit.jupiter.api.Test;
import tank.ResourceMgr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class test {
    public static void main(String[] args) {
        try {
            BufferedImage goodTankU = ImageIO.read(ResourceMgr.class.getClassLoader()
                    .getResourceAsStream("images/GoodTank1.png"));
            assert goodTankU==null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
