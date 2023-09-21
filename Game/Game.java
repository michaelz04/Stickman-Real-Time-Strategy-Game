
/*
Zhou Michael 
June 15, 2020
Final Project
ICS3U7 Ms. Strelkovska
*/

import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {

	static CardLayout cardLayout;
	static Container container;
	static boolean start;

	public Game() {
		container = getContentPane();
		cardLayout = new CardLayout();
		container.setLayout(cardLayout);

		container.add("Menu", new MenuPanel());
		container.add("Instructions", new InstructionPanel());
		container.add("Game", new GamePanel());
		container.add("Win", new WinnerPanel());
		container.add("Lose", new LoserPanel());
	}

	public static void main(String[] args) {

		Game game = new Game();
		game.setSize(1280, 720);
		game.setVisible(true);
		game.setResizable(false);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
