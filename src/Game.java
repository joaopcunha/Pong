import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int WIDTH = 240;
	public static int HEIGHT = 160;
	public static int SCALE = 4;
	private boolean isRunning;
	private Thread thread;
	
	public BufferedImage layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	public static Player player;
	public static Player player2;
	public static Enemy enemy;
	public static Ball ball;
	public static Menu menu;
	public static Pause pause;
	public static Score score = new Score();
	public static String gameState = "menu";
	public static String gameMode = "singleplayer";
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		this.addKeyListener(this);
		player = new Player(100, HEIGHT-5);
		player2 = new Player(100, 0);
		enemy = new Enemy(100, 0);
		ball = new Ball(100, HEIGHT/2);
		menu = new Menu();
		pause = new Pause();
	}
	

	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame("Pong");
		frame.setResizable(false);
		frame.add(game);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
		
		game.start();
		
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		if (gameState == "menu") {
			menu.tick();
		} else if (gameState == "pause") {
			pause.tick();
		} else if (gameState == "normal") {
			if (gameMode == "singleplayer") {
				player.tick();
				ball.tick();
				enemy.tick();
			} else if (gameMode == "multiplayer") {
				player.tick();
				ball.tick();
				player2.tick();
			}
			score.tick();
			
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = layer.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		if (gameState == "normal") {
			player.render(g);
			ball.render(g);
			if (gameMode == "singleplayer") {
				enemy.render(g);
			} else if (gameMode == "multiplayer") {
				player2.render(g);
			}
		}
		
		
		g = bs.getDrawGraphics();
		g.drawImage(layer, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		
		if (gameState == "menu") {
			menu.render(g);
		} else if (gameState == "pause") {
			pause.render(g);
		} else if (gameState == "gameover") {
			score.renderGameOver(g);
		} else {
			score.render(g);
		}
		
		bs.show();
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000/amountOfTicks;
		double delta = 0;
		
		
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if (delta >= 1) {
				tick();
				render();
				delta--;
			}
		}
		
		stop();
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if (gameState == "menu") {
			if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
				menu.down = true;
			}
			else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
				menu.up = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				menu.enter = true;
			}
		} else if (gameState == "gameover") {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				score.resetScore();
				gameState = "menu";
			}
		} else if (gameState == "pause") {
			if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
				pause.down = true;
			}
			else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
				pause.up = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				pause.enter = true;
			}
		} else if (gameState == "normal") {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				player.right = true;
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				player.left = true;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_Q) {
				player2.left = true;
			} else if (e.getKeyCode() == KeyEvent.VK_W) {
				player2.right = true;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				gameState = "pause";
			}
		}
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_Q) {
			player2.left = false;
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			player2.right = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
