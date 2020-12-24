import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame obj = new JFrame();
		Tablero tablero = new Tablero();
		
		obj.setBounds(10, 10, 800, 800);
		obj.setTitle("AtomicLbas VS Zombie");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(tablero);
		
	}

}