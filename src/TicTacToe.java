import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

@SuppressWarnings("serial")
/**
 * Main Tic Tac Game
 * @author Vivek
 *
 */
public class TicTacToe extends JFrame {

	public static Gamemode gamemode = Gamemode.HumanvsHuman;
	private JPanel p1 = new JPanel();

	// 9 buttons for Tic tac toe
	public static XOButton buttons[][] = new XOButton[3][3];
	public static JLabel msg = new JLabel("Player 1 turn");

	public static Cell turn = Cell.Cross;
	public static boolean gameOver = false;
	public static int counter = 0;
	public static int clickedX, clickedY;
	private GridBagConstraints constraints;
	private GridBagLayout layout;
	public static JButton hvh, hvc;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
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

		hvh = new JButton("Human vs Human");
		hvc = new JButton("Human vs Computer");

		msg.setSize(300, 80);
		layout = new GridBagLayout();
		setLayout(layout);
		constraints = new GridBagConstraints();
		constraints.weightx = 1;
		// constraints.weighty=1;
		constraints.fill = GridBagConstraints.BOTH;
		addComponent(msg, 0, 0, 2, 1);
		addComponent(hvh, 1, 0, 1, 1);
		addComponent(hvc, 1, 1, 1, 1);
		constraints.weighty = 1;
		addComponent(p1, 2, 0, 2, 1);

		// Add Menus
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);

		// File Menu
		JMenu file = new JMenu("File");
		menubar.add(file);
		JMenuItem newGame = new JMenuItem("New Game");
		JMenuItem exit = new JMenuItem("Exit");
		file.add(newGame);
		file.add(exit);
		// Help Menu
		JMenu help = new JMenu("Help");
		menubar.add(help);
		JMenuItem about = new JMenuItem("About");
		help.add(about);

		class exitaction implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}

		}
		exit.addActionListener(new exitaction());

		class newgameaction implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				new TicTacToe();
				// System.exit(0);
				dispose();
				XOButton buttons[][] = new XOButton[3][3];
				msg.setText("Player 1 turn");
				// msg = new JLabel("Player 1 turn");
				turn = Cell.Cross;
				gameOver = false;
				counter = 0;
				/*
				 * gamemode = Gamemode.HumanvsHuman; for(int i=0;i<3;i++)
				 * for(int j=0;j<3;j++) buttons[i][j]= new XOButton(i, j);
				 * 
				 * msg.setText("Player 1 turn");
				 * 
				 * turn = Cell.Cross; gameOver = false; counter = 0;
				 */
			}

		}
		newGame.addActionListener(new newgameaction());

		class aboutaction implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(new JFrame(),
						"Tic Tac Toe Game v0.1: By Vivek");

			}

		}
		about.addActionListener(new aboutaction());

		// mode button action listeners
		hvh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (counter == 0)
					gamemode = Gamemode.HumanvsHuman;

			}
		});

		hvc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (counter == 0)
					gamemode = Gamemode.HumanvsComputer;

			}
		});

		setVisible(true);
	}

	public static void checkLines() {
		// System.out.println(counter);
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

		// Diagonals
		if (clickedX == clickedY) {
			for (i = 0; i < 3; i++) {
				if (buttons[i][i].getValue() != turn)
					break;
			}
			if (i == 3)
				gameOver = true;
		}
		if (buttons[2][0].getValue() == turn) {
			if (buttons[1][1].getValue() == turn)
				if (buttons[0][2].getValue() == turn)
					gameOver = true;
		}
		if (gameOver && turn == Cell.Cross)
			msg.setText("Player 1 Wins");
		else if (gameOver && turn == Cell.Circle) {
			if (gamemode == Gamemode.HumanvsHuman)
				msg.setText("Player 2 Wins");
			else
				msg.setText("Computer Wins");
		}
		// Check for draw
		if (!gameOver && TicTacToe.counter >= 9) {
			msg.setText("Game Draw");
			gameOver = true;
		}

		if (gameOver) {
			try {
				new MakeSound().makeSound("gong.au");
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

	}

	private void addComponent(Component c, int row, int col, int width,
			int height) {
		constraints.gridx = col;
		constraints.gridy = row;
		constraints.gridwidth = width;
		constraints.gridheight = height;
		layout.setConstraints(c, constraints);
		add(c);
	}

	/**
	 * Used Following AI in this Game
	 * 
	 * Turn =1 Go(1) (upper left corner). 
	 * Turn =2 If Board[5] is blank, Go(5),
	 * else Go(1). 
	 * Turn =3 If Board[9] is blank, Go(9), else Go(3). 
	 * Turn =4 If
	 * Posswin(X) is not 0, then Go(Posswin(X)) i.e. [ block opponent’s win],
	 * else Go(Make2).
	 *  Turn =5 if Posswin(X) is not 0 then Go(Posswin(X)) [i.e.
	 * win], else if Posswin(O) is not 0, then Go(Posswin(O)) [i.e. block win],
	 * else if Board[7] is blank, then Go(7), else Go(3). [to explore other
	 * possibility if there be any ]. 
	 * Turn =6 If Posswin(O) is not 0 then
	 * Go(Posswin(O)), else if Posswin(X) is not 0, then Go(Posswin(X)), else
	 * Go(Make2). 
	 * Turn =7 If Posswin(X) is not 0 then Go(Posswin(X)), else if
	 * Posswin(X) is not 0, then Go(Posswin(O)) else go anywhere that is blank.
	 * Turn =8 if Posswin(O) is not 0 then Go(Posswin(O)), else if Posswin(X) is
	 * not 0, then Go(Posswin(X)), else go anywhere that is blank. Turn =9 Same
	 * as Turn=7.
	 */
	public static void computerTurn() {
		// AI for computer
		// first move
		// System.out.println(counter);
		if (counter == 1) {
			if (buttons[1][1].getValue() == Cell.Blank)
				buttons[1][1].doClick();
			else
				buttons[0][0].doClick();
		}
		// second move
		else if (counter == 3) {
			boolean done = PossWin(Cell.Cross);
			if (!done)
				make2();
		}
		// third move
		else if (counter == 5) {
			boolean done = PossWin(Cell.Circle);
			if (!done) {
				done = PossWin(Cell.Cross);
				if (!done)
					make2();
			}
		}
		// fourth move
		else if (counter == 7) {
			boolean done = PossWin(Cell.Circle);
			if (!done) {
				done = PossWin(Cell.Cross);
				if (!done) {
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 3; j++) {
							if (buttons[i][j].getValue() == Cell.Blank) {
								buttons[i][j].doClick();
								return;
							}

						}
					}
				}
			}
		}
	}

	private static boolean PossWin(Cell cell) {
		int cellcount = 0, blankcount = 0;
		XOButton Cbutton = null;
		// Horizontal
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (buttons[i][j].getValue() == Cell.Blank) {
					blankcount++;
					Cbutton = buttons[i][j];
				} else if (buttons[i][j].getValue() == cell)
					cellcount++;
			}
			if (blankcount == 1 && cellcount == 2) {
				Cbutton.doClick();
				return true;
			}

			cellcount = 0;
			blankcount = 0;
		}

		// Vertical
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 3; i++) {
				if (buttons[i][j].getValue() == Cell.Blank) {
					blankcount++;
					Cbutton = buttons[i][j];
				} else if (buttons[i][j].getValue() == cell)
					cellcount++;
			}
			if (blankcount == 1 && cellcount == 2) {
				Cbutton.doClick();
				return true;
			}

			cellcount = 0;
			blankcount = 0;
		}
		// Diagonals
		for (int i = 0; i < 3; i++) {
			if (buttons[i][i].getValue() == Cell.Blank) {
				blankcount++;
				Cbutton = buttons[i][i];
			} else if (buttons[i][i].getValue() == cell)
				cellcount++;
		}
		if (blankcount == 1 && cellcount == 2) {
			Cbutton.doClick();
			return true;
		}

		cellcount = 0;
		blankcount = 0;
		int i, j;
		for (i = 2, j = 0; i >= 0 && j < 3; i--) {
			if (buttons[i][j].getValue() == Cell.Blank) {
				blankcount++;
				Cbutton = buttons[i][j];
			} else if (buttons[i][j].getValue() == cell)
				cellcount++;
			j++;
		}
		if (blankcount == 1 && cellcount == 2) {
			Cbutton.doClick();
			return true;
		}
		return false;
	}

	private static void make2() {
		if (buttons[1][1].getValue() == Cell.Blank)
			buttons[1][1].doClick();
		else if (buttons[0][0].getValue() == Cell.Blank)
			buttons[0][0].doClick();
		else if (buttons[0][2].getValue() == Cell.Blank)
			buttons[0][2].doClick();
		else if (buttons[2][0].getValue() == Cell.Blank)
			buttons[2][0].doClick();
		else
			buttons[2][2].doClick();
	}
}
