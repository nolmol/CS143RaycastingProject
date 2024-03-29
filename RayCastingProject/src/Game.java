import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends JFrame implements Runnable{
	int screenWidth = 1600;
	int screenHeight = 900;
	double fps = 0, frameCount = 0;
	public long lastTime = 0;
	private static final long serialVersionUID = 1L;
	public int mapWidth = 15;
	public int mapHeight = 15;
	private Thread thread;
	private boolean running;
	private BufferedImage image;
	public int[] pixels;
	public ArrayList<Texture> textures;
	public Camera camera;
	public Screen screen;
	public static int[][] map = 
		{
			{1,1,1,1,1,1,1,1,2,2,2,2,2,2,2},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
			{1,0,0,0,0,0,1,0,0,0,0,0,0,0,2},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
			{1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
			{1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
			{1,0,0,0,0,0,1,4,0,3,3,3,3,0,4},
			{1,0,0,0,0,0,1,4,0,3,3,3,3,0,4},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
			{1,1,1,1,1,1,1,4,4,4,4,4,4,4,4}
		};
	public Game() {
		thread = new Thread(this);
		image = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		textures = new ArrayList<Texture>();
		textures.add(Texture.wood);
		textures.add(Texture.brick);
		textures.add(Texture.bluestone);
		textures.add(Texture.stone);
		camera = new Camera(1.5, 1.5, 1, 0, 0, -.66);
		screen = new Screen(map, mapWidth, mapHeight, textures, screenWidth, screenHeight);
		addKeyListener(camera);
		setSize(screenWidth, screenHeight);
		setResizable(false);
		setTitle("3D Engine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.black);
		setLocationRelativeTo(null);
		setVisible(true);
		start();
	}
	private synchronized void start() {
		running = true;
		thread.start();
	}
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.WHITE);
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
		//Center
		g.fillRect(screenWidth/2 - 1, screenHeight/2 - 1, 2, 2);
		g.setColor(Color.LIGHT_GRAY);
		//Bottom
		g.fillRect(screenWidth/2 - 1, screenHeight/2 + 6, 2, 12);
		//Right
		g.fillRect(screenWidth/2 + 6, screenHeight/2 - 1, 12, 2);
		//Left     x-axis            y-axis             x-size, y-size
		g.fillRect(screenWidth/2 - 18, screenHeight/2 - 1, 12, 2);
		//Top
		g.fillRect(screenWidth/2 - 1, screenHeight/2 - 18, 2, 12);
		//Display FPS
		g.drawString("FPS: " + (fps - (fps % .1) + 0.1), screenWidth - 64, 50);
		
		frameCount++;
		//Update fps
		if (frameCount % 20 == 0) {
			fps = ((1000000000.0 / (System.nanoTime() - lastTime)) * 6); //one second(nano) divided by amount of time it takes for one frame to finish
			if (fps > 60) {
				fps = 60;
			}
			lastTime = System.nanoTime();
		}
		bs.show();
		g.dispose();
	}
	public void run() {
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 60.0;//60 times per second
		double delta = 0;
		requestFocus();

		while(running) {
			long now = System.nanoTime();
			delta = delta + ((now-lastTime) / ns);
			lastTime = now;
			while (delta >= 1)//Make sure update is only happening 60 times a second
			{
				//handles all of the logic restricted time
				screen.update(camera, pixels);
				camera.update(map);
				delta--;
			}
			render();//displays to the screen unrestricted time
		}
	}
	public static void main(String [] args) {
		System.setProperty("sun.java2d.opengl", "true");
		Game game = new Game();
	}
}
