import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Zombie {

	public int posX;
	public int posY;
	public int posxAnterior;
	public int posyAnterior;
	public int casillaAncho;
	public int casillaAltura;
	
	public Zombie(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		casillaAncho = 30;
		casillaAltura = 30;
		posxAnterior = 0;
		posyAnterior = 0;
		
	}


	public void draw(Graphics2D g) {
		g.setColor(Color.red);
		g.fillRect(posX * casillaAncho + 80 , posY * casillaAltura +50, casillaAncho, casillaAltura);
		//lineas
		g.setStroke(new BasicStroke(3));
		g.setColor(Color.black);
		g.drawRect(posX * casillaAncho + 80 , posY * casillaAltura +50, casillaAncho, casillaAltura);
		
	}
	
	
	public int getPosxAnterior() {
		return posxAnterior;
	}
	
	public int getPosyAnterior() {
		return posyAnterior;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public void moveRight() {
		posxAnterior = posX;
		posyAnterior = posY;
		if(getPosX() < 18) {
			posX = posX + 1; 
		}	
	}
	
	public void moveLeft() {
		posxAnterior = posX;
		posyAnterior = posY;
		if(getPosX() > 1) {
			posX -= 1;
		}
				 
	}
	
	public void moveUp() {
		posxAnterior = posX;
		posyAnterior = posY;
		if(getPosY() > 1) {
			posY -= 1;
		}
		
	}
	
	public void moveDown() {
		posxAnterior = posX;
		posyAnterior = posY;
		if(getPosY() < 18) {
			posY += 1; 
		}
		
		
		
	}
	
}
