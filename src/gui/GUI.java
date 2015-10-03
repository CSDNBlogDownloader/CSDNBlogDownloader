package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import model.Model;
import util.Util;
import controller.Controller;

public class GUI implements PropertyChangeListener {

	private JFrame frame;
	private JTabbedPane model_panel;
	private JPanel user_panel;
	private JPanel url_panel;
	private JPanel category_panel;
	private JTextArea system_textArea;
	private JMenuItem loadConfig_menuItem;
	private JMenuItem saveConfig_menuItem;
	private JMenuItem help_menuItem;
	private JProgressBar progressBar;
	
	private JLabel profile_label;
	private JTextField visited_textField;
	private JTextField score_textField;
	private JTextField rank_textField;
	private JTextField original_textField;
	private JTextField repost_textField;
	private JTextField translate_textField;
	private JTextField comment_textField;
	private JTextField user_textField;
	private JTextField userpanel_root_textField;
	
	private JButton userpanel_start_button;
	private JButton userpanel_root_button;
	private JButton userpanel_openRoot_button;
	private JButton userpanel_abort_button;
	private JButton userpanel_reset_button;
	private JButton browser_button;
	
	private JButton categorypanel_start_button;
	private JButton categorypanel_root_button;
	private JButton categorypanel_openRoot_button;
	private JButton categorypanel_abort_button;
	private JButton categorypanel_clear_button;
	private JTextField categorypanel_root_textField;
	private JTextArea categorypanel_input_textArea;
	
	private JButton urlpanel_start_button;
	private JButton urlpanel_root_button;
	private JButton urlpanel_openRoot_button;
	private JButton urlpanel_abort_button;
	private JButton urlpanel_clear_button;
	private JTextField urlpanel_root_textField;
	private JTextArea urlpanel_input_textArea;

	private Controller controller;

	/**
	 * 启动图形界面
	 */
	public void GUIStart() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 创建图形界面
	 * @param controller 控制器
	 */
	public GUI(Controller controller) {
		this.controller = controller;
		initialize();
	}

	/**
	 * 绘制界面
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setType(Type.POPUP);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/icons/title.jpg")));
		frame.setTitle("CSDN\u535A\u5BA2\u4E0B\u8F7D\u5668\u2014By Geurney");
		frame.setBounds(100, 100, 654, 527);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu file_menu = new JMenu("\u6587\u4EF6");
		file_menu.setIcon(null);
		menuBar.add(file_menu);
		
		JMenuItem exit_menuItem = new JMenuItem("\u9000\u51FA");
		exit_menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.exit();
			}
		});
		exit_menuItem.setIcon(new ImageIcon(GUI.class.getResource("/icons/exit.png")));
		file_menu.add(exit_menuItem);
		
		JMenu config_menu = new JMenu("\u8BBE\u7F6E");
		config_menu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				controller.updateConfigMenu();
			}
		});
		menuBar.add(config_menu);
		
		loadConfig_menuItem = new JMenuItem("\u8F7D\u5165\u7528\u6237");
		loadConfig_menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.loadConfig();
			}
		});
		loadConfig_menuItem.setIcon(new ImageIcon(GUI.class.getResource("/icons/smile.png")));
		config_menu.add(loadConfig_menuItem);
		
		saveConfig_menuItem = new JMenuItem("\u4FDD\u5B58\u7528\u6237");
		saveConfig_menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveConfig();
			}
		});
		saveConfig_menuItem.setIcon(new ImageIcon(GUI.class.getResource("/icons/configure.png")));
		config_menu.add(saveConfig_menuItem);
		
		JMenu help_menu = new JMenu("\u5E2E\u52A9");
		help_menu.setIcon(null);
		menuBar.add(help_menu);
		
		help_menuItem = new JMenuItem("\u5E2E\u52A9...");
		help_menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.updateHelp();
			}
		});
		help_menuItem.setIcon(new ImageIcon(GUI.class.getResource("/icons/help.png")));
		help_menu.add(help_menuItem);
		
		JMenu about_menu = new JMenu("\u5173\u4E8E");
		menuBar.add(about_menu);
		
		JMenuItem about_menuItem = new JMenuItem("\u5173\u4E8E...");
		about_menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    JOptionPane.showMessageDialog(frame,
			            "CSDB博客下载器 v2.0\n作者：Geurney",
			            "关于",
			            JOptionPane.INFORMATION_MESSAGE, new ImageIcon(GUI.class.getResource("/icons/icon.jpg")));
			}
		});
		about_menuItem.setIcon(new ImageIcon(GUI.class.getResource("/icons/about.png")));
		about_menu.add(about_menuItem);
		
		JSeparator separator = new JSeparator();
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		
		JScrollPane	system_Pane = new JScrollPane();
		system_Pane.setViewportBorder(UIManager.getBorder("ScrollPane.border"));
		
		system_textArea = new JTextArea();
		system_textArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getButton() == MouseEvent.BUTTON3) {
					JPopupMenu rightMenu = new JPopupMenu();
					JMenuItem rightMenuItem = new JMenuItem("清空");
					if (controller.isBusy()) {
						rightMenuItem.setEnabled(false);
					}
					rightMenuItem.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
								system_textArea.setText("");
								progressBar.setValue(0);
						}
					});
					rightMenu.add(rightMenuItem);
					rightMenu.show(event.getComponent(), event.getX(), event.getY());
				}
			}
		});
		system_textArea.setEditable(false);
		system_Pane.setViewportView(system_textArea);
		system_textArea.setWrapStyleWord(true);
		system_textArea.setLineWrap(true);
		
		model_panel = new JTabbedPane(JTabbedPane.TOP);
		model_panel.setToolTipText("");
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
							.addGap(39))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(system_Pane)
								.addComponent(model_panel, GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(33))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(model_panel, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
							.addGap(149))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(system_Pane, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)))
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(19))
		);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);
		frame.getContentPane().setLayout(groupLayout);
		
		/*********************************************用户模式***********************************************/
		user_panel = new JPanel();
		model_panel.addTab("\u4E0B\u8F7D\u7528\u6237", null, user_panel, null);
		user_panel.setLayout(new GridLayout(0, 2, 6, 0));
		
		/*****************用户模式博客信息栏*********************/
		JPanel info_panel = new JPanel();
		info_panel.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		
		profile_label = new JLabel("\u5934\u50CF");
		profile_label.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel userInfo_panel = new JPanel();
		userInfo_panel.setForeground(Color.DARK_GRAY);
		GridBagLayout gbl_userInfo_panel = new GridBagLayout();
		gbl_userInfo_panel.columnWidths = new int[] {38, 78, 0};
		gbl_userInfo_panel.rowHeights = new int[]{20, 20, 20, 0};
		gbl_userInfo_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_userInfo_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		userInfo_panel.setLayout(gbl_userInfo_panel);
		
		JLabel visited_label = new JLabel("\u8BBF\u95EE");
		visited_label.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_visited_label = new GridBagConstraints();
		gbc_visited_label.fill = GridBagConstraints.BOTH;
		gbc_visited_label.gridx = 0;
		gbc_visited_label.gridy = 0;
		userInfo_panel.add(visited_label, gbc_visited_label);
		
		visited_textField = new JTextField();
		visited_textField.setEditable(false);
		GridBagConstraints gbc_visited_textField = new GridBagConstraints();
		gbc_visited_textField.fill = GridBagConstraints.BOTH;
		gbc_visited_textField.gridx = 1;
		gbc_visited_textField.gridy = 0;
		userInfo_panel.add(visited_textField, gbc_visited_textField);
		visited_textField.setColumns(10);
		
		JLabel score_label = new JLabel("\u79EF\u5206");
		score_label.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_score_label = new GridBagConstraints();
		gbc_score_label.fill = GridBagConstraints.BOTH;
		gbc_score_label.gridx = 0;
		gbc_score_label.gridy = 1;
		userInfo_panel.add(score_label, gbc_score_label);
		
		score_textField = new JTextField();
		score_textField.setEditable(false);
		GridBagConstraints gbc_score_textField = new GridBagConstraints();
		gbc_score_textField.fill = GridBagConstraints.BOTH;
		gbc_score_textField.gridx = 1;
		gbc_score_textField.gridy = 1;
		userInfo_panel.add(score_textField, gbc_score_textField);
		score_textField.setColumns(10);
		
		JLabel rank_label = new JLabel("\u6392\u540D");
		rank_label.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_rank_label = new GridBagConstraints();
		gbc_rank_label.fill = GridBagConstraints.BOTH;
		gbc_rank_label.gridx = 0;
		gbc_rank_label.gridy = 2;
		userInfo_panel.add(rank_label, gbc_rank_label);
		
		JPanel blogInfo_panel = new JPanel();
		GridBagLayout gbl_blogInfo_panel = new GridBagLayout();
		gbl_blogInfo_panel.columnWidths = new int[] {38, 78, 0};
		gbl_blogInfo_panel.rowHeights = new int[]{22, 22, 22, 22, 0};
		gbl_blogInfo_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_blogInfo_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		blogInfo_panel.setLayout(gbl_blogInfo_panel);
		
		JLabel original_label = new JLabel("\u539F\u521B");
		original_label.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_original_label = new GridBagConstraints();
		gbc_original_label.fill = GridBagConstraints.BOTH;
		gbc_original_label.gridx = 0;
		gbc_original_label.gridy = 0;
		blogInfo_panel.add(original_label, gbc_original_label);
		
		original_textField = new JTextField();
		original_textField.setEditable(false);
		GridBagConstraints gbc_original_textField = new GridBagConstraints();
		gbc_original_textField.fill = GridBagConstraints.BOTH;
		gbc_original_textField.gridx = 1;
		gbc_original_textField.gridy = 0;
		blogInfo_panel.add(original_textField, gbc_original_textField);
		original_textField.setColumns(10);
		
		JLabel repost_label = new JLabel("\u8F6C\u8F7D");
		repost_label.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_repost_label = new GridBagConstraints();
		gbc_repost_label.fill = GridBagConstraints.BOTH;
		gbc_repost_label.gridx = 0;
		gbc_repost_label.gridy = 1;
		blogInfo_panel.add(repost_label, gbc_repost_label);
		
		repost_textField = new JTextField();
		repost_textField.setEditable(false);
		GridBagConstraints gbc_repost_textField = new GridBagConstraints();
		gbc_repost_textField.fill = GridBagConstraints.BOTH;
		gbc_repost_textField.gridx = 1;
		gbc_repost_textField.gridy = 1;
		blogInfo_panel.add(repost_textField, gbc_repost_textField);
		repost_textField.setColumns(10);
		
		JLabel translate_label = new JLabel("\u8BD1\u6587");
		translate_label.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_translate_label = new GridBagConstraints();
		gbc_translate_label.fill = GridBagConstraints.BOTH;
		gbc_translate_label.gridx = 0;
		gbc_translate_label.gridy = 2;
		blogInfo_panel.add(translate_label, gbc_translate_label);
		
		translate_textField = new JTextField();
		translate_textField.setEditable(false);
		GridBagConstraints gbc_translate_textField = new GridBagConstraints();
		gbc_translate_textField.fill = GridBagConstraints.BOTH;
		gbc_translate_textField.gridx = 1;
		gbc_translate_textField.gridy = 2;
		blogInfo_panel.add(translate_textField, gbc_translate_textField);
		translate_textField.setColumns(10);
		
		JLabel comment_label = new JLabel("\u8BC4\u8BBA");
		comment_label.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_comment_label = new GridBagConstraints();
		gbc_comment_label.fill = GridBagConstraints.BOTH;
		gbc_comment_label.gridx = 0;
		gbc_comment_label.gridy = 3;
		blogInfo_panel.add(comment_label, gbc_comment_label);
		GroupLayout gl_info_panel = new GroupLayout(info_panel);
		gl_info_panel.setHorizontalGroup(
			gl_info_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_info_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(profile_label, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_info_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(userInfo_panel, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
						.addComponent(blogInfo_panel, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_info_panel.setVerticalGroup(
			gl_info_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_info_panel.createSequentialGroup()
					.addGroup(gl_info_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_info_panel.createSequentialGroup()
							.addGap(14)
							.addComponent(profile_label, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_info_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(userInfo_panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(blogInfo_panel, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(23, Short.MAX_VALUE))
		);
		
		comment_textField = new JTextField();
		comment_textField.setEditable(false);
		GridBagConstraints gbc_comment_textField = new GridBagConstraints();
		gbc_comment_textField.fill = GridBagConstraints.BOTH;
		gbc_comment_textField.gridx = 1;
		gbc_comment_textField.gridy = 3;
		blogInfo_panel.add(comment_textField, gbc_comment_textField);
		comment_textField.setColumns(10);
		
		rank_textField = new JTextField();
		rank_textField.setEditable(false);
		GridBagConstraints gbc_rank_textField = new GridBagConstraints();
		gbc_rank_textField.fill = GridBagConstraints.BOTH;
		gbc_rank_textField.gridx = 1;
		gbc_rank_textField.gridy = 2;
		userInfo_panel.add(rank_textField, gbc_rank_textField);
		rank_textField.setColumns(10);
		info_panel.setLayout(gl_info_panel);
		user_panel.add(info_panel);
		
		/*****************用户模式控制栏*********************/
		JPanel config_panel = new JPanel();
		user_panel.add(config_panel);
		config_panel.setBorder(UIManager.getBorder("FormattedTextField.border"));
		
		JLabel user_label = new JLabel("\u7528\u6237");
		user_label.setHorizontalAlignment(SwingConstants.CENTER);
		user_label.setIcon(new ImageIcon(GUI.class.getResource("/icons/user.png")));
		
		user_textField = new JTextField();
		user_textField.setColumns(10);
		
		JLabel userpanel_root_label = new JLabel("\u672C\u5730");
		userpanel_root_label.setIcon(new ImageIcon(GUI.class.getResource("/icons/save.png")));
		
		userpanel_root_textField = new JTextField();
		userpanel_root_textField.setColumns(10);
		
		userpanel_root_button = new JButton("\u6D4F\u89C8...");
		userpanel_root_button.setHorizontalAlignment(SwingConstants.LEFT);
		userpanel_root_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 JFileChooser chooser = new JFileChooser();
				 chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				 int retval = chooser.showOpenDialog(userpanel_root_button);
				 if (retval == JFileChooser.APPROVE_OPTION) {
					 userpanel_root_textField.setText(chooser.getSelectedFile().getPath());
				 }
			}
		});
		userpanel_root_button.setIcon(new ImageIcon(GUI.class.getResource("/icons/search.png")));
		
		userpanel_start_button = new JButton("\u4E0B\u8F7D");
		userpanel_start_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = user_textField.getText();
				if (name.trim().length() == 0) {
					JOptionPane.showMessageDialog(frame,
						    "请输入用户名！",
						    "警告",
						    JOptionPane.WARNING_MESSAGE);
					return;
				}
				String root = userpanel_root_textField.getText();
				if (root.trim().length() == 0) {
					JOptionPane.showMessageDialog(frame,
						    "请输入下载目录！",
						    "警告",
						    JOptionPane.WARNING_MESSAGE);
					return;
				}
				controller.userPanelStart(name, root);
			}
		});
		userpanel_start_button.setIcon(new ImageIcon(GUI.class.getResource("/icons/start.png")));
		
		userpanel_openRoot_button = new JButton("\u67E5\u770B\u76EE\u5F55");
		userpanel_openRoot_button.setIcon(new ImageIcon(GUI.class.getResource("/icons/open.png")));
		userpanel_openRoot_button.setEnabled(false);
		userpanel_openRoot_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Util.openExplorer(userpanel_root_textField.getText());
			}
		});
		
		userpanel_abort_button = new JButton("\u7EC8\u6B62");
		userpanel_abort_button.setIcon(new ImageIcon(GUI.class.getResource("/icons/abort.png")));
		userpanel_abort_button.setEnabled(false);
    	userpanel_abort_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.cancel();
			}
    	});
		
		userpanel_reset_button = new JButton("\u91CD\u7F6E");
		userpanel_reset_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.userpanelReset();
			}
		});
		userpanel_reset_button.setIcon(new ImageIcon(GUI.class.getResource("/icons/reset.png")));
		
		browser_button = new JButton("\u8BBF\u95EE");
		browser_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = user_textField.getText();
				if (name.trim().length() == 0) {
					JOptionPane.showMessageDialog(frame,
						    "请输入用户名！",
						    "警告",
						    JOptionPane.WARNING_MESSAGE);
					return;
				}
				Util.openBrowser(Model.host + name);
			}
		});
		browser_button.setIcon(new ImageIcon(GUI.class.getResource("/icons/browser.png")));
		GroupLayout gl_config_panel = new GroupLayout(config_panel);
		gl_config_panel.setHorizontalGroup(
			gl_config_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_config_panel.createSequentialGroup()
					.addGroup(gl_config_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_config_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_config_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_config_panel.createSequentialGroup()
									.addComponent(userpanel_root_label)
									.addGap(5)
									.addComponent(userpanel_root_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(userpanel_root_button))
								.addGroup(gl_config_panel.createSequentialGroup()
									.addComponent(user_label)
									.addGap(5)
									.addComponent(user_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(browser_button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
						.addGroup(gl_config_panel.createSequentialGroup()
							.addGap(21)
							.addGroup(gl_config_panel.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(userpanel_abort_button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(userpanel_start_button, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))
							.addGap(18, 27, Short.MAX_VALUE)
							.addGroup(gl_config_panel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(userpanel_reset_button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(userpanel_openRoot_button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addContainerGap(105, Short.MAX_VALUE))
		);
		gl_config_panel.setVerticalGroup(
			gl_config_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_config_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_config_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_config_panel.createSequentialGroup()
							.addGap(1)
							.addComponent(user_label))
						.addGroup(gl_config_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(user_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(browser_button)))
					.addGap(23)
					.addGroup(gl_config_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_config_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(userpanel_root_label))
						.addGroup(gl_config_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(userpanel_root_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(userpanel_root_button)))
					.addGap(18)
					.addGroup(gl_config_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(userpanel_openRoot_button)
						.addComponent(userpanel_start_button))
					.addGap(18)
					.addGroup(gl_config_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(userpanel_abort_button)
						.addComponent(userpanel_reset_button))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		config_panel.setLayout(gl_config_panel);
		
		/*********************************************文章模式***********************************************/
		url_panel = new JPanel();
		model_panel.addTab("\u4E0B\u8F7D\u6587\u7AE0", null, url_panel, null);

		JScrollPane urlpanel_input_scrollPane = new JScrollPane();
		
		urlpanel_input_textArea = new JTextArea();
		urlpanel_input_scrollPane.setViewportView(urlpanel_input_textArea);
		
		JLabel urlpanel_root_label = new JLabel("\u672C\u5730");
		urlpanel_root_label.setIcon(new ImageIcon(GUI.class.getResource("/icons/save.png")));
		
		urlpanel_root_textField = new JTextField();
		urlpanel_root_textField.setColumns(10);
		
		urlpanel_root_button = new JButton("\u6D4F\u89C8...");
		urlpanel_root_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 JFileChooser chooser = new JFileChooser();
				 chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				 int retval=chooser.showOpenDialog(urlpanel_root_button);
				 if (retval == JFileChooser.APPROVE_OPTION) {
					 urlpanel_root_textField.setText(chooser.getSelectedFile().getPath());
				 }
			}
		});
		urlpanel_root_button.setIcon(new ImageIcon(GUI.class.getResource("/icons/search.png")));
		urlpanel_root_button.setHorizontalAlignment(SwingConstants.LEFT);
		
		urlpanel_openRoot_button = new JButton("\u67E5\u770B\u76EE\u5F55");
		urlpanel_openRoot_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.openExplorer(urlpanel_root_textField.getText());
			}
		});
		urlpanel_openRoot_button.setIcon(new ImageIcon(GUI.class.getResource("/icons/open.png")));
		urlpanel_openRoot_button.setEnabled(false);
		
		urlpanel_abort_button = new JButton("\u7EC8\u6B62");
		urlpanel_abort_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.cancel();
			}
		});
		urlpanel_abort_button.setIcon(new ImageIcon(GUI.class.getResource("/icons/abort.png")));
		urlpanel_abort_button.setEnabled(false);

		urlpanel_start_button = new JButton("\u4E0B\u8F7D");
		urlpanel_start_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String root = urlpanel_root_textField.getText();
				if (root.trim().length() == 0) {
					JOptionPane.showMessageDialog(frame,
						    "请输入下载目录！",
						    "警告",
						    JOptionPane.WARNING_MESSAGE);
					return;
				}
				String urls = urlpanel_input_textArea.getText();
				if (urls.trim().length() == 0) {
					JOptionPane.showMessageDialog(frame,
						    "请输入文章链接！",
						    "警告",
						    JOptionPane.WARNING_MESSAGE);
					return;
				}
				controller.urlpanelStart(root, urls);
			}
		});
		urlpanel_start_button.setIcon(new ImageIcon(GUI.class.getResource("/icons/start.png")));

		urlpanel_clear_button = new JButton("\u6E05\u7A7A");
		urlpanel_clear_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.urlpanelClear();
			}
		});
		urlpanel_clear_button.setIcon(new ImageIcon(GUI.class.getResource("/icons/reset.png")));
		GroupLayout gl_url_panel = new GroupLayout(url_panel);
		gl_url_panel.setHorizontalGroup(
			gl_url_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(urlpanel_input_scrollPane, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
				.addGroup(gl_url_panel.createSequentialGroup()
					.addGap(22)
					.addComponent(urlpanel_root_label, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(urlpanel_root_textField, GroupLayout.PREFERRED_SIZE, 332, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(urlpanel_root_button)
					.addContainerGap(63, Short.MAX_VALUE))
				.addGroup(gl_url_panel.createSequentialGroup()
					.addGap(60)
					.addComponent(urlpanel_start_button, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(urlpanel_abort_button, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(urlpanel_clear_button, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(urlpanel_openRoot_button, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(87, Short.MAX_VALUE))
		);
		gl_url_panel.setVerticalGroup(
			gl_url_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_url_panel.createSequentialGroup()
					.addComponent(urlpanel_input_scrollPane, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_url_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(urlpanel_root_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(urlpanel_root_label, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addComponent(urlpanel_root_button, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_url_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(urlpanel_start_button, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(urlpanel_abort_button, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(urlpanel_clear_button)
						.addComponent(urlpanel_openRoot_button, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(12, Short.MAX_VALUE))
		);
		url_panel.setLayout(gl_url_panel);
		
		/*********************************************分类模式***********************************************/
		category_panel = new JPanel();
		model_panel.addTab("下载分类", null, category_panel, null);
		
		JScrollPane categorypanel_input_scrollPane = new JScrollPane();
		
		JLabel categorypanel_root_label = new JLabel("\u672C\u5730");
		categorypanel_root_label.setIcon(new ImageIcon(GUI.class.getResource("/icons/save.png")));
		
		categorypanel_root_textField = new JTextField();
		categorypanel_root_textField.setColumns(10);
		
		categorypanel_root_button = new JButton("\u6D4F\u89C8...");
		categorypanel_root_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 JFileChooser chooser = new JFileChooser();
				 chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				 int retval=chooser.showOpenDialog(categorypanel_root_button);
				 if (retval == JFileChooser.APPROVE_OPTION) {
					 categorypanel_root_textField.setText(chooser.getSelectedFile().getPath());
				 }
			}
		});
		categorypanel_root_button.setIcon(new ImageIcon(GUI.class.getResource("/icons/search.png")));
		categorypanel_root_button.setHorizontalAlignment(SwingConstants.LEFT);
		
		categorypanel_start_button = new JButton("\u4E0B\u8F7D");
		categorypanel_start_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String root = categorypanel_root_textField.getText();
				if (root.trim().length() == 0) {
					JOptionPane.showMessageDialog(frame,
						    "请输入下载目录！",
						    "警告",
						    JOptionPane.WARNING_MESSAGE);
					return;
				}
				String categories = categorypanel_input_textArea.getText();
				if (categories.trim().length() == 0) {
					JOptionPane.showMessageDialog(frame,
						    "请输入分类链接！",
						    "警告",
						    JOptionPane.WARNING_MESSAGE);
					return;
				}
				controller.categorypanelStart(root, categories);
			}
		});
		categorypanel_start_button.setIcon(new ImageIcon(GUI.class.getResource("/icons/start.png")));
		
		categorypanel_abort_button = new JButton("\u7EC8\u6B62");
		categorypanel_abort_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				controller.cancel();
			}
		});
		categorypanel_abort_button.setIcon(new ImageIcon(GUI.class.getResource("/icons/abort.png")));
		categorypanel_abort_button.setEnabled(false);

		categorypanel_clear_button = new JButton("\u6E05\u7A7A");
		categorypanel_clear_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.categorypanelClear();
			}
		});
		categorypanel_clear_button.setIcon(new ImageIcon(GUI.class.getResource("/icons/reset.png")));
		
		categorypanel_openRoot_button = new JButton("\u67E5\u770B\u76EE\u5F55");
		categorypanel_openRoot_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.openExplorer(categorypanel_root_textField.getText());
			}
		});
		categorypanel_openRoot_button.setIcon(new ImageIcon(GUI.class.getResource("/icons/open.png")));
		categorypanel_openRoot_button.setEnabled(false);
		GroupLayout gl_category_panel = new GroupLayout(category_panel);
		gl_category_panel.setHorizontalGroup(
			gl_category_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(categorypanel_input_scrollPane, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
				.addGroup(gl_category_panel.createSequentialGroup()
					.addGap(22)
					.addComponent(categorypanel_root_label, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(categorypanel_root_textField, GroupLayout.PREFERRED_SIZE, 332, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(categorypanel_root_button)
					.addContainerGap(63, Short.MAX_VALUE))
				.addGroup(gl_category_panel.createSequentialGroup()
					.addGap(60)
					.addComponent(categorypanel_start_button, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(categorypanel_abort_button, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(categorypanel_clear_button, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(categorypanel_openRoot_button, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(87, Short.MAX_VALUE))
		);
		gl_category_panel.setVerticalGroup(
			gl_category_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_category_panel.createSequentialGroup()
					.addComponent(categorypanel_input_scrollPane, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_category_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(categorypanel_root_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(categorypanel_root_label, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addComponent(categorypanel_root_button, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_category_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(categorypanel_start_button, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(categorypanel_abort_button, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(categorypanel_clear_button)
						.addComponent(categorypanel_openRoot_button, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(12, Short.MAX_VALUE))
		);
		
		categorypanel_input_textArea = new JTextArea();
		categorypanel_input_scrollPane.setViewportView(categorypanel_input_textArea);
		category_panel.setLayout(gl_category_panel);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String property = event.getPropertyName();
		switch (property) {
			case Model.STATE_BLOGINFO : {
				@SuppressWarnings("unchecked")
				List<String> blogInfo = (List<String>) event.getNewValue();
				userpanelUpdateBloginfo(blogInfo);
			} break;
			case Model.STATE_PROFILE : {
				String imgUrl = (String)event.getNewValue();
				profile_label.setIcon(new ImageIcon(imgUrl));
			}break;
			case Model.STATE_SYSTEM : {
				String systemInfo = (String)event.getNewValue();
				system_textArea.append(systemInfo);
			} break;
			case Model.STATE_PROGRESS : {
				int progress = (int)event.getNewValue();
				progressBar.setValue(progress);
			}break;
			case Model.USER_STATE_DONE :{
				String systemInfo = (String)event.getNewValue();
				system_textArea.append(systemInfo);
				userpanelDone();
			}break;
			case Model.URL_STATE_DONE :{
				String systemInfo = (String)event.getNewValue();
				system_textArea.append(systemInfo);
				urlpanelDone();
			}break;
			case Model.CATEGORY_STATE_DONE :{
				String systemInfo = (String)event.getNewValue();
				system_textArea.append(systemInfo);
				categorypanelDone();
			}break;
		}
	}
	
	/***********************菜单栏方法********************/
	/**
	 * 更新配置菜单
	 */
	public void updateConfigMenu() {
		Component currentTab = model_panel.getSelectedComponent();
		if(currentTab == user_panel) {
			loadConfig_menuItem.setText("载入用户");
			saveConfig_menuItem.setText("保存用户");
		} else if (currentTab == url_panel) {
			loadConfig_menuItem.setText("导入链接");
			saveConfig_menuItem.setText("保存链接");
		} else {
			loadConfig_menuItem.setText("导入分类");
			saveConfig_menuItem.setText("保存分类");
		}
	}
	
	/**
	 * 载入配置
	 */
	public void loadConfig() {
		Component currentTab = model_panel.getSelectedComponent();
		if(currentTab == user_panel) {
			try {
				String[] info = Util.readFile("config.ini").trim().split(",");
				if (info.length != 2) {
					JOptionPane.showMessageDialog(frame,
						    "无法解析用户配置文件内容！",
						    "配置错误",
						    JOptionPane.ERROR_MESSAGE);
					return;
				}
				user_textField.setText(info[0]);
				userpanel_root_textField.setText(info[1]);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame,
					    "无法打开用户配置文件！",
					    "配置错误",
					    JOptionPane.ERROR_MESSAGE);
			}
		} else if (currentTab == url_panel) {
			try {
				JFileChooser chooser = new JFileChooser(".");
				int retval = chooser.showOpenDialog(loadConfig_menuItem);
				if (retval == JFileChooser.APPROVE_OPTION) {
					String configPath = chooser.getSelectedFile().getPath();
					urlpanel_input_textArea.setText(Util.readFile(configPath));
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame,
					    "无法打开链接文件！",
					    "配置错误",
					    JOptionPane.ERROR_MESSAGE);
				}
		} else {
			try {
				JFileChooser chooser = new JFileChooser(".");
				int retval = chooser.showOpenDialog(loadConfig_menuItem);
				if (retval == JFileChooser.APPROVE_OPTION) {
					String configPath = chooser.getSelectedFile().getPath();
					categorypanel_input_textArea.setText(Util.readFile(configPath));
				} else {
					JOptionPane.showMessageDialog(frame,
						    "分类文件路径错误！",
						    "配置错误",
						    JOptionPane.ERROR_MESSAGE);
					return;
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame,
					    "无法打开分类文件！",
					    "配置错误",
					    JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * 保存配置
	 */
	public void saveConfig() {
		Component currentTab = model_panel.getSelectedComponent();
		if(currentTab == user_panel) {
			String name = user_textField.getText();
			if (name.trim().length() == 0) {
				JOptionPane.showMessageDialog(frame,
					    "请输入用户名！",
					    "警告",
					    JOptionPane.WARNING_MESSAGE);
				return;
			}
			String root = userpanel_root_textField.getText();
			if (root.trim().length() == 0) {
				JOptionPane.showMessageDialog(frame,
					    "请输入下载目录！",
					    "警告",
					    JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (Util.writeToFile("config.ini", name + "," + root)) {
				JOptionPane.showMessageDialog(frame,
					        "配置保存成功！",
					        "保存配置",
					        JOptionPane.PLAIN_MESSAGE);
				return;
			} else {
				JOptionPane.showMessageDialog(frame,
					    "文件写入失败！",
					    "配置错误",
					    JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else if (currentTab == url_panel) {
			String urls = urlpanel_input_textArea.getText();
			if (urls.trim().length() == 0) {
				JOptionPane.showMessageDialog(frame,
					    "请输入链接！",
					    "警告",
					    JOptionPane.WARNING_MESSAGE);
				return;
			}
			JFileChooser chooser = new JFileChooser(".");
			int retval = chooser.showOpenDialog(loadConfig_menuItem);
			if (retval == JFileChooser.APPROVE_OPTION) {
				String configPath = chooser.getSelectedFile().getPath();
				if (Util.writeToFile(configPath, urls)) {
					JOptionPane.showMessageDialog(frame,
					        "配置保存成功！",
					        "保存配置",
					        JOptionPane.PLAIN_MESSAGE);
					return;
				} else {
				JOptionPane.showMessageDialog(frame,
					    "文件写入失败！",
					    "配置错误",
					    JOptionPane.ERROR_MESSAGE);
				return;
				}
			} else {
				JOptionPane.showMessageDialog(frame,
					    "文件路径错误！",
					    "配置错误",
					    JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else {
			String categories = categorypanel_input_textArea.getText();
			if (categories.trim().length() == 0) {
				JOptionPane.showMessageDialog(frame,
					    "请输入链接！",
					    "警告",
					    JOptionPane.WARNING_MESSAGE);
				return;
			}
			JFileChooser chooser = new JFileChooser(".");
			int retval = chooser.showOpenDialog(loadConfig_menuItem);
			if (retval == JFileChooser.APPROVE_OPTION) {
				String configPath = chooser.getSelectedFile().getPath();
				if (Util.writeToFile(configPath, categories)) {
					JOptionPane.showMessageDialog(frame,
					        "配置保存成功！",
					        "保存配置",
					        JOptionPane.PLAIN_MESSAGE);
					return;
				} else {
				JOptionPane.showMessageDialog(frame,
					    "文件写入失败！",
					    "配置错误",
					    JOptionPane.ERROR_MESSAGE);
				return;
				}
			} else {
				JOptionPane.showMessageDialog(frame,
					    "文件路径错误！",
					    "配置错误",
					    JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
	
	/**
	 * 更新帮助
	 */
	public void updateHelp() {
		Component currentTab = model_panel.getSelectedComponent();
		if(currentTab == user_panel) {
		   JOptionPane.showMessageDialog(frame,
			        "1. 输入用户名\n2. 指定下载目录\n3. 点击下载\n4. 查看目录",
			        "用户下载使用说明",
			        JOptionPane.PLAIN_MESSAGE);
		} else if (currentTab == url_panel) {
		   JOptionPane.showMessageDialog(frame,
			        "1. 输入博客链接\n2. 指定下载目录\n3. 点击下载\n4. 查看目录",
			        "文章下载使用说明",
			        JOptionPane.PLAIN_MESSAGE);
		} else {
		   JOptionPane.showMessageDialog(frame,
			        "1. 输入分类链接\n2. 指定下载目录\n3. 点击下载\n4. 查看目录",
			        "分类下载使用说明",
			        JOptionPane.PLAIN_MESSAGE);					
		}
	}
	
	/**********************模式方法****************************/
	
	/************用户模式方法*************/
	/**
	 * 用户模式启动
	 */
	public void userpanelStart() {
	    frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	    userpanel_start_button.setEnabled(false);
	    userpanel_root_textField.setEditable(false);
	    user_textField.setEditable(false);
	    userpanel_root_button.setEnabled(false);
	    userpanel_openRoot_button.setEnabled(true);
	    userpanel_reset_button.setEnabled(false);
	    userpanel_abort_button.setEnabled(true);
	    loadConfig_menuItem.setEnabled(false);
	    progressBar.setValue(0);
	    model_panel.setEnabledAt(1, false);
	    model_panel.setEnabledAt(2, false);
	}
	
	/**
	 * 用户模式重置
	 */
	public void userpanelReset() {
		user_textField.setText("");
		userpanel_root_textField.setText("");
		visited_textField.setText("");
		score_textField.setText("");
		rank_textField.setText("");
		original_textField.setText("");
		repost_textField.setText("");
		translate_textField.setText("");
		comment_textField.setText("");
		profile_label.setIcon(null);
		progressBar.setValue(0);
		userpanel_openRoot_button.setEnabled(false);
	}
	
	/**
	 * 更新用户模式的博客信息
	 * @param blogInfo 博客信息
	 */
	public void userpanelUpdateBloginfo(List<String> blogInfo) {
		visited_textField.setText(blogInfo.get(0));
		score_textField.setText(blogInfo.get(1));
		rank_textField.setText(blogInfo.get(2));
		original_textField.setText(blogInfo.get(3));
		repost_textField.setText(blogInfo.get(4));
		translate_textField.setText(blogInfo.get(5));
		comment_textField.setText(blogInfo.get(6));
	}
	
	/**
	 * 用户模式工作结束
	 */
	public void userpanelDone() {
		frame.setCursor(null);
    	userpanel_start_button.setEnabled(true);
    	userpanel_root_textField.setEditable(true);
    	user_textField.setEditable(true);
    	userpanel_root_button.setEnabled(true);
    	userpanel_reset_button.setEnabled(true);
    	userpanel_abort_button.setEnabled(false);
    	loadConfig_menuItem.setEnabled(true);
	    model_panel.setEnabledAt(1, true);
	    model_panel.setEnabledAt(2, true);
	}
	
	/*************文章模式方法*************/
	/**
	 * 文章模式启动
	 */
	public void urlpanelStart() {
	  	frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	    urlpanel_start_button.setEnabled(false);
	    urlpanel_root_textField.setEditable(false);
	    urlpanel_root_button.setEnabled(false);
	    urlpanel_openRoot_button.setEnabled(true);
	    urlpanel_clear_button.setEnabled(false);
	    urlpanel_input_textArea.setEditable(false);
	    urlpanel_abort_button.setEnabled(true);
	    loadConfig_menuItem.setEnabled(false);
	    progressBar.setValue(0);
	    model_panel.setEnabledAt(0, false);
	    model_panel.setEnabledAt(2, false);
	}
	
	/**
	 * 文章模式清空
	 */
	public void urlpanelClear() {
		urlpanel_input_textArea.setText("");
	}
	
	/**
	 * 文章模式工作结束
	 */
	public void urlpanelDone() {
	    frame.setCursor(null);
    	urlpanel_start_button.setEnabled(true);
    	urlpanel_root_textField.setEditable(true);
    	urlpanel_root_button.setEnabled(true);
    	urlpanel_clear_button.setEnabled(true);
    	urlpanel_abort_button.setEnabled(false);
    	urlpanel_input_textArea.setEditable(true);
    	loadConfig_menuItem.setEnabled(true);
	    model_panel.setEnabledAt(0, true);
	    model_panel.setEnabledAt(2, true);
	}

	/*************分类模式方法*************/
	/**
	 * 分类模式启动
	 */
	public void categorypanelStart() {
		frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		categorypanel_start_button.setEnabled(false);
		categorypanel_root_textField.setEditable(false);
		categorypanel_root_button.setEnabled(false);
		categorypanel_openRoot_button.setEnabled(true);
	    categorypanel_clear_button.setEnabled(false);
	    categorypanel_abort_button.setEnabled(true);
	    categorypanel_input_textArea.setEditable(false);
	    loadConfig_menuItem.setEnabled(false);
	    progressBar.setValue(0);
	    model_panel.setEnabledAt(0, false);
	    model_panel.setEnabledAt(1, false);
	}
	
	/**
	 * 分类模式清空
	 */
	public void categorypanelClear(){
		categorypanel_input_textArea.setText("");
	}
	
	/**
	 * 分类模式工作结束
	 */
	public void categorypanelDone() {
		frame.setCursor(null);
    	categorypanel_start_button.setEnabled(true);
    	categorypanel_root_button.setEnabled(true);
    	categorypanel_root_textField.setEditable(true);
    	categorypanel_clear_button.setEnabled(true);
    	categorypanel_abort_button.setEnabled(false);
    	categorypanel_input_textArea.setEditable(true);
    	loadConfig_menuItem.setEnabled(true);
	    model_panel.setEnabledAt(0, true);
	    model_panel.setEnabledAt(1, true);
	}
	
}
