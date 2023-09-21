import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Footman extends Troop {

	public Footman(int x, int y, int health, String name, boolean isPlayer) {
		super(x, y, health, name, isPlayer);
	}

	public void drawTroop(Graphics g) {
		try {
			if (isPlayer) {
				img1 = ImageIO.read(new File("footman1.png"));
				img2 = ImageIO.read(new File("footman2.png"));
				img3 = ImageIO.read(new File("footman3.png"));
				img4 = ImageIO.read(new File("footman4.png"));
			} else {
				img1 = ImageIO.read(new File("footman1e.png"));
				img2 = ImageIO.read(new File("footman2e.png"));
				img3 = ImageIO.read(new File("footman3e.png"));
				img4 = ImageIO.read(new File("footman4e.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (isMoving) {
			if (counter <= 50 || counter > 350 && counter <= 400) {
				g.drawImage(img1, x, y, null);
			} else if (counter > 50 && counter <= 100 || counter > 300 && counter <= 350) {
				g.drawImage(img2, x, y, null);
			} else if (counter > 100 && counter <= 150 || counter > 250 && counter <= 300) {
				g.drawImage(img3, x, y, null);
			} else {
				g.drawImage(img4, x, y, null);
			}
			if (counter == 400) {
				counter = 0;
			}
			counter += 10;

			g.setColor(Color.GREEN);
			g.fillRect(x + 200, y + 50, 100, 10);
			g.setColor(Color.RED);
			g.fillRect(x + 200, y + 50, 100 - health * 10, 10);
			g.setColor(Color.BLACK);
			g.drawString("Health: " + health, x + 200, y + 45);
			g.drawString(name, x + 200, y + 30);
		} else {
			g.drawImage(img1, x, y, null);

			g.setColor(Color.GREEN);
			g.fillRect(x + 200, y + 50, 100, 10);
			g.setColor(Color.RED);
			g.fillRect(x + 200, y + 50, 100 - health * 10, 10);
			g.setColor(Color.BLACK);
			g.drawString("Health: " + health, x + 200, y + 45);
			g.drawString(name, x + 200, y + 30);
		}

	}
}
