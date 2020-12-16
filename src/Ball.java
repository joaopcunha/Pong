import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball {
	
	public double x, y, width, height;
	public double dx, dy;
	public double speed = 1.5;
	
	public Ball(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 4;
		this.height = 4;
		
		this.dx = this.getRandomHorizontal();
		this.dy = this.getRandomVertical();
		
	}
	
	public double getRandomHorizontal() {
		int angle = new Random().nextInt(125-45) + 45;
		return Math.cos(Math.toRadians(angle));
	}
	
	public double getRandomVertical() {
		int angle = new Random().nextInt(125-45) + 45;
		return Math.sin(Math.toRadians(angle));
	}

	public void tick() {
		
		if (x+(dx*speed)+width >= Game.WIDTH) {
			dx*=-1;
		} else if (x+(dx*speed) < 0) {
			dx*=-1;
		}
		
		if (y >= Game.HEIGHT) {
			Game.score.addUpScore();
			new Game();
			return;
		} else if (y < 0) {
			Game.score.addDownScore();
			new Game();
			return;
		}
		
		Rectangle bounds = new Rectangle((int)(x+dx*speed), (int) (y+dy*speed), (int)width, (int)height);
		Rectangle boundsPlayer = new Rectangle(Game.player.x, Game.player.y, Game.player.width, Game.player.height);
		Rectangle boundsPlayer2 = new Rectangle(Game.player2.x, Game.player2.y, Game.player2.width, Game.player2.height);
		Rectangle boundsEnemy = new Rectangle((int)Game.enemy.x, (int)Game.enemy.y, (int)Game.enemy.width, (int)Game.enemy.height);
		
		if(bounds.intersects(boundsPlayer)) {
			dx=this.getRandomHorizontal();
			dy*=-1;
		} else if (bounds.intersects(boundsPlayer2)) {
			dx=this.getRandomHorizontal();
			dy*=-1;
		} else if (bounds.intersects(boundsEnemy)) {
			dx=this.getRandomHorizontal();
			dy*=-1;
		}
		
		x+=dx*speed;
		y+=dy*speed;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect((int)x, (int)y, (int)width, (int)height);
	}

}
