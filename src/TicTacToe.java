import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
/**
 * Main Tic Tac Game
 * @author Vivek
 *
 */
public class TicTacToe extends JFrame {

	private JPanel p1 = new JPanel();
	private JPanel p2 = new JPanel();

	// 9 buttons for Tic tac toe
	public static XOButton buttons[][] = new XOButton[3][3];
	public static JLabel msg = new JLabel("Player 1 turn");

	public static Cell turn = Cell.Cross;
	public static boolean gameOver = false;
	public static int counter = 0;
	public static int clickedX, clickedY;

	public static void main(String[] args) {
		new TicTacToe();
	}

	public TicTacToe() {
		super("Tic Tac Toe");
		setSize(300, 380);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		p1.setLayout(new GridLayout(3, 3));
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttons[i][j] = new XOButton(i, j);
				p1.add(buttons[i][j]);
			}
		}
		// add(p1);
		msg.setSize(300, 80);
		p1.setSize(300, 300);
		p2.setLayout(new BorderLayout());
		p2.add(msg, BorderLayout.NORTH);
		p2.add(p1, BorderLayout.CENTER);
		add(p2);
		
		
		//Add Menus
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);
			
		//File Menu
		JMenu file = new JMenu("File");
		menubar.add(file);
		JMenuItem newGame = new JMenuItem("New Game");
		JMenuItem exit = new JMenuItem("Exit");
		file.add(newGame);
		file.add(exit);
		//Help Menu
		JMenu help = new JMenu("Help");
		menubar.add(help);
		JMenuItem about = new JMenuItem("About");
		help.add(about);
		
		class exitaction implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
			
		}
		exit.addActionListener(new exitaction());
		
		class newgameaction implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				new TicTacToe();
				//System.exit(0);
				dispose();
				 XOButton buttons[][] = new XOButton[3][3];
				msg = new JLabel("Player 1 turn");

				turn = Cell.Cross;
				gameOver = false;
				counter = 0;
			}
			
		}
		newGame.addActionListener(new newgameaction());
		
		class aboutaction implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(new JFrame(), "Tic Tac Toe Game v0.1: By Vivek");
				
			}
			
		}
		about.addActionListener(new aboutaction());
		setVisible(true);
	}

	public static void checkLines() {
		// check horizontal
		int i, j;
		for (i = 0; i < 3; i++) {
			if (buttons[i][clickedY].getValue() != turn)
				break;
		}
		if (i == 3)
			gameOver = true;

		// check vertical
		for (i = 0; i < 3; i++) {
			if (buttons[clickedX][i].getValue() != turn)
				break;
		}
		if (i == 3)
			gameOver = true;
		
		//Diagonals
		if (clickedX == clickedY) {
			for (i = 0; i < 3; i++) {
				if (buttons[i][i].getValue() != turn)
					break;
			}
			if (i == 3)
				gameOver = true;
		}
		if (buttons[2][0].getValue() == turn){
			if (buttons[1][1].getValue() == turn)
				if (buttons[0][2].getValue() == turn)
					gameOver = true;
		}
		if(gameOver && turn== Cell.Cross)
			msg.setText("Player 1 Wins");
		else if(gameOver && turn== Cell.Circle)
			msg.setText("Player 2 Wins");
		
		if(gameOver){
			try {
				MakeSound.makeSound("src/gong.au");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
	}
	
}
