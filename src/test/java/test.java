import net.*;
import org.junit.jupiter.api.Test;
import tank.ResourceMgr;
import tank.TankFrame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class test {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Msg msg=(Msg) Class.forName("net."+"BulletNew"+"Msg")
                .getDeclaredConstructor().newInstance();
        msg.handle();
    }
}
