import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoserPanel extends JPanel implements ActionListener, MouseListener {

	JButton quit;

	public LoserPanel() {
		setPreferredSize(new Dimension(1280, 720));
		setBackground(Color.WHITE);
		this.setLayout(null);

		JLabel message = new JLabel("You Lose.");
		quit = new JButton(new ImageIcon("quit.png"));

		quit.setContentAreaFilled(false);
		quit.setBorderPainted(false);
		quit.setBorder(BorderFactory.createEmptyBorder());
		quit.addActionListener(this);
		quit.addMouseListener(this);

		message.setForeground(Color.BLACK);
		message.setFont(new Font(message.getName(), Font.PLAIN, 50));

		Dimension size;
		size = quit.getPreferredSize();
		quit.setBounds((1280 / 2) - size.width / 2, 400, size.width, size.height);
		size = message.getPreferredSize();
		message.setBounds((1280 / 2) - size.width / 2, 200, size.width, size.height);

		this.add(quit);
		this.add(message);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == quit) {
			System.exit(0);
		}
	}

	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == quit) {
			quit.setIcon(new ImageIcon("quit2.png"));
		}
	}

	public void mouseExited(MouseEvent e) {
		quit.setIcon(new ImageIcon("quit.png"));
	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseClicked(MouseEvent arg0) {

	}

}
