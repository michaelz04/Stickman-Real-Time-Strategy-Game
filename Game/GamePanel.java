import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.ArrayDeque;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener, MouseListener {

	// command buttons
	JButton attackB, retreatB, holdB;
	// economy building buttons
	JButton mine, quarry;
	// defence building buttons
	JButton watchtower;
	// troop building buttons
	JButton garrison, camp, stable;
	// upgrade buttons
	JButton footman_upgrade, bowman_upgrade, knight_upgrade;
	// troop buttons
	JButton footman, bowman, knight;

	// labels
	JLabel player_mine, enemy_mine;
	JLabel player_quarry, enemy_quarry;

	JLabel player_garrison, enemy_garrison;
	JLabel player_camp, enemy_camp;
	JLabel player_stable, enemy_stable;

	JLabel player_watchtower, enemy_watchtower;

	JLabel info;

	// note: variable names with "E" at the end means "enemy"

	// bought
	// player
	boolean mine_bought;
	boolean quarry_bought;

	boolean watchtower_bought;

	boolean garrison_bought;
	boolean camp_bought;
	boolean stable_bought;

	boolean footman_upgrade_bought;
	boolean bowman_upgrade_bought;
	boolean knight_upgrade_bought;

	// enemy
	boolean mine_boughtE;
	boolean quarry_boughtE;

	boolean watchtower_boughtE;

	boolean garrison_boughtE;
	boolean camp_boughtE;
	boolean stable_boughtE;

	boolean footman_upgrade_boughtE;
	boolean bowman_upgrade_boughtE;
	boolean knight_upgrade_boughtE;

	// gold
	int gold_increment = 1;
	int gold = 0;

	int gold_incrementE = 1;
	int goldE = 0;

	int troop_limit = 2;
	int troop_counter = 0;
	JLabel troop_counter_label;

	// troop counter
	int f = 0;
	int b = 0;
	int k = 0;

	int fE = 0;
	int bE = 0;
	int kE = 0;

	int troop_limitE = 2;
	int troop_counterE = 0;

	boolean attack;
	boolean retreat;

	int player_health = 100;
	int enemy_health = 100;

	// player troops
	// used deque for dead troops as a queue to
	Troop footman1 = new Footman(120, 260, 10, "Footman1", true),
			footman2 = new Footman(120, 260, 10, "Footman2", true),
			footman3 = new Footman(120, 260, 10, "Footman3", true),
			footman4 = new Footman(120, 260, 10, "Footman4", true);
	ArrayList<Troop> alive_footmen = new ArrayList<Troop>();
	Deque<Troop> dead_footmen = new ArrayDeque<Troop>(Arrays.asList(footman1, footman2, footman3, footman4));

	Troop bowman1 = new Bowman(120, 260, 10, "Bowman1", true), bowman2 = new Bowman(120, 260, 10, "Bowman2", true),
			bowman3 = new Bowman(120, 260, 10, "Bowman3", true), bowman4 = new Bowman(120, 260, 10, "Bowman4", true);
	ArrayList<Troop> alive_bowmen = new ArrayList<Troop>();
	Deque<Troop> dead_bowmen = new ArrayDeque<Troop>(Arrays.asList(bowman1, bowman2, bowman3, bowman4));

	Troop knight1 = new Knight(120, 260, 10, "Knight1", true), knight2 = new Knight(120, 260, 10, "Knight2", true),
			knight3 = new Knight(120, 260, 10, "Knight3", true), knight4 = new Knight(120, 260, 10, "Knight4", true);
	ArrayList<Troop> alive_knights = new ArrayList<Troop>();
	Deque<Troop> dead_knights = new ArrayDeque<Troop>(Arrays.asList(knight1, knight2, knight3, knight4));

	// enemy troops
	Troop footman1E = new Footman(680, -30, 10, "Footman1", false),
			footman2E = new Footman(680, -30, 10, "Footman2", false),
			footman3E = new Footman(680, -30, 10, "Footman3", false),
			footman4E = new Footman(680, -30, 10, "Footman4", false);
	ArrayList<Troop> alive_footmenE = new ArrayList<Troop>();
	Deque<Troop> dead_footmenE = new ArrayDeque<Troop>(Arrays.asList(footman1E, footman2E, footman3E, footman4E));

	Troop bowman1E = new Bowman(680, -30, 10, "Bowman1", false), bowman2E = new Bowman(680, -30, 10, "Bowman2", false),
			bowman3E = new Bowman(680, -30, 10, "Bowman3", false),
			bowman4E = new Bowman(680, -30, 10, "Bowman4", false);
	ArrayList<Troop> alive_bowmenE = new ArrayList<Troop>();
	Deque<Troop> dead_bowmenE = new ArrayDeque<Troop>(Arrays.asList(bowman1E, bowman2E, bowman3E, bowman4E));

	Troop knight1E = new Knight(680, -30, 10, "Knight1", false), knight2E = new Knight(680, -30, 10, "Knight1", false),
			knight3E = new Knight(680, -30, 10, "Knight3", false),
			knight4E = new Knight(680, -30, 10, "Knight4", false);
	ArrayList<Troop> alive_knightsE = new ArrayList<Troop>();
	Deque<Troop> dead_knightsE = new ArrayDeque<Troop>(Arrays.asList(knight1E, knight2E, knight3E, knight4E));

	// timers
	Timer general_timer, footman_timer, bowman_timer, knight_timer, footman_timerE, bowman_timerE, knight_timerE,
			bot_timer;

	boolean quit;

	public GamePanel() {

		setPreferredSize(new Dimension(1280, 720));
		setBackground(Color.WHITE);
		this.setLayout(null);

		Timer timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startTimer(Game.start);
			}
		});
		timer.start();

		// labels
		JLabel gold_label = new JLabel("Gold: 00000");
		gold_label.setForeground(Color.BLACK);
		gold_label.setFont(new Font(gold_label.getName(), Font.PLAIN, 20));

		troop_counter_label = new JLabel("Troop Count: " + troop_counter + "/" + troop_limit);
		troop_counter_label.setForeground(Color.BLACK);
		troop_counter_label.setFont(new Font(troop_counter_label.getName(), Font.PLAIN, 20));

		JLabel player_base = new JLabel(new ImageIcon("base.png"));
		JLabel enemy_base = new JLabel(new ImageIcon("base.png"));

		player_mine = new JLabel(new ImageIcon("mine.png"));
		enemy_mine = new JLabel(new ImageIcon("mine.png"));
		player_quarry = new JLabel(new ImageIcon("quarry.png"));
		enemy_quarry = new JLabel(new ImageIcon("quarry.png"));

		player_watchtower = new JLabel(new ImageIcon("watchtower.png"));
		enemy_watchtower = new JLabel(new ImageIcon("watchtower.png"));

		player_garrison = new JLabel(new ImageIcon("garrison.png"));
		enemy_garrison = new JLabel(new ImageIcon("garrison.png"));
		player_camp = new JLabel(new ImageIcon("camp.png"));
		enemy_camp = new JLabel(new ImageIcon("camp.png"));
		player_stable = new JLabel(new ImageIcon("stable.png"));
		enemy_stable = new JLabel(new ImageIcon("stable.png"));

		JLabel command = new JLabel("Commands");
		JLabel economy = new JLabel("Economy");
		JLabel defence = new JLabel("Defence");
		JLabel troop_building = new JLabel("Troop Buildings");
		JLabel upgrades = new JLabel("Troop Upgrades");

		JLabel[] labels = { command, economy, defence, troop_building, upgrades };

		info = new JLabel();

		for (int i = 0; i < labels.length; i++) {
			labels[i].setForeground(Color.WHITE);
			labels[i].setFont(new Font(labels[i].getName(), Font.PLAIN, 30));
		}

		// buttons
		attackB = new JButton(new ImageIcon("attack.png"));
		retreatB = new JButton(new ImageIcon("retreat.png"));
		holdB = new JButton(new ImageIcon("hold.png"));
		mine = new JButton(new ImageIcon("mineb.png"));
		quarry = new JButton(new ImageIcon("quarryb.png"));
		watchtower = new JButton(new ImageIcon("watchtowerb.png"));
		garrison = new JButton(new ImageIcon("garrisonb.png"));
		camp = new JButton(new ImageIcon("campb.png"));
		stable = new JButton(new ImageIcon("stableb.png"));
		footman = new JButton(new ImageIcon("footmanb.png"));
		bowman = new JButton(new ImageIcon("bowmanb.png"));
		knight = new JButton(new ImageIcon("knightb.png"));
		footman_upgrade = new JButton(new ImageIcon("footman_upgrade.png"));
		bowman_upgrade = new JButton(new ImageIcon("bowman_upgrade.png"));
		knight_upgrade = new JButton(new ImageIcon("knight_upgrade.png"));

		JButton[] buttons = { attackB, retreatB, holdB, mine, quarry, watchtower, garrison, camp, stable,
				footman_upgrade, bowman_upgrade, knight_upgrade, footman, bowman, knight };

		// button formatting and add listeners
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setContentAreaFilled(false);
			buttons[i].setBorderPainted(false);
			buttons[i].setBorder(BorderFactory.createEmptyBorder());
			buttons[i].addActionListener(this);
			buttons[i].addMouseListener(this);
		}

		// set bounds
		Dimension size;
		// labels
		size = gold_label.getPreferredSize();
		gold_label.setBounds(640, 20, size.width, size.height);
		size = troop_counter_label.getPreferredSize();
		troop_counter_label.setBounds(300, 20, size.width, size.height);

		size = info.getPreferredSize();
		info.setBounds(630, 450, size.width, size.height);

		// base
		size = player_base.getPreferredSize();
		player_base.setBounds(-160, 300, size.width, size.height);
		size = enemy_base.getPreferredSize();
		enemy_base.setBounds(960, -60, size.width, size.height);
		// garrison
		size = player_garrison.getPreferredSize();
		player_garrison.setBounds(-20, 290, size.width, size.height);
		size = enemy_garrison.getPreferredSize();
		enemy_garrison.setBounds(830, -70, size.width, size.height);
		// camp
		size = player_camp.getPreferredSize();
		player_camp.setBounds(-160, 140, size.width, size.height);
		size = enemy_camp.getPreferredSize();
		enemy_camp.setBounds(960, 60, size.width, size.height);
		// stable
		size = player_stable.getPreferredSize();
		player_stable.setBounds(0, 160, size.width, size.height);
		size = enemy_stable.getPreferredSize();
		enemy_stable.setBounds(840, 70, size.width, size.height);
		// mine
		size = player_mine.getPreferredSize();
		player_mine.setBounds(-140, 40, size.width, size.height);
		size = enemy_mine.getPreferredSize();
		enemy_mine.setBounds(980, 180, size.width, size.height);
		// quarry
		size = player_quarry.getPreferredSize();
		player_quarry.setBounds(-140, -80, size.width, size.height);
		size = enemy_quarry.getPreferredSize();
		enemy_quarry.setBounds(980, 230, size.width, size.height);
		// watchtower
		size = player_watchtower.getPreferredSize();
		player_watchtower.setBounds(10, 10, size.width, size.height);
		size = enemy_watchtower.getPreferredSize();
		enemy_watchtower.setBounds(850, 210, size.width, size.height);

		// label formating
		int counter = 40;
		for (int i = 0; i < labels.length; i++) {
			size = labels[i].getPreferredSize();
			labels[i].setBounds(counter, 520, size.width, size.height);
			if (i == 1) {
				counter -= 30;
			} else if (i == 2) {
				counter -= 50;
			} else if (i == 3) {
				counter += 50;
			}
			counter += 250;
		}

		// button formating
		counter = 10;
		for (int i = 0; i < buttons.length - 3; i++) {
			size = buttons[i].getPreferredSize();
			buttons[i].setBounds(counter, 580, size.width, size.height);

			if (i == 2) {
				counter += 60;
			} else if (i == 4 || i == 5 || i == 8) {
				counter += 110;
			}
			counter += 70;
		}

		counter = 550;
		for (int i = buttons.length - 3; i < buttons.length; i++) {
			size = buttons[i].getPreferredSize();
			buttons[i].setBounds(counter, 440, size.width, size.height);

			counter += 70;
		}

		// add
		this.add(gold_label);
		this.add(troop_counter_label);
		this.add(player_base);
		this.add(enemy_base);
		this.add(info);

		for (int i = 0; i < labels.length; i++) {
			this.add(labels[i]);
		}

		for (int i = 0; i < buttons.length - 3; i++) {
			this.add(buttons[i]);
		}

		// end of formatting/adding buttons, labels, whatever

		// timer for other tasks
		general_timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (quit) {
					((Timer) e.getSource()).stop();
				}
				// player gold
				gold += gold_increment;
				gold_label.setText("Gold: " + gold);
				// enemy gold
				goldE += gold_incrementE;

				// watchtower damage
				if (watchtower_bought) {
					for (int i = 0; i < alive_footmenE.size(); i++) {
						if (alive_footmenE.get(i).getX() == 160) {
							alive_footmenE.get(i).setHealth(alive_footmenE.get(i).getHealth() - 1);
						}
					}
					for (int i = 0; i < alive_bowmenE.size(); i++) {
						if (alive_bowmenE.get(i).getX() == 200) {
							alive_bowmenE.get(i).setHealth(alive_bowmenE.get(i).getHealth() - 1);
						}
					}
					for (int i = 0; i < alive_knightsE.size(); i++) {
						if (alive_knightsE.get(i).getX() == 160) {
							alive_knightsE.get(i).setHealth(alive_knightsE.get(i).getHealth() - 1);
						}
					}
				}
				if (watchtower_boughtE) {
					for (int i = 0; i < alive_footmen.size(); i++) {
						if (alive_footmen.get(i).getX() == 640) {
							alive_footmen.get(i).setHealth(alive_footmen.get(i).getHealth() - 1);
						}
					}
					for (int i = 0; i < alive_bowmen.size(); i++) {
						if (alive_bowmen.get(i).getX() == 600) {
							alive_bowmen.get(i).setHealth(alive_bowmen.get(i).getHealth() - 1);
						}
					}
					for (int i = 0; i < alive_knights.size(); i++) {
						if (alive_knights.get(i).getX() == 640) {
							alive_knights.get(i).setHealth(alive_knights.get(i).getHealth() - 1);
						}
					}
				}

				if (enemy_health <= 0) {
					quit = true;
					Game.cardLayout.next(Game.container);
					((Timer) e.getSource()).stop();
				}
				if (player_health <= 0) {
					quit = true;
					Game.cardLayout.next(Game.container);
					Game.cardLayout.next(Game.container);
					((Timer) e.getSource()).stop();
				}

			}
		});

		// footman actions
		footman_timer = new Timer(481, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (quit) {
					((Timer) e.getSource()).stop();
				}
				if (attack) {
					for (int i = 0; i < alive_footmen.size(); i++) {
						if (!(alive_footmen.get(i).getX() >= 640)) {
							footmenAttack(i);
							if (!alive_footmen.get(i).getAttacking() && !(alive_footmen.get(i).getX() >= 640)) {
								alive_footmen.get(i).attack();
							}
						}

						else if (alive_footmen.get(i).getX() >= 640) {
							if (alive_footmenE.size() != 0 || alive_bowmenE.size() != 0 || alive_knightsE.size() != 0) {
								footmenAttack(i);
							} else {
								alive_footmen.get(i).hit();
								enemy_health -= footman1.getDamage();
							}
						} else {
							alive_footmen.get(i).stop();
						}
					}
				} else if (retreat) {
					for (int i = 0; i < alive_footmen.size(); i++) {
						if (!(alive_footmen.get(i).getX() <= 120)) {
							alive_footmen.get(i).retreat();
						} else {
							alive_footmen.get(i).stop();
						}
					}
				} else {
					for (int i = 0; i < alive_footmen.size(); i++) {
						alive_footmen.get(i).stop();
					}
				}
				repaint();

			}
		});

		// bowman actions
		bowman_timer = new Timer(481, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (quit) {
					((Timer) e.getSource()).stop();
				}
				if (attack) {
					for (int i = 0; i < alive_bowmen.size(); i++) {
						if (!(alive_bowmen.get(i).getX() >= 600)) {
							bowmenAttack(i);
							if (!alive_bowmen.get(i).getAttacking() && !(alive_bowmen.get(i).getX() >= 600)) {
								alive_bowmen.get(i).attack();
							}
						} else if (alive_bowmen.get(i).getX() >= 600) {
							if (alive_footmenE.size() != 0 || alive_bowmenE.size() != 0 || alive_knightsE.size() != 0) {
								bowmenAttack(i);
							} else {
								alive_bowmen.get(i).hit();
								enemy_health -= bowman1.getDamage();
							}
						} else {
							alive_bowmen.get(i).stop();
						}
					}
				} else if (retreat) {
					for (int i = 0; i < alive_bowmen.size(); i++) {
						if (!(alive_bowmen.get(i).getX() <= 120)) {
							alive_bowmen.get(i).retreat();
						} else {
							alive_bowmen.get(i).stop();
						}
					}
				} else {
					for (int i = 0; i < alive_bowmen.size(); i++) {
						alive_bowmen.get(i).stop();
					}
				}
				repaint();
			}
		});

		// knight actions
		knight_timer = new Timer(481, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (quit) {
					((Timer) e.getSource()).stop();
				}
				if (attack) {
					for (int i = 0; i < alive_knights.size(); i++) {
						if (!(alive_knights.get(i).getX() >= 640)) {
							knightsAttack(i);
							if (!alive_knights.get(i).getAttacking() && !(alive_knights.get(i).getX() >= 640)) {
								alive_knights.get(i).attack();
							}

						} else if (alive_knights.get(i).getX() >= 630) {
							if (alive_footmenE.size() != 0 || alive_bowmenE.size() != 0 || alive_knightsE.size() != 0) {
								knightsAttack(i);
							} else {
								alive_knights.get(i).hit();
								enemy_health -= knight1.getDamage();
							}
						} else {
							alive_knights.get(i).stop();
						}
					}
				} else if (retreat) {
					for (int i = 0; i < alive_knights.size(); i++) {
						if (!(alive_knights.get(i).getX() <= 120)) {
							alive_knights.get(i).retreat();
						} else {
							alive_knights.get(i).stop();
						}
					}
				} else {
					for (int i = 0; i < alive_knights.size(); i++) {
						alive_knights.get(i).stop();
					}
				}
				repaint();
			}
		});

		// enemy troops
		footman_timerE = new Timer(481, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (quit) {
					((Timer) e.getSource()).stop();
				}
				for (int i = 0; i < alive_footmenE.size(); i++) {
					if (!(alive_footmenE.get(i).getX() <= 160)) {
						footmenAttackE(i);
						if (!alive_footmenE.get(i).getAttacking() && !(alive_footmenE.get(i).getX() <= 160)) {
							alive_footmenE.get(i).retreat();
						}
					}

					else if (alive_footmenE.get(i).getX() <= 160) {
						if (alive_footmen.size() != 0 || alive_bowmen.size() != 0 || alive_knights.size() != 0) {
							footmenAttackE(i);
						} else {
							alive_footmenE.get(i).hit();
							player_health -= footman1E.getDamage();
						}

					} else {
						alive_footmenE.get(i).stop();
					}

				}

				repaint();
			}
		});

		// bowman actions
		bowman_timerE = new Timer(481, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (quit) {
					((Timer) e.getSource()).stop();
				}
				for (int i = 0; i < alive_bowmenE.size(); i++) {
					if (!(alive_bowmenE.get(i).getX() <= 200)) {
						bowmenAttackE(i);
						if (!alive_bowmenE.get(i).getAttacking() && !(alive_bowmenE.get(i).getX() <= 200)) {
							alive_bowmenE.get(i).retreat();
						}
					} else if (alive_bowmenE.get(i).getX() <= 200) {
						if (alive_footmen.size() != 0 || alive_bowmen.size() != 0 || alive_knights.size() != 0) {
							bowmenAttackE(i);
						} else {
							alive_bowmenE.get(i).hit();
							player_health -= bowman1.getDamage();
						}

					} else {
						alive_bowmenE.get(i).stop();
					}

				}
				repaint();
			}
		});

		// knight actions
		knight_timerE = new Timer(481, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (quit) {
					((Timer) e.getSource()).stop();
				}
				for (int i = 0; i < alive_knightsE.size(); i++) {
					if (!(alive_knightsE.get(i).getX() <= 160)) {
						knightsAttackE(i);

						if (!alive_knightsE.get(i).getAttacking() && !(alive_knightsE.get(i).getX() <= 160)) {
							alive_knightsE.get(i).retreat();
						}
					} else if (alive_knightsE.get(i).getX() <= 160) {
						if (alive_footmen.size() != 0 || alive_bowmen.size() != 0 || alive_knights.size() != 0) {
							knightsAttackE(i);
						} else {
							alive_knightsE.get(i).hit();
							player_health -= knight1E.getDamage();
						}

					} else {
						alive_knightsE.get(i).stop();
					}
				}

				repaint();
			}
		});

		// bot timer
		bot_timer = new Timer(481, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (quit) {
					((Timer) e.getSource()).stop();
				}

				double random = Math.random();

				if (fE < 4 && goldE >= 10 && troop_counterE < troop_limitE && garrison_boughtE && random < 0.01) {
					fE++;
					alive_footmenE.add(dead_footmenE.remove());
					troop_counterE++;
					goldE -= 10;
				}

				if (bE < 4 && goldE >= 20 && troop_counterE < troop_limitE && camp_boughtE && random > 0.01
						&& random < 0.02) {
					bE++;
					alive_bowmenE.add(dead_bowmenE.remove());
					troop_counterE++;
					goldE -= 20;
				}

				if (kE < 4 && goldE >= 30 && troop_counterE < troop_limitE && stable_boughtE && random > 0.02
						&& random < 0.03) {
					kE++;
					alive_knightsE.add(dead_knightsE.remove());
					troop_counterE++;
					goldE -= 30;
				}

				if (goldE >= 10 && !mine_boughtE && random > 0.8) {
					gold_incrementE += 1;
					goldE -= 10;
					mine_boughtE = true;
					add(enemy_mine);
				}

				if (goldE >= 30 && !quarry_bought && random > 0.8) {
					gold_incrementE += 5;
					goldE -= 30;
					quarry_boughtE = true;
					add(enemy_quarry);

				}

				if (goldE >= 20 && !watchtower_boughtE && random < 0.4) {
					add(enemy_watchtower);
					watchtower_boughtE = true;
					goldE -= 20;

				}

				if (goldE >= 10 && !garrison_boughtE && random < 0.1) {
					add(enemy_garrison);
					troop_limitE += 1;
					garrison_boughtE = true;
					goldE -= 10;
				}

				if (goldE >= 20 && !camp_boughtE && random > 0.95) {
					add(enemy_camp);
					troop_limitE += 1;
					camp_boughtE = true;
					goldE -= 20;
				}

				if (goldE >= 30 && !stable_boughtE && random < 0.07) {
					add(enemy_stable);
					troop_limitE += 1;
					stable_boughtE = true;
					goldE -= 30;
				}

				if (goldE >= 10 && !footman_upgrade_boughtE && random < 0.01) {
					footman1E.setDamage(2);
					footman_upgrade_boughtE = true;
					goldE -= 10;
				}

				if (goldE >= 20 && !bowman_upgrade_boughtE && random > 0.98) {
					bowman1E.setDamage(4);
					bowman_upgrade_boughtE = true;
					goldE -= 20;
				}

				if (goldE >= 30 && !knight_upgrade_boughtE && random < 0.01) {
					knight1E.setDamage(4);
					knight_upgrade_boughtE = true;
					goldE -= 30;
				}

			}
		});

		setDead();

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int i = 0; i < alive_footmen.size(); i++) {
			alive_footmen.get(i).drawTroop(g);
			repaint();
		}

		for (int i = 0; i < alive_bowmen.size(); i++) {
			alive_bowmen.get(i).drawTroop(g);
			repaint();
		}

		for (int i = 0; i < alive_knights.size(); i++) {
			alive_knights.get(i).drawTroop(g);
			repaint();
		}

		// enemy
		for (int i = 0; i < alive_footmenE.size(); i++) {
			alive_footmenE.get(i).drawTroop(g);
			repaint();
		}

		for (int i = 0; i < alive_bowmenE.size(); i++) {
			alive_bowmenE.get(i).drawTroop(g);
			repaint();
		}

		for (int i = 0; i < alive_knightsE.size(); i++) {
			alive_knightsE.get(i).drawTroop(g);
			repaint();
		}
		g.setColor(Color.BLACK);
		g.fillRect(0, 520, 1280, 200);

		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		g.drawLine(290, 0, 290, 520);

		g2.setStroke(new BasicStroke(2));
		g.drawLine(990, 0, 990, 520);

		// player base health
		g.setColor(Color.RED);
		g.fillRect(300, 70, 100, 10);
		g.setColor(Color.GREEN);
		g.fillRect(300, 70, player_health, 10);
		g.setColor(Color.BLACK);
		g.drawString("Player Base Health: " + player_health, 300, 60);

		// enemy base health
		g.setColor(Color.RED);
		g.fillRect(850, 500, 100, 10);
		g.setColor(Color.GREEN);
		g.fillRect(850, 500, enemy_health, 10);
		g.setColor(Color.BLACK);
		g.drawString("Enemy Base Health: " + enemy_health, 850, 490);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == attackB) {
			attack = true;
			retreat = false;
		} else if (e.getSource() == retreatB) {
			attack = false;
			retreat = true;
		} else if (e.getSource() == holdB) {
			retreat = false;
			attack = false;
		}

		else if (e.getSource() == footman) {
			if (f < 4 && gold >= 10 && troop_counter < troop_limit) {
				f++;
				alive_footmen.add(dead_footmen.remove());

				change_troop_counter(1);
				gold -= 10;
			}
		} else if (e.getSource() == bowman) {
			if (b < 4 && gold >= 20 && troop_counter < troop_limit) {
				b++;
				alive_bowmen.add(dead_bowmen.remove());

				change_troop_counter(1);
				gold -= 20;
			}
		} else if (e.getSource() == knight) {
			if (k < 4 && gold >= 30 && troop_counter < troop_limit) {
				k++;
				alive_knights.add(dead_knights.remove());

				change_troop_counter(1);
				gold -= 30;
			}
		}

		else if (e.getSource() == mine) {
			if (gold >= 10 && !mine_bought) {
				gold_increment += 1;
				gold -= 10;
				mine_bought = true;
				add(player_mine);
			}
		} else if (e.getSource() == quarry) {
			if (gold >= 30 && !quarry_bought) {
				gold_increment += 5;
				gold -= 30;
				quarry_bought = true;
				add(player_quarry);
			}
		}

		else if (e.getSource() == watchtower) {
			if (gold >= 20 && !watchtower_bought) {
				add(player_watchtower);
				gold -= 20;
				watchtower_bought = true;
			}
		}

		else if (e.getSource() == garrison) {
			if (gold >= 10 && !garrison_bought) {
				add(footman);
				add(player_garrison);
				troop_limit += 1;
				garrison_bought = true;
				troop_counter_label.setText("Troop Count: " + troop_counter + "/" + troop_limit);
				gold -= 10;
			}
		} else if (e.getSource() == camp) {
			if (gold >= 20 && !camp_bought) {
				add(bowman);
				add(player_camp);
				troop_limit += 1;
				camp_bought = true;
				troop_counter_label.setText("Troop Count: " + troop_counter + "/" + troop_limit);
				gold -= 20;
			}
		} else if (e.getSource() == stable) {
			if (gold >= 30 && !stable_bought) {
				add(knight);
				add(player_stable);
				troop_limit += 1;
				stable_bought = true;
				troop_counter_label.setText("Troop Count: " + troop_counter + "/" + troop_limit);
				gold -= 30;
			}
		}

		else if (e.getSource() == footman_upgrade) {
			if (gold >= 10 && !footman_upgrade_bought) {
				footman1.setDamage(2);
				footman_upgrade_bought = true;
				gold -= 10;
			}

		} else if (e.getSource() == bowman_upgrade) {
			if (gold >= 20 && !bowman_upgrade_bought) {
				bowman1.setDamage(2);
				bowman_upgrade_bought = true;
				gold -= 20;
			}
		} else if (e.getSource() == knight_upgrade) {
			if (gold >= 30 && !knight_upgrade_bought) {
				knight1.setDamage(2);
				knight_upgrade_bought = true;
				gold -= 30;
			}
		}
		repaint();
	}

	public void mouseEntered(MouseEvent e) {
		JButton[] buttons = { attackB, retreatB, holdB, mine, quarry, watchtower, garrison, camp, stable,
				footman_upgrade, bowman_upgrade, knight_upgrade, footman, bowman, knight };
		String[] img_names = { "attack2.png", "retreat2.png", "hold2.png", "mineb2.png", "quarryb2.png",
				"watchtowerb2.png", "garrisonb2.png", "campb2.png", "stableb2.png", "footman_upgrade2.png",
				"bowman_upgrade2.png", "knight_upgrade2.png", "footmanb2.png", "bowmanb2.png", "knightb2.png" };

		String[] text = { "Attack", "Retreat", "Hold", "Mine: 10 gold", "Quarry: 30 gold", "Watchtower: 20 gold",
				"Garrison: 10 gold", "Camp: 20 gold", "Stable: 30 gold", "Upgrade Footman: 10 gold",
				"Upgrade Bowman: 20 gold", "Upgrade Knight: 30 gold", "Train Footman: 10 gold", "Train Bowman: 20 gold",
				"Train Knight: 30 gold" };

		boolean[] bought = { mine_bought, quarry_bought, watchtower_bought, garrison_bought, camp_bought, stable_bought,
				footman_upgrade_bought, bowman_upgrade_bought, knight_upgrade_bought };

		for (int i = 0; i < buttons.length; i++) {
			if (e.getSource() == buttons[i]) {
				buttons[i].setIcon(new ImageIcon(img_names[i]));

				if (i > 2 && i < buttons.length - 3 && bought[i - 3]) {
					info.setText("Bought");
				} else {
					info.setText(text[i]);
				}

				Dimension size;
				size = info.getPreferredSize();
				info.setBounds(630, 420, size.width, size.height);

			}
		}

	}

	public void mouseExited(MouseEvent e) {
		JButton[] buttons = { attackB, retreatB, holdB, mine, quarry, watchtower, garrison, camp, stable,
				footman_upgrade, bowman_upgrade, knight_upgrade, footman, bowman, knight };
		String[] img_names = { "attack.png", "retreat.png", "hold.png", "mineb.png", "quarryb.png", "watchtowerb.png",
				"garrisonb.png", "campb.png", "stableb.png", "footman_upgrade.png", "bowman_upgrade.png",
				"knight_upgrade.png", "footmanb.png", "bowmanb.png", "knightb.png" };

		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setIcon(new ImageIcon(img_names[i]));
		}
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void change_troop_counter(int c) {
		troop_counter += c;
		troop_counter_label.setText("Troop Count: " + troop_counter + "/" + troop_limit);
	}

	//just to check if troop has 0 health and remove them
	public void setDead() {
		Timer aliveCheck = new Timer(481, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (quit) {
					((Timer) e.getSource()).stop();
				}
				// footmen
				for (int i = 0; i < alive_footmen.size(); i++) {
					if (alive_footmen.get(i).getHealth() <= 0) {
						alive_footmen.get(i).setDead(120, 260);
						dead_footmen.add(alive_footmen.get(i));
						alive_footmen.remove(i);
						change_troop_counter(-1);
						f--;
					}
				}

				for (int i = 0; i < alive_footmenE.size(); i++) {
					if (alive_footmenE.get(i).getHealth() <= 0) {
						alive_footmenE.get(i).setDead(680, -30);
						dead_footmenE.add(alive_footmenE.get(i));
						alive_footmenE.remove(i);
						troop_counterE--;
						fE--;
					}
				}
				// bowmen
				for (int i = 0; i < alive_bowmen.size(); i++) {
					if (alive_bowmen.get(i).getHealth() <= 0) {
						alive_bowmen.get(i).setDead(120, 260);
						dead_bowmen.add(alive_bowmen.get(i));
						alive_bowmen.remove(i);
						change_troop_counter(-1);
						b--;

					}
				}
				for (int i = 0; i < alive_bowmenE.size(); i++) {
					if (alive_bowmenE.get(i).getHealth() <= 0) {
						alive_bowmenE.get(i).setDead(680, -30);
						alive_bowmenE.get(i).setAttacking(false);
						dead_bowmenE.add(alive_bowmenE.get(i));
						alive_bowmenE.remove(i);
						troop_counterE--;
						bE--;
					}
				}
				// knights
				for (int i = 0; i < alive_knights.size(); i++) {
					if (alive_knights.get(i).getHealth() <= 0) {
						alive_knights.get(i).setDead(120, 260);
						alive_knights.get(i).setAttacking(false);
						dead_knights.add(alive_knights.get(i));
						alive_knights.remove(i);
						change_troop_counter(-1);
						k--;

					}
				}
				for (int i = 0; i < alive_knightsE.size(); i++) {
					if (alive_knightsE.get(i).getHealth() <= 0) {
						alive_knightsE.get(i).setDead(680, -30);
						alive_knightsE.get(i).setAttacking(false);
						dead_knightsE.add(alive_knightsE.get(i));
						alive_knightsE.remove(i);
						troop_counterE--;
						kE--;
					}
				}
				checkAttacking();
			}
		});
		aliveCheck.start();
	}

	//maybe redundant code? could be more efficient/implemented better
	public void checkAttacking() {
		if (alive_footmen.size() == 0 && alive_bowmen.size() == 0 && alive_knights.size() == 0) {
			for (int i = 0; i < alive_footmenE.size(); i++) {
				alive_footmenE.get(i).setAttacking(false);
			}
			for (int i = 0; i < alive_bowmenE.size(); i++) {
				alive_bowmenE.get(i).setAttacking(false);
			}
			for (int i = 0; i < alive_knightsE.size(); i++) {
				alive_knightsE.get(i).setAttacking(false);
			}
		}
		if (alive_footmenE.size() == 0 && alive_bowmenE.size() == 0 && alive_knightsE.size() == 0) {
			for (int i = 0; i < alive_footmen.size(); i++) {
				alive_footmen.get(i).setAttacking(false);
			}
			for (int i = 0; i < alive_bowmen.size(); i++) {
				alive_bowmen.get(i).setAttacking(false);
			}
			for (int i = 0; i < alive_knights.size(); i++) {
				alive_knights.get(i).setAttacking(false);
			}
		}

		for (int i = 0; i < alive_footmen.size(); i++) {
			for (int j = 0; j < alive_footmenE.size(); j++) {
				if (alive_footmen.get(i).getX() == alive_footmenE.get(j).getX() - 40) {
					alive_footmen.get(i).setAttacking(true);
				} else {
					alive_footmen.get(i).setAttacking(false);
				}
			}

			for (int j = 0; j < alive_bowmenE.size(); j++) {
				if (alive_footmen.get(i).getX() == alive_bowmenE.get(j).getX() - 40) {
					alive_footmen.get(i).setAttacking(true);
				} else {
					alive_footmen.get(i).setAttacking(false);
				}
			}

			for (int j = 0; j < alive_knightsE.size(); j++) {
				if (alive_footmen.get(i).getX() == alive_knightsE.get(j).getX() - 40) {
					alive_footmen.get(i).setAttacking(true);
				} else {
					alive_footmen.get(i).setAttacking(false);
				}
			}
		}

		for (int i = 0; i < alive_bowmen.size(); i++) {
			for (int j = 0; j < alive_footmenE.size(); j++) {
				if (alive_bowmen.get(i).getX() >= alive_footmenE.get(j).getX() - 80
						&& alive_bowmen.get(i).getX() <= alive_footmenE.get(j).getX() - 40) {
					alive_bowmen.get(i).setAttacking(true);
				} else {
					alive_bowmen.get(i).setAttacking(false);
				}
			}

			for (int j = 0; j < alive_bowmenE.size(); j++) {
				if (alive_bowmen.get(i).getX() >= alive_bowmenE.get(j).getX() - 80
						&& alive_bowmen.get(i).getX() <= alive_bowmenE.get(j).getX() - 40) {
					alive_bowmen.get(i).setAttacking(true);
				} else {
					alive_bowmen.get(i).setAttacking(false);
				}
			}

			for (int j = 0; j < alive_knightsE.size(); j++) {
				if (alive_bowmen.get(i).getX() >= alive_knightsE.get(j).getX() - 80
						&& alive_bowmen.get(i).getX() <= alive_knightsE.get(j).getX() - 40) {
					alive_bowmen.get(i).setAttacking(true);
				} else {
					alive_bowmen.get(i).setAttacking(false);
				}
			}
		}

		for (int i = 0; i < alive_knights.size(); i++) {
			for (int j = 0; j < alive_footmenE.size(); j++) {
				if (alive_knights.get(i).getX() == alive_footmenE.get(j).getX() - 40) {
					alive_knights.get(i).setAttacking(true);
				} else {
					alive_knights.get(i).setAttacking(false);
				}
			}

			for (int j = 0; j < alive_bowmenE.size(); j++) {
				if (alive_knights.get(i).getX() == alive_bowmenE.get(j).getX() - 40) {
					alive_knights.get(i).setAttacking(true);
				} else {
					alive_knights.get(i).setAttacking(false);
				}
			}

			for (int j = 0; j < alive_knightsE.size(); j++) {
				if (alive_knights.get(i).getX() == alive_knightsE.get(j).getX() - 40) {
					alive_knights.get(i).setAttacking(true);
				} else {
					alive_knights.get(i).setAttacking(false);
				}
			}
		}

		for (int i = 0; i < alive_footmenE.size(); i++) {
			for (int j = 0; j < alive_footmen.size(); j++) {
				if (alive_footmenE.get(i).getX() == alive_footmen.get(j).getX() + 40) {
					alive_footmenE.get(i).setAttacking(true);
				} else {
					alive_footmenE.get(i).setAttacking(false);
				}
			}

			for (int j = 0; j < alive_bowmen.size(); j++) {
				if (alive_footmenE.get(i).getX() == alive_bowmen.get(j).getX() + 40) {
					alive_footmenE.get(i).setAttacking(true);
				} else {
					alive_footmenE.get(i).setAttacking(false);
				}
			}

			for (int j = 0; j < alive_knights.size(); j++) {
				if (alive_footmenE.get(i).getX() == alive_knights.get(j).getX() + 40) {
					alive_footmenE.get(i).setAttacking(true);
				} else {
					alive_footmenE.get(i).setAttacking(false);
				}
			}
		}

		for (int i = 0; i < alive_bowmenE.size(); i++) {
			for (int j = 0; j < alive_footmen.size(); j++) {
				if (alive_bowmenE.get(i).getX() <= alive_footmen.get(j).getX() + 80
						&& alive_bowmenE.get(i).getX() >= alive_footmen.get(j).getX() + 40) {
					alive_bowmenE.get(i).setAttacking(true);
				} else {
					alive_bowmenE.get(i).setAttacking(false);
				}
			}

			for (int j = 0; j < alive_bowmen.size(); j++) {
				if (alive_bowmenE.get(i).getX() <= alive_bowmen.get(j).getX() + 80
						&& alive_bowmenE.get(i).getX() >= alive_bowmen.get(j).getX() + 40) {
					alive_bowmenE.get(i).setAttacking(true);
				} else {
					alive_bowmenE.get(i).setAttacking(false);
				}
			}

			for (int j = 0; j < alive_knights.size(); j++) {
				if (alive_bowmenE.get(i).getX() <= alive_knights.get(j).getX() + 80
						&& alive_bowmenE.get(i).getX() >= alive_knights.get(j).getX() + 40) {
					alive_bowmenE.get(i).setAttacking(true);
				} else {
					alive_bowmenE.get(i).setAttacking(false);
				}
			}
		}

		for (int i = 0; i < alive_knightsE.size(); i++) {
			for (int j = 0; j < alive_footmen.size(); j++) {
				if (alive_knightsE.get(i).getX() == alive_footmen.get(j).getX() + 40) {
					alive_knightsE.get(i).setAttacking(true);
				} else {
					alive_knightsE.get(i).setAttacking(false);
				}
			}

			for (int j = 0; j < alive_bowmen.size(); j++) {
				if (alive_knightsE.get(i).getX() == alive_bowmen.get(j).getX() + 40) {
					alive_knightsE.get(i).setAttacking(true);
				} else {
					alive_knightsE.get(i).setAttacking(false);
				}
			}

			for (int j = 0; j < alive_knights.size(); j++) {
				if (alive_knightsE.get(i).getX() == alive_knights.get(j).getX() + 40) {
					alive_knightsE.get(i).setAttacking(true);
				} else {
					alive_knightsE.get(i).setAttacking(false);
				}
			}
		}

	}

	public void footmenAttack(int i) {
		for (int j = 0; j < alive_footmenE.size(); j++) {
			if (alive_footmen.get(i).getX() == alive_footmenE.get(j).getX() - 40) {
				alive_footmen.get(i).stop();

				if (alive_footmenE.get(j).getHealth() > 0) {
					alive_footmen.get(i).setAttacking(true);
					alive_footmen.get(i).hit();
					alive_footmenE.get(j).setHealth(alive_footmenE.get(j).getHealth() - footman1.getDamage());
				}
			}
		}

		for (int j = 0; j < alive_bowmenE.size(); j++) {
			if (alive_footmen.get(i).getX() == alive_bowmenE.get(j).getX() - 40) {
				alive_footmen.get(i).stop();

				if (alive_bowmenE.get(j).getHealth() > 0) {
					alive_footmen.get(i).setAttacking(true);
					alive_footmen.get(i).hit();
					alive_bowmenE.get(j).setHealth(alive_bowmenE.get(j).getHealth() - footman1.getDamage());
				}
			}
		}

		for (int j = 0; j < alive_knightsE.size(); j++) {
			if (alive_footmen.get(i).getX() == alive_knightsE.get(j).getX() - 40) {
				alive_footmen.get(i).stop();

				if (alive_knightsE.get(j).getHealth() > 0) {
					alive_footmen.get(i).setAttacking(true);
					alive_footmen.get(i).hit();
					alive_knightsE.get(j).setHealth(alive_knightsE.get(j).getHealth() - footman1.getDamage());
				}
			}
		}
	}

	public void bowmenAttack(int i) {
		for (int j = 0; j < alive_footmenE.size(); j++) {
			if (alive_bowmen.get(i).getX() >= alive_footmenE.get(j).getX() - 80
					&& alive_bowmen.get(i).getX() <= alive_footmenE.get(j).getX() - 40) {
				alive_bowmen.get(i).stop();

				if (alive_footmenE.get(j).getHealth() > 0) {
					alive_bowmen.get(i).setAttacking(true);
					alive_bowmen.get(i).hit();
					alive_footmenE.get(j).setHealth(alive_footmenE.get(j).getHealth() - bowman1.getDamage());
				}
			}
		}

		for (int j = 0; j < alive_bowmenE.size(); j++) {
			if (alive_bowmen.get(i).getX() >= alive_bowmenE.get(j).getX() - 80
					&& alive_bowmen.get(i).getX() <= alive_bowmenE.get(j).getX() - 40) {
				alive_bowmen.get(i).stop();

				if (alive_bowmenE.get(j).getHealth() > 0) {
					alive_bowmen.get(i).setAttacking(true);
					alive_bowmen.get(i).hit();
					alive_bowmenE.get(j).setHealth(alive_bowmenE.get(j).getHealth() - bowman1.getDamage());
				}
			}
		}

		for (int j = 0; j < alive_knightsE.size(); j++) {
			if (alive_bowmen.get(i).getX() >= alive_knightsE.get(j).getX() - 80
					&& alive_bowmen.get(i).getX() <= alive_knightsE.get(j).getX() - 40) {
				alive_bowmen.get(i).stop();

				if (alive_knightsE.get(j).getHealth() > 0) {
					alive_bowmen.get(i).setAttacking(true);
					alive_bowmen.get(i).hit();
					alive_knightsE.get(j).setHealth(alive_knightsE.get(j).getHealth() - bowman1.getDamage());
				}
			}
		}
	}

	public void knightsAttack(int i) {
		for (int j = 0; j < alive_footmenE.size(); j++) {
			if (alive_knights.get(i).getX() == alive_footmenE.get(j).getX() - 40) {
				alive_knights.get(i).stop();

				if (alive_footmenE.get(j).getHealth() > 0) {
					alive_knights.get(i).setAttacking(true);
					alive_knights.get(i).hit();
					alive_footmenE.get(j).setHealth(alive_footmenE.get(j).getHealth() - knight1.getDamage());
				}
			}
		}

		for (int j = 0; j < alive_bowmenE.size(); j++) {
			if (alive_knights.get(i).getX() == alive_bowmenE.get(j).getX() - 40) {
				alive_knights.get(i).stop();

				if (alive_bowmenE.get(j).getHealth() > 0) {
					alive_knights.get(i).setAttacking(true);
					alive_knights.get(i).hit();
					alive_bowmenE.get(j).setHealth(alive_bowmenE.get(j).getHealth() - knight1.getDamage());
				}
			}
		}

		for (int j = 0; j < alive_knightsE.size(); j++) {
			if (alive_knights.get(i).getX() == alive_knightsE.get(j).getX() - 40) {
				alive_knights.get(i).stop();

				if (alive_knightsE.get(j).getHealth() > 0) {
					alive_knights.get(i).setAttacking(true);
					alive_knights.get(i).hit();
					alive_knightsE.get(j).setHealth(alive_knightsE.get(j).getHealth() - knight1.getDamage());
				}
			}
		}
	}

	public void footmenAttackE(int i) {
		for (int j = 0; j < alive_footmen.size(); j++) {
			if (alive_footmenE.get(i).getX() == alive_footmen.get(j).getX() + 40) {
				alive_footmenE.get(i).stop();

				if (alive_footmen.get(j).getHealth() > 0) {
					alive_footmenE.get(i).setAttacking(true);
					alive_footmenE.get(i).hit();
					alive_footmen.get(j).setHealth(alive_footmen.get(j).getHealth() - footman1E.getDamage());
				}
			}
		}
		for (int j = 0; j < alive_bowmen.size(); j++) {
			if (alive_footmenE.get(i).getX() == alive_bowmen.get(j).getX() + 40) {
				alive_footmenE.get(i).stop();

				if (alive_bowmen.get(j).getHealth() > 0) {
					alive_footmenE.get(i).setAttacking(true);
					alive_footmenE.get(i).hit();
					alive_bowmen.get(j).setHealth(alive_bowmen.get(j).getHealth() - footman1E.getDamage());
				}
			}
		}
		for (int j = 0; j < alive_knights.size(); j++) {
			if (alive_footmenE.get(i).getX() == alive_knights.get(j).getX() + 40) {
				alive_footmenE.get(i).stop();

				if (alive_knights.get(j).getHealth() > 0) {
					alive_footmenE.get(i).setAttacking(true);
					alive_footmenE.get(i).hit();
					alive_knights.get(j).setHealth(alive_knights.get(j).getHealth() - footman1E.getDamage());
				}
			}
		}
	}

	public void bowmenAttackE(int i) {
		for (int j = 0; j < alive_footmen.size(); j++) {
			if (alive_bowmenE.get(i).getX() <= alive_footmen.get(j).getX() + 80
					&& alive_bowmenE.get(i).getX() >= alive_footmen.get(j).getX() + 40) {
				alive_bowmenE.get(i).stop();

				if (alive_footmen.get(j).getHealth() > 0) {
					alive_bowmenE.get(i).setAttacking(true);
					alive_bowmenE.get(i).hit();
					alive_footmen.get(j).setHealth(alive_footmen.get(j).getHealth() - bowman1E.getDamage());
				}
			}
		}
		for (int j = 0; j < alive_bowmen.size(); j++) {
			if (alive_bowmenE.get(i).getX() <= alive_bowmen.get(j).getX() + 80
					&& alive_bowmenE.get(i).getX() >= alive_bowmen.get(j).getX() + 40) {
				alive_bowmenE.get(i).stop();

				if (alive_bowmen.get(j).getHealth() > 0) {
					alive_bowmenE.get(i).setAttacking(true);
					alive_bowmenE.get(i).hit();
					alive_bowmen.get(j).setHealth(alive_bowmen.get(j).getHealth() - bowman1E.getDamage());
				}
			}
		}
		for (int j = 0; j < alive_knights.size(); j++) {
			if (alive_bowmenE.get(i).getX() <= alive_knights.get(j).getX() + 80
					&& alive_bowmenE.get(i).getX() >= alive_knights.get(j).getX() + 40) {
				alive_bowmenE.get(i).stop();

				if (alive_knights.get(j).getHealth() > 0) {
					alive_bowmenE.get(i).setAttacking(true);
					alive_bowmenE.get(i).hit();
					alive_knights.get(j).setHealth(alive_knights.get(j).getHealth() - bowman1E.getDamage());
				}
			}
		}
	}

	public void knightsAttackE(int i) {
		for (int j = 0; j < alive_footmen.size(); j++) {
			if (alive_knightsE.get(i).getX() == alive_footmen.get(j).getX() + 40) {
				alive_knightsE.get(i).stop();

				if (alive_footmen.get(j).getHealth() > 0) {
					alive_knightsE.get(i).setAttacking(true);
					alive_knightsE.get(i).hit();
					alive_footmen.get(j).setHealth(alive_footmen.get(j).getHealth() - knight1E.getDamage());
				}
			}
		}
		for (int j = 0; j < alive_bowmen.size(); j++) {
			if (alive_knightsE.get(i).getX() == alive_bowmen.get(j).getX() + 40) {
				alive_knightsE.get(i).stop();

				if (alive_bowmen.get(j).getHealth() > 0) {
					alive_knightsE.get(i).setAttacking(true);
					alive_knightsE.get(i).hit();
					alive_bowmen.get(j).setHealth(alive_bowmen.get(j).getHealth() - knight1E.getDamage());
				}
			}
		}
		for (int j = 0; j < alive_knights.size(); j++) {
			if (alive_knightsE.get(i).getX() == alive_knights.get(j).getX() + 40) {
				alive_knightsE.get(i).stop();

				if (alive_knights.get(j).getHealth() > 0) {
					alive_knightsE.get(i).setAttacking(true);
					alive_knightsE.get(i).hit();
					alive_knights.get(j).setHealth(alive_knights.get(j).getHealth() - knight1E.getDamage());
				}
			}
		}
	}

	public void startTimer(boolean b) {
		if (b && !quit) {
			general_timer.start();
			footman_timer.start();
			bowman_timer.start();
			knight_timer.start();
			footman_timerE.start();
			bowman_timerE.start();
			knight_timerE.start();
			bot_timer.start();
		}
	}
}
