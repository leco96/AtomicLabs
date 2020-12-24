import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import javax.swing.Timer;
import javax.swing.JPanel;

public class Tablero extends JPanel implements ActionListener{
	private MapGenerator map;
	private boolean play = false;
	private Timer timer;
	private int delay = 350;
	private LinkedList<Zombie> zombies = new LinkedList<Zombie>(); 
	private LinkedList<Colaborador> colaboradores = new LinkedList<Colaborador>(); 
	private int randomZombieX, randomMovimiento, totalColaboradores, infectados;
	private boolean banderaVentana = true, inicializacion, bandera = true, mueveZombie = true;
	private int salvados;
	public int  pos = 0, iteracion = 0, countIter = 0;
	Image background = Toolkit.getDefaultToolkit().createImage("Background.jpg");
	
	public Tablero() {
		//inicializar tablero
		map = new MapGenerator(20,20);
		//inicializar zombies
		generarZombies();
		//inicializar colaboradores
		generarColaboradores();
		timer = new Timer(delay, this);
		timer.start();
		play = true;
		inicializacion = true;
		salvados = 0;
		infectados = 0;
	}
	
	public void generarZombies() {
		//Generar dos zombies aleatoriamente en ventanas
		for(int i = 0; i < 2  ;i ++) {
			while(banderaVentana) {
				randomZombieX = (int) (Math.random() *  16 + 2);
				if((randomZombieX > 2 && randomZombieX < 7) || (randomZombieX > 12 && randomZombieX < 17)) {
					banderaVentana = false;
				}
			}
			//añadir un zombie al linkedList
			zombies.add(new Zombie(randomZombieX,0)); 
			System.out.println("zombie rompió la ventana " + "(" + zombies.get(i).getPosX() + " , " + zombies.get(i).getPosY() + ")");
			randomZombieX = 0;
			banderaVentana = true;
		}
	}
	
	public void generarColaboradores() {
		//generar colaboradores lado izq sup
			colaboradores.add(new Colaborador(9,1,1)); 
			colaboradores.add(new Colaborador(6,3,2));
			colaboradores.add(new Colaborador(4,5,3));
			colaboradores.add(new Colaborador(3,3,4)); 
			colaboradores.add(new Colaborador(7,7,5));
			colaboradores.add(new Colaborador(3,8,6));
			
			//generar colaboradores lado derecho up
			colaboradores.add(new Colaborador(11, 9,7));
			colaboradores.add(new Colaborador(15, 6,8));
			colaboradores.add(new Colaborador(17, 8,9));
			colaboradores.add(new Colaborador(11, 3,10));
			colaboradores.add(new Colaborador(15, 3,11));
			
			//generar colaboradores lado Izq down
			colaboradores.add(new Colaborador(3, 13,12));
			colaboradores.add(new Colaborador(6, 14,13));
			colaboradores.add(new Colaborador(3, 17,14));
			colaboradores.add(new Colaborador(7, 17,15));
		
			
			//generar colaboradores lado derecho down
			colaboradores.add(new Colaborador(13, 12,16));
			colaboradores.add(new Colaborador(17, 13,17));
			colaboradores.add(new Colaborador(13, 17,18));
			colaboradores.add(new Colaborador(10, 15,19));
	
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(background, 0, 0,null);
		map.draw((Graphics2D)g);

		//dibujar zombies
		for(int i =0; i < zombies.size() ; i++) {
			zombies.get(i).draw((Graphics2D)g);
		}
		
		//dibujar colaboradores
		for(int i =0; i < colaboradores.size() ; i++) {
			colaboradores.get(i).draw((Graphics2D)g);
		}
	    
		elementosEscena(g);
		
		g.dispose();
		
	}

	public void elementosEscena(Graphics g) {
		
		int fontSize = 25;
	    g.setFont(new Font("TimesRoman", Font.BOLD, fontSize));
	    g.setColor(Color.blue);
	    g.drawString("Colab.Atomic Lab: " + colaboradores.size() , 10, 20);
	    
	    g.setColor(Color.orange);
	    g.drawString("Infectados: " + infectados  , 280, 20);
	
	    g.setColor(Color.red);
	    g.drawString("Zombies: " + zombies.size() , 450, 20);
	    
	    g.setColor(Color.WHITE);
	    g.drawString("salvados: " + salvados , 650, 20);
	    
	    if(colaboradores.size() == 0) {
	    	 g.setColor(Color.red);
	 	    g.drawString("FIN DE SIMULACION", 300, 650);
		}
	}
	
	public void colaboradoresInfectados() {
		int count = 0;
		for(int i = 0;  i < colaboradores.size() ; i++) {
			if(colaboradores.get(i).estaInfectado()) {
				count ++;
			}
			infectados =  count;
			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		timer.start();
		if(play) {
			
			if(bandera) {
				//movimiento zombies
				movimientoZombies();
				
				if(pos == 4) {
					bandera = false;
					pos = 0;
				}
			}else {
				//movimiento colaboradores
				movimientoColaborador();	
				if(pos == 2) {
					bandera = true;
					pos = 0;
				}
			}
			
			//analizar que colaboradores se infectaron
			estanInfectados();
			//inciarTransformacion
			transformacion();
			//termino la transformacion
			transformacionTermindada();
			//colaboradores que han sido salvados
			colaboradoresSalvados();
			//checar cantidad de colaboradores infectados
			colaboradoresInfectados();
			//Checar si termino el juego
			gameOver();
			
			//cuenta numero de iteraciones
			if(countIter %6 == 0) {
				iteracion ++;
				totalColaboradores = colaboradores.size();
				bitacora();
				countIter = 0;
			}
		}
		repaint();
	}

	public void transformacionTermindada() {
		for(int i = 0; i < colaboradores.size() ; i++) {
			//si ya termino
			if(colaboradores.get(i).estaConvertido()) {
				zombies.add(new Zombie(colaboradores.get(i).getPosX(), colaboradores.get(i).getPosY()));
				System.out.println("El colaborador " + colaboradores.get(i).getId() + " es ahora un zombie, ¡CORREE!");
				colaboradores.remove(i);
			}
		}
	}
	
	public void bitacora() {
		try(FileWriter fw = new FileWriter("Bitacora_zombie.txt", true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			    {
			        out.println(iteracion + " | " + zombies.size() + " | " + totalColaboradores + " | " + salvados);
			} catch (Exception e) {
			    //exception handling left as an exercise for the reader
			}
	}
	
	public void transformacion() {
		for(int i = 0 ; i < colaboradores.size(); i++) {
			if(colaboradores.get(i).estaInfectado()) {
				colaboradores.get(i).iniciarTranformacion();
			}
	
		}
	}
	
	public void estanInfectados() {
		for(int i =0; i < zombies.size() ; i++ ) {
			for(int j =0; j < colaboradores.size() ; j++) {
				if(colaboradores.get(j).getPosY() == zombies.get(i).getPosY() && colaboradores.get(j).getPosX() == (zombies.get(i).getPosX() + 1)) { //existe un colaborador a la dere
					colaboradores.get(j).setColor(1);
					colaboradores.get(j).infectado();
					System.out.println("El colaborador " + colaboradores.get(i).getId() + " se ha INFECTADO en la casilla" + "(" + colaboradores.get(i).getPosX() + " , " + colaboradores.get(i).getPosY() + ")");
				}
				else if(colaboradores.get(j).getPosY() == zombies.get(i).getPosY() && colaboradores.get(j).getPosX() == (zombies.get(i).getPosX() - 1)) { //existe un colaborador a la izq
					colaboradores.get(j).setColor(1);
					colaboradores.get(j).infectado();
					System.out.println("El colaborador " + colaboradores.get(i).getId() + " se ha INFECTADO en la casilla" + "(" + colaboradores.get(i).getPosX() + " , " + colaboradores.get(i).getPosY() + ")");
				}
				else if(colaboradores.get(j).getPosY() == (zombies.get(i).getPosY() + 1) && colaboradores.get(j).getPosX() == zombies.get(i).getPosX()) { //existe un colaborador arriba
					colaboradores.get(j).setColor(1);
					colaboradores.get(j).infectado();
					System.out.println("El colaborador " + colaboradores.get(i).getId() + " se ha INFECTADO en la casilla" + "(" + colaboradores.get(i).getPosX() + " , " + colaboradores.get(i).getPosY() + ")");
				}
				else if(colaboradores.get(j).getPosY() == (zombies.get(i).getPosY() - 1) && colaboradores.get(j).getPosX() == zombies.get(i).getPosX()) { //existe un colaborador abajo
					colaboradores.get(j).setColor(1);
					colaboradores.get(j).infectado();
					System.out.println("El colaborador " + colaboradores.get(i).getId() + " se ha INFECTADO en la casilla" + "(" + colaboradores.get(i).getPosX() + " , " + colaboradores.get(i).getPosY() + ")");
				}
			
			}
			
		}
		
	}
	
	public void gameOver() {
		if(colaboradores.size() == 0) {
			totalColaboradores = 0;
			play = false;
			System.out.println("SIMULACION TERMINADA");
		}
	}
	
	public void colaboradoresSalvados() {
		for(int i =0; i < colaboradores.size() ; i++) {
			if(colaboradores.get(i).getPosY() > 14 && colaboradores.get(i).getPosY() < 19 && colaboradores.get(i).getPosX() == 19  ) {
				System.out.println("El colaborador " + colaboradores.get(i).getId() + " se ha SALVADO en la casilla" + "(" + colaboradores.get(i).getPosX() + " , " + colaboradores.get(i).getPosY() + ")");
				salvados = salvados + 1;
				colaboradores.remove(i);
			
			}
		}
	}
	
	public void movimientoZombies() {
		for(int i =0; i < zombies.size() ; i++) {
			
			//mueve por primera vez el zombie
			if(inicializacion) {
				zombies.get(i). moveDown();
			}else {
				//se mueve aleatoriamente el zombie sin repetir casilla			
				randomMovimiento =  (int) (Math.random() *  4 + 1);
			
				//movimiento - checa que es libre el de la derecha
				if(randomMovimiento  == 1 && map.map[zombies.get(i).getPosY()][zombies.get(i).getPosX() + 1] == 0) {
					//checa con el anterior
					if((zombies.get(i).getPosX() + 1)   != zombies.get(i).getPosxAnterior()) {
						zombies.get(i). moveRight();
					}else {
						estaVacio(i);
					}
				}else if(randomMovimiento  == 2 && map.map[zombies.get(i).getPosY()][zombies.get(i).getPosX() - 1] == 0) {
					if( (zombies.get(i).getPosX() - 1)   != zombies.get(i).getPosxAnterior()) {
					
						zombies.get(i). moveLeft();
					}else {
						estaVacio(i);
					}
				}else if(randomMovimiento  == 3 && map.map[zombies.get(i).getPosY() - 1][zombies.get(i).getPosX()] == 0) {
					if((zombies.get(i).getPosY() - 1) != zombies.get(i).getPosyAnterior()  ) {
						zombies.get(i). moveUp();
					}else {
						estaVacio(i);
					}
				}else if(randomMovimiento  == 4 && map.map[zombies.get(i).getPosY() + 1][zombies.get(i).getPosX()] == 0  ) {
					if((zombies.get(i).getPosY() + 1) != zombies.get(i).getPosyAnterior() ) {
						zombies.get(i). moveDown();	
					}else {
						estaVacio(i);
					}	
				}else {
					estaVacio(i);
				}
			}
			
			
			
		}
	
		pos = pos + 1;
		countIter = countIter + 1;
		inicializacion = false;
	}
	
	public void estaVacio(int i) {
		if(map.map[zombies.get(i).getPosY()][zombies.get(i).getPosX() +1] == 0 && (zombies.get(i).getPosX() + 1)   != zombies.get(i).getPosxAnterior()) {	
			zombies.get(i). moveRight();
		}else if(map.map[zombies.get(i).getPosY()][zombies.get(i).getPosX() -1] == 0 && (zombies.get(i).getPosX() - 1)   != zombies.get(i).getPosxAnterior()) {	
			zombies.get(i). moveLeft();
		}else if(map.map[zombies.get(i).getPosY() - 1][zombies.get(i).getPosX()] == 0 && (zombies.get(i).getPosY() - 1) != zombies.get(i).getPosyAnterior()) {	
			zombies.get(i). moveUp();
		}else if(map.map[zombies.get(i).getPosY() + 1][zombies.get(i).getPosX() ] == 0 && (zombies.get(i).getPosY() + 1) != zombies.get(i).getPosyAnterior() ) {
			zombies.get(i). moveDown();
		}else {
			System.out.println("perdio turno");
		}
	}
	
	public void movimientoColaborador() {
		
		for(int i =0; i < colaboradores.size() ; i++) {
			//SI ENCUENTRA ALGO EN ALGUN LADO
			if(colaboradores.get(i).getPosY() >= 1 && colaboradores.get(i).getPosY() < 19 && colaboradores.get(i).getPosX() >= 1 && colaboradores.get(i).getPosX() < 19) {//establecer rangos del tablero
				 if(colaboradores.get(i).getPosY() < 5 &&  colaboradores.get(i).getPosY() >= 1 && colaboradores.get(i).getPosX() < 10) {
					if(map.map[colaboradores.get(i).getPosY() + 1][colaboradores.get(i).getPosX()] == 1) {
						colaboradores.get(i). moveLeft();
					}else if(map.map[colaboradores.get(i).getPosY() + 1][colaboradores.get(i).getPosX() + 1] == 0){
						colaboradores.get(i).moveDiagonalDR();
					}else {
						colaboradores.get(i).moveDown();
					}
				}
				else if(colaboradores.get(i).getPosY() < 10 &&  colaboradores.get(i).getPosY() >= 5 && colaboradores.get(i).getPosX() < 10) {
					if(map.map[colaboradores.get(i).getPosY() + 1][colaboradores.get(i).getPosX()] == 1) {
						colaboradores.get(i). moveRight();
					}else {
						colaboradores.get(i). moveDown();
					}
				}
			//segunda etapa	
			else if(colaboradores.get(i).getPosY() < 5 &&  colaboradores.get(i).getPosY() >= 1 && colaboradores.get(i).getPosX() >= 10 ) {
					if(map.map[colaboradores.get(i).getPosY() + 1][colaboradores.get(i).getPosX()] == 1) {
						colaboradores.get(i). moveRight();
					}else {
						colaboradores.get(i). moveDown();
					}
			}
			else if(colaboradores.get(i).getPosY() < 10 &&  colaboradores.get(i).getPosY() >= 5 && colaboradores.get(i).getPosX() >= 10 ) {
				if(map.map[colaboradores.get(i).getPosY() + 1][colaboradores.get(i).getPosX()] == 1) {
					colaboradores.get(i). moveLeft();
				}else {
					colaboradores.get(i). moveDown();
				}
			}
			//TERCERA ETAPA
			
			else if(colaboradores.get(i).getPosY() >= 11 &&  colaboradores.get(i).getPosX() <= 8 ) {
				if(colaboradores.get(i).getPosX() <= 8 ) {
					colaboradores.get(i). moveRight();
					
				}else if(colaboradores.get(i).getPosY() < 17) {
					colaboradores.get(i). moveDown();
				}
			}else if(colaboradores.get(i).getPosY() < 18 && colaboradores.get(i).getPosX() < 11) { //final
					colaboradores.get(i). moveDown();
			}else if(colaboradores.get(i).getPosY() == 16  && colaboradores.get(i).getPosX() >= 11) { //final
				colaboradores.get(i). moveRight();
			}else if(colaboradores.get(i).getPosY() < 17  && colaboradores.get(i).getPosX() >= 11) { //final
				colaboradores.get(i). moveDown();
			}
			else if(colaboradores.get(i).getPosY() >= 16) {
					colaboradores.get(i). moveRight();
			}

			
		}
			
			
	}
	
		
	pos = pos + 1;	
	countIter = countIter + 1;
	}
}
