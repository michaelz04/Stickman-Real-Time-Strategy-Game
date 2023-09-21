import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Troop {

	protected int x;
	protected int y;
	protected int health;
	protected String name;
	protected boolean isPlayer;

	protected int damage;

	protected int xVelocity;
	protected int yVelocity;

	protected boolean isMoving;
	protected boolean isAttacking;

	protected int counter = 0;

	protected BufferedImage img1 = null;
	protected BufferedImage img2 = null;
	protected BufferedImage img3 = null;
	protected BufferedImage img4 = null;

	public Troop(int x, int y, int health, String name, boolean isPlayer) {

		this.x = x;
		this.y = y;
		this.health = health;
		this.name = name;
		this.isPlayer = isPlayer;

		xVelocity = 10;
		yVelocity = 5;

		damage = 1;

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public boolean getAttacking() {
		return isAttacking;
	}

	public void setAttacking(boolean isAttacking) {
		this.isAttacking = isAttacking;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setDead(int x, int y) {
		this.x = x;
		this.y = y;
		health = 10;
		isAttacking = false;
	}

	public void attack() {
		x += xVelocity;
		y -= yVelocity;
		isMoving = true;
	}

	public void retreat() {
		x -= xVelocity;
		y += yVelocity;
		isMoving = true;
	}

	public void hit() {
		isMoving = true;
	}

	public void stop() {
		isMoving = false;
	}

	public void drawTroop(Graphics g) {

	}
}
