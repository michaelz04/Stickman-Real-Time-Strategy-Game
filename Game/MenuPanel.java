import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuPanel extends JPanel implements ActionListener, MouseListener {

	JButton start, instructions, quit;

	public MenuPanel() {

		setPreferredSize(new Dimension(1280, 720));
		setBackground(Color.WHITE);
		this.setLayout(null);

		JLabel title = new JLabel(new ImageIcon("title.png"));
		JLabel stickman = new JLabel(new ImageIcon("stickman.gif"));
		start = new JButton(new ImageIcon("start.png"));
		instructions = new JButton(new ImageIcon("instructions.png"));
		quit = new JButton(new ImageIcon("quit.png"));
		JButton[] buttons = { start, instructions, quit };

		// button formatting and add listeners
		for (int i = 0; i < 3; i++) {
			buttons[i].setContentAreaFilled(false);
			buttons[i].setBorderPainted(false);
			buttons[i].setBorder(BorderFactory.createEmptyBorder());
			buttons[i].addActionListener(this);
			buttons[i].addMouseListener(this);
		}

		// set bounds
		int counter = 250;
		for (int i = 0; i < 3; i++) {
			Dimension size;
			size = buttons[i].getPreferredSize();
			buttons[i].setBounds((1280 / 2) - size.width / 2, counter, size.width, size.height);
			counter += 100;
		}

		Dimension size;
		size = title.getPreferredSize();
		title.setBounds((1280 / 2) - size.width / 2, 20, size.width, size.height);
		size = stickman.getPreferredSize();
		stickman.setBounds(750, 250, size.width, size.height);

		this.add(title);
		this.add(start);
		this.add(instructions);
		this.add(quit);
		this.add(stickman);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(new ImageIcon("backround.png").getImage(), 0, 0, 1280, 720, null);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start) {
			Game.start = true;
			Game.cardLayout.next(Game.container);
			Game.cardLayout.next(Game.container);
		} else if (e.getSource() == instructions) {
			Game.cardLayout.next(Game.container);
		} else if (e.getSource() == quit) {
			System.exit(0);
		}
	}

	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == start) {
			start.setIcon(new ImageIcon("start2.png"));
		} else if (e.getSource() == instructions) {
			instructions.setIcon(new ImageIcon("instructions2.png"));
		} else if (e.getSource() == quit) {
			quit.setIcon(new ImageIcon("quit2.png"));
		}
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {
		start.setIcon(new ImageIcon("start.png"));
		instructions.setIcon(new ImageIcon("instructions.png"));
		quit.setIcon(new ImageIcon("quit.png"));

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

}
