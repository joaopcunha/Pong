import java.awt.Color;
import java.awt.Graphics;

public class Player {
	
	public int x, y;
	public boolean right, left;
	public int width = 40, height = 5;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick() {
		
		if (right) {
			this.x+=2;
		}
		
		if (left) {
			this.x-=2;
		}
		
		if (x+width > Game.WIDTH) {
			x = Game.WIDTH - width;
		}
		
		if (x < 0) {
			x = 0;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(x, y, width, height);
	}

}
