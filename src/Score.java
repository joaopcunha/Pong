import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class Score {
	
	private int upScore = 0, downScore = 0;
	private int maxScore = 10;
	
	public void addUpScore() {
		this.upScore++;
	}
	
	public void addDownScore() {
		this.downScore++;
	}
	
	public void resetScore() {
		upScore = 0;
		downScore = 0;
	}
	
	public void tick() {
		if (downScore >= maxScore || upScore >= maxScore) {
			Game.gameState = "gameover";
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 40));
		g.drawString(Integer.toString(upScore), (Game.WIDTH*Game.SCALE/2)-440, (Game.HEIGHT*Game.SCALE/2)-220);
		g.drawString(Integer.toString(downScore), (Game.WIDTH*Game.SCALE/2)-440, (Game.HEIGHT*Game.SCALE/2)+220);
	}
	
	public void renderGameOver(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0, 0, 0, 100));
		g2.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.setFont(new Font("arial", Font.BOLD, 36));
		g.setColor(Color.white);
		g.drawString("Game Over", (Game.WIDTH*Game.SCALE/2)-130, (Game.HEIGHT*Game.SCALE/2)-28);
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.drawString("Final score is down: "+downScore+" up: "+upScore, (Game.WIDTH*Game.SCALE/2)-130, (Game.HEIGHT*Game.SCALE/2)+10);
		g.drawString("Press space to restart", (Game.WIDTH*Game.SCALE/2)-130, (Game.HEIGHT*Game.SCALE/2)+40);
	}

}
