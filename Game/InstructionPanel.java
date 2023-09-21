import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class InstructionPanel extends JPanel implements ActionListener {

	JButton next, back, start;
	JLabel info;

	int counter;

	public InstructionPanel() {

		setPreferredSize(new Dimension(1280, 720));
		setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());

		JLabel instructions = new JLabel(new ImageIcon("instructions.png"));

		info = new JLabel(new ImageIcon("img1.png"));

		next = new JButton(new ImageIcon("next.png"));
		back = new JButton(new ImageIcon("back.png"));
		start = new JButton(new ImageIcon("start.png"));

		JButton[] buttons = { next, back, start };

		for (int i = 0; i < 3; i++) {
			buttons[i].setContentAreaFilled(false);
			buttons[i].setBorderPainted(false);
			buttons[i].setBorder(BorderFactory.createEmptyBorder());
			buttons[i].addActionListener(this);
		}

		this.add(instructions, BorderLayout.NORTH);
		this.add(next, BorderLayout.EAST);
		this.add(back, BorderLayout.WEST);
		this.add(start, BorderLayout.SOUTH);
		this.add(info, BorderLayout.CENTER);

		counter = 0;
	}

	public void actionPerformed(ActionEvent e) {

		String[] imgs = { "img1.png", "img2.png", "img3.png", "img4.png", "img5.png", "img6.png", "img7.png",
				"img8.png", "img9.png", "img10.png" };

		if (e.getSource() == start) {
			Game.start = true;
			Game.cardLayout.next(Game.container);
		} else if (e.getSource() == next) {
			if (!(counter >= imgs.length - 1)) {
				counter++;
				info.setIcon(new ImageIcon(imgs[counter]));
			}

		} else if (e.getSource() == back) {
			if (!(counter <= 0)) {
				counter--;
				info.setIcon(new ImageIcon(imgs[counter]));
			}
		}
	}
}
