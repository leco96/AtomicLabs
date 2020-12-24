import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Colaborador {

	public int posX;
	public int posY;
	public int casillaAncho;
	public int casillaAltura;
	public int id;
	public int color;
	public boolean infectado;
	public int contadorZombie;
	public boolean convertido;
	
	
	public Colaborador(int posX, int posY, int id) {
		this.posX = posX;
		this.posY = posY;
		this.id = id;
		casillaAncho = 30;
		casillaAltura = 30;
		color = 0;
		infectado = false;
		contadorZombie = 0;
		convertido = false;
	}
	
	public void draw(Graphics2D g) {
		if(color == 0) {
			g.setColor(Color.blue);
		}else if(color == 1){
			g.setColor(Color.ORANGE);
		}
		else if(color == 2){
			g.setColor(Color.red);
		}
		
		
		g.fillRect(posX * casillaAncho + 80 , posY * casillaAltura +50, casillaAncho, casillaAltura);
		//lineas
		g.setStroke(new BasicStroke(3));
		g.setColor(Color.black);
		g.drawRect(posX * casillaAncho + 80 , posY * casillaAltura +50, casillaAncho, casillaAltura);
		
	}
	
	public void setConvertido() {
		convertido = true;
	}
	public boolean estaConvertido() {
		return convertido;
	}
	
	public void iniciarTranformacion() {
		contadorZombie += 1;
		if(contadorZombie >= 4) {
			color = 2;
			setConvertido();
			infectado = false;
		}
	}
	
	public void infectado() {
		infectado = true;
	}
	
	public boolean estaInfectado() {
		return infectado;
	}
	
	
	public void setColor(int nuevoColor) {
		this.color = nuevoColor;
	}
	
	public int getId() {
		return id;
	}
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public void moveRight() {
		if(getPosX() < 18 || (getPosX() < 19 && getPosY() >= 16 ) ) {
			posX = posX + 1; 
		}
		
	}
	
	public void moveLeft() {
		if(getPosX() > 1) {
			posX -= 1;
		}
		 
	}
	
	public void moveUp() {
		if(getPosY() > 1) {
			posY -= 1;
		}
	}
	
	public void moveDown() {
		if(getPosY() < 18) {
			posY += 1; 
		}
		
	}
	public void moveDiagonalDR() {
		posY += 1; 
		posX += 1;
	}
	public void moveDiagonalUL() {
		posY -= 1; 
		posX -= 1;
	}
	
	public void moveDiagonalDL() {
		posY -= 1; 
		posX += 1;
	}
	
}
