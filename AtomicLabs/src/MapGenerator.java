import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class MapGenerator {
		public int map[][];
		public int casillaAncho;
		public int casillaAltura;

		//recibe numero de renglones y columnas
		public MapGenerator(int row, int col) {
			map = new int[row][col];
			casillaAncho = 30;
			casillaAltura = 30;
			
		}
		
		
		public void draw(Graphics g) {
			for(int i = 0; i < map.length; i ++) {
				for(int j = 0; j < map[0].length; j++) {
					//si esta en este rango es pared limites
					if( i < 20 && (j == 0  ||  j == 19) || j < 20 && (i == 0 || i == 19)) {
						map[i][j] = 1;
						g.setColor(Color.black); 
					}//paredes intermedias
					else if( ( i == 4 || i == 12 || i == 16) && (j >= 2 && j <=7) 
							|| i == 4 && (j >= 10 && j <=16) || j == 13 && (i >= 5 && i <= 7) 
							|| i == 10 && (j < 9 || j > 11) || (j == 12 || j == 16) && (i > 11 && i < 16)){//checar si son paredes intermedias
						//se coloca un 1, para indicar que es una pared
						map[i][j] = 1;
						g.setColor(Color.black);
					}else {//pasillos
						g.setColor(Color.white);
						map[i][j] = 0;
					}
					if(i == 0 && ((j > 2  && j < 7) || (j > 12  && j < 17) )
							|| j == 19  && (i < 19 && i > 14)) {//entradas y salida
						g.setColor(Color.white);
						map[i][j] = 1;
					}
					
					g.fillRect(j * casillaAncho + 80 , i * casillaAltura +50, casillaAncho, casillaAltura);
					
					//lineas
					((Graphics2D) g).setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect(j * casillaAncho + 80 , i * casillaAltura +50, casillaAncho, casillaAltura);
				
				}
			}
		}
		
		public void setOcupado(int value, int row, int col) {
			map[row][col] = value;
		}
		
		
}
