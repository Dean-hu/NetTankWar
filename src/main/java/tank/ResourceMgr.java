package tank;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceMgr {
	public static BufferedImage goodTankL, goodTankU, goodTankR, goodTankD,
			goodTankLU,goodTankRU,goodTankLD,goodTankRD;

	public static BufferedImage bulletL, bulletU, bulletR, bulletD,
	                            bulletLU,bulletLD,bulletRU,bulletRD;

	public static BufferedImage[] explodes = new BufferedImage[16];
	public static BufferedImage[] squares =new BufferedImage[7];
 	
	static {
		try {
			goodTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
			goodTankL = ImageUtil.rotateImage(goodTankU, -90);
			goodTankR = ImageUtil.rotateImage(goodTankU, 90);
			goodTankD = ImageUtil.rotateImage(goodTankU, 180);
			goodTankLU = ImageUtil.rotateImage(goodTankU, -45);
			goodTankRU = ImageUtil.rotateImage(goodTankU, 45);
			goodTankLD = ImageUtil.rotateImage(goodTankU, -135);
			goodTankRD = ImageUtil.rotateImage(goodTankU, 135);

			bulletU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.png"));
			bulletL = ImageUtil.rotateImage(bulletU, -90);
			bulletR = ImageUtil.rotateImage(bulletU, 90);
			bulletD = ImageUtil.rotateImage(bulletU, 180);
			bulletLU = ImageUtil.rotateImage(bulletU, -45);
			bulletRU = ImageUtil.rotateImage(bulletU, 45);
			bulletLD = ImageUtil.rotateImage(bulletU, -135);
			bulletRD = ImageUtil.rotateImage(bulletU, 135);


			for(int i=0; i<16; i++) 
				explodes[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/e" + (i+1) + ".gif"));

			for(int i=0; i<7; i++)
				squares[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/square" + i + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
