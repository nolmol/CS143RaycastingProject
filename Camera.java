import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Camera implements KeyListener{
	public double xPos, yPos, xDir, yDir, xPlane, yPlane;
	public boolean left, right, forward, back, strLeft, strRight;
	public final double MOVE_SPEED = .08;
	public final double ROTATION_SPEED = .045;
	public Camera(double x, double y, double xd, double yd, double xp, double yp) {
		xPos = x;
		yPos = y;
		xDir = xd;
		yDir = yd;
		xPlane = xp;
		yPlane = yp;
	}
	public void keyPressed(KeyEvent key) {
		if((key.getKeyCode() == KeyEvent.VK_A))
			strLeft = true;
		if((key.getKeyCode() == KeyEvent.VK_D))
			strRight = true;
		if((key.getKeyCode() == KeyEvent.VK_W))
			forward = true;
		if((key.getKeyCode() == KeyEvent.VK_S))
			back = true;
		if((key.getKeyCode() == KeyEvent.VK_LEFT))
			left = true;
		if((key.getKeyCode() == KeyEvent.VK_RIGHT))
			right = true;
	}
	public void keyReleased(KeyEvent key) {
		if((key.getKeyCode() == KeyEvent.VK_A))
			strLeft = false;
		if((key.getKeyCode() == KeyEvent.VK_D))
			strRight = false;
		if((key.getKeyCode() == KeyEvent.VK_W))
			forward = false;
		if((key.getKeyCode() == KeyEvent.VK_S))
			back = false;
		if((key.getKeyCode() == KeyEvent.VK_LEFT))
			left = false;
		if((key.getKeyCode() == KeyEvent.VK_RIGHT))
			right = false;
	}
	public void update(int[][] map) {
		if(forward) {
			if(map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 0) {  //xPos + x Coordinate on unit circle e.g( 0 +.3*10) every update, so you move.
				xPos+=xDir*MOVE_SPEED;
			}
			if(map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] ==0)
				yPos+=yDir*MOVE_SPEED;
		}
		if(back) {
			if(map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 0)
				xPos-=xDir*MOVE_SPEED;
			if(map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)]==0)
				yPos-=yDir*MOVE_SPEED;
		}
		if(strLeft) {
			double xDirL = Math.cos(Math.acos(xDir)+(Math.PI/2.0));
			double yDirL = Math.sin(Math.asin(yDir)+(Math.PI/2.0));
			if(xDir<0) {
				yDirL*=(-1);
			}
			if(yDir<0) {
				xDirL*=(-1);
			}
			if(map[(int)(xPos + xDirL * MOVE_SPEED)][(int)yPos] == 0) {  //xPos + x Coordinate on unit circle e.g( 0 +.3*10) every update, so you move.
				xPos+=xDirL*MOVE_SPEED;
			}
			if(map[(int)xPos][(int)(yPos + yDirL * MOVE_SPEED)] ==0)
				yPos+=yDirL*MOVE_SPEED;
		}
		if(strRight) {
			double xDirR = Math.cos(Math.acos(xDir)-(Math.PI/2.0));
			double yDirR = Math.sin(Math.asin(yDir)-(Math.PI/2.0));
			if(xDir<0) {
				yDirR*=(-1);
			}
			if(yDir<0) {
				xDirR*=(-1);
			}
			if(map[(int)(xPos + xDirR * MOVE_SPEED)][(int)yPos] == 0) {  //xPos + x Coordinate on unit circle e.g( 0 +.3*10) every update, so you move.
				xPos+=xDirR*MOVE_SPEED;
			}
			if(map[(int)xPos][(int)(yPos + yDirR * MOVE_SPEED)] ==0)
				yPos+=yDirR*MOVE_SPEED;
		}
		if(right) {
			double oldxDir=xDir;
			xDir=xDir*Math.cos(-ROTATION_SPEED) - yDir*Math.sin(-ROTATION_SPEED);
			yDir=oldxDir*Math.sin(-ROTATION_SPEED) + yDir*Math.cos(-ROTATION_SPEED);
			double oldxPlane = xPlane;
			xPlane=xPlane*Math.cos(-ROTATION_SPEED) - yPlane*Math.sin(-ROTATION_SPEED);
			yPlane=oldxPlane*Math.sin(-ROTATION_SPEED) + yPlane*Math.cos(-ROTATION_SPEED);
		}
		if(left) {
			double oldxDir=xDir;
			xDir=xDir*Math.cos(ROTATION_SPEED) - yDir*Math.sin(ROTATION_SPEED);
			yDir=oldxDir*Math.sin(ROTATION_SPEED) + yDir*Math.cos(ROTATION_SPEED);
			double oldxPlane = xPlane;
			xPlane=xPlane*Math.cos(ROTATION_SPEED) - yPlane*Math.sin(ROTATION_SPEED);
			yPlane=oldxPlane*Math.sin(ROTATION_SPEED) + yPlane*Math.cos(ROTATION_SPEED);
		}
	}
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}