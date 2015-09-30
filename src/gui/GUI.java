package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import user.User;
import user.UserInterface;
import util.Util;

public class GUI implements PropertyChangeListener {

	private JFrame frame;
	private JTextField user_textField;
	private JTextField root_textField;
	
	private JTextField visited_textField;
	private JTextField score_textField;
	private JTextField rank_textField;
	private JTextField original_textField;
	private JTextField repost_textField;
	private JTextField translate_textField;
	private JTextField comment_textField;
	
	private JLabel profile_label;
	private JTextArea system_textArea;
	
	private JButton start_button;
	private JButton root_button;
	private JButton openRoot_button;
	private JButton browser_button;
	private JButton abort_button;
	private JButton reset_button;
	private JMenuItem loadUser_menuItem;
	
	private JProgressBar progressBar;

	private User user;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setType(Type.POPUP);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/icons/title.jpg")));
		frame.setTitle("CSDN\u535A\u5BA2\u4E0B\u8F7D\u5668\u2014By Geurney");
		frame.setBounds(100, 100, 710, 496);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu file_menu = new JMenu("\u6587\u4EF6");
		file_menu.setIcon(null);
		menuBar.add(file_menu);
		
		JMenuItem exit_menuItem = new JMenuItem("\u9000\u51FA");
		exit_menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		exit_menuItem.setIcon(new ImageIcon(GUI.class.getResource("/icons/exit.png")));
		file_menu.add(exit_menuItem);
		
		JMenu config_menu = new JMenu("\u8BBE\u7F6E");
		menuBar.add(config_menu);
		
		loadUser_menuItem = new JMenuItem("\u8F7D\u5165\u7528\u6237");
		loadUser_menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String[] info = Util.readFile("config.ini").split(",");
					if (info.length != 2) {
						JOptionPane.showMessageDialog(frame,
							    "无法解析配置文件内容！",
							    "配置错误",
							    JOptionPane.ERROR_MESSAGE);
						return;
					}
					user_textField.setText(info[0]);
					root_textField.setText(info[1]);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(frame,
						    "无法打开配置文件！",
						    "配置错误",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		loadUser_menuItem.setIcon(new ImageIcon(GUI.class.getResource("/icons/smile.png")));
		config_menu.add(loadUser_menuItem);
		
		JMenuItem saveConfig_menuItem = new JMenuItem("\u4FDD\u5B58\u914D\u7F6E");
		saveConfig_menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = user_textField.getText();
				if (name.length() == 0) {
					JOptionPane.showMessageDialog(frame,
						    "请输入用户名！",
						    "警告",
						    JOptionPane.WARNING_MESSAGE);
					return;
				}
				String root = root_textField.getText();
				if (root.length() == 0) {
					JOptionPane.showMessageDialog(frame,
						    "请输入下载目录！",
						    "警告",
						    JOptionPane.WARNING_MESSAGE);
					return;
				}
				Util.writeToFile("config.ini", name + "," + root);
				JOptionPane.showMessageDialog(frame,
					        "配置保存成功！",
					        "保存配置",
					        JOptionPane.PLAIN_MESSAGE);
			}
		});
		saveConfig_menuItem.setIcon(new ImageIcon(GUI.class.getResource("/icons/configure.png")));
		config_menu.add(saveConfig_menuItem);
		
		JMenu help_menu = new JMenu("\u5E2E\u52A9");
		help_menu.setIcon(null);
		menuBar.add(help_menu);
		
		JMenuItem help_menuItem = new JMenuItem("\u5E2E\u52A9...");
		help_menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				   JOptionPane.showMessageDialog(frame,
					        "1. 输入用户名\n2. 指定下载目录（可点击浏览按钮）\n3.下载\n4.查看目录",
					        "使用说明",
					        JOptionPane.PLAIN_MESSAGE);
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
			            "CSDB博客下载器 v1.0\n作者：Geurney",
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
		
		JPanel info_panel = new JPanel();
		info_panel.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		
		JPanel panel = new JPanel();
		panel.setBorder(UIManager.getBorder("FormattedTextField.border"));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(progressBar, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(info_panel, GroupLayout.PREFERRED_SIZE, 343, GroupLayout.PREFERRED_SIZE)
							.addGap(23)
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))
						.addComponent(system_Pane, GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE))
					.addGap(23))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(21)
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(info_panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(panel, 0, 0, Short.MAX_VALUE))))
					.addPreferredGap(ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
					.addComponent(system_Pane, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(28))
		);
		
		JLabel user_label = new JLabel("\u7528\u6237");
		user_label.setHorizontalAlignment(SwingConstants.CENTER);
		user_label.setIcon(new ImageIcon(GUI.class.getResource("/icons/user.png")));
		
		user_textField = new JTextField();
		user_textField.setColumns(10);
		
		JLabel root_label = new JLabel("\u672C\u5730");
		root_label.setIcon(new ImageIcon(GUI.class.getResource("/icons/save.png")));
		
		root_textField = new JTextField();
		root_textField.setColumns(10);
		
		root_button = new JButton("\u6D4F\u89C8...");
		root_button.setHorizontalAlignment(SwingConstants.LEFT);
		root_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 JFileChooser chooser = new JFileChooser();
				 chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				 int retval=chooser.showOpenDialog(root_button);
				 if (retval == JFileChooser.APPROVE_OPTION) {
					 root_textField.setText(chooser.getSelectedFile().getPath());
				 }
			}
		});
		root_button.setIcon(new ImageIcon(GUI.class.getResource("/icons/search.png")));
		
		start_button = new JButton("\u4E0B\u8F7D");
		//	ExecutorService backgroundExec = Executors.newCachedThreadPool();
			start_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String name = user_textField.getText();
					if (name.length() == 0) {
						JOptionPane.showMessageDialog(frame,
							    "请输入用户名！",
							    "警告",
							    JOptionPane.WARNING_MESSAGE);
						return;
					}
					String root = root_textField.getText();
					if (root.length() == 0) {
						JOptionPane.showMessageDialog(frame,
							    "请输入下载目录！",
							    "警告",
							    JOptionPane.WARNING_MESSAGE);
						return;
					}
				    frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				    start_button.setEnabled(false);
				    root_button.setEnabled(false);
				    openRoot_button.setEnabled(true);
				    user_textField.setEditable(false);
				    root_textField.setEditable(false);
				    reset_button.setEnabled(false);
				    loadUser_menuItem.setEnabled(false);
				    abort_button.setEnabled(true);
/*			    backgroundExec.execute(new Runnable() {
				    	public void run() {
				    		User user = new User(name, root);
					    	user.run();
					    	start_button.setEnabled(true);
					    	root_button.setEnabled(true);
					    	user_textField.setEditable(true);
					    	root_textField.setEditable(true);
					    	frame.setCursor(null);
				    	}
				    });*/
/*			    new Thread () {
				    	public void run() {
				    		User user = new User(name, root);
					    	user.run();
					    	start_button.setEnabled(true);
					    	root_button.setEnabled(true);
					    	user_textField.setEditable(true);
					    	root_textField.setEditable(true);
					    	frame.setCursor(null);
				    	}
				    }.run();;*/
				    user = new User(name, root);
				    user.addPropertyChangeListener(GUI.this);
			    	user.execute();
				}
			});
			start_button.setIcon(new ImageIcon(GUI.class.getResource("/icons/start.png")));
		
		openRoot_button = new JButton("\u67E5\u770B\u76EE\u5F55");
		openRoot_button.setIcon(new ImageIcon(GUI.class.getResource("/icons/open.png")));
		openRoot_button.setEnabled(false);
		openRoot_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Util.openExplorer(root_textField.getText());
			}
		});
		
		abort_button = new JButton("\u7EC8\u6B62");
		abort_button.setIcon(new ImageIcon(GUI.class.getResource("/icons/abort.png")));
		abort_button.setEnabled(false);
    	abort_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (user != null) {
					user.cancel(true);
					abort_button.setEnabled(false);
				}
			}
    	});
		
		reset_button = new JButton("\u91CD\u7F6E");
		reset_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				user_textField.setText("");
				root_textField.setText("");
				system_textArea.setText("");
				visited_textField.setText("");
				score_textField.setText("");
				rank_textField.setText("");
				original_textField.setText("");
				repost_textField.setText("");
				translate_textField.setText("");
				comment_textField.setText("");
				profile_label.setIcon(null);
				progressBar.setValue(0);
				openRoot_button.setEnabled(false);
			}
		});
		reset_button.setIcon(new ImageIcon(GUI.class.getResource("/icons/reset.png")));
		
		browser_button = new JButton("\u8BBF\u95EE");
		browser_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = user_textField.getText();
				if (name.length() == 0) {
					JOptionPane.showMessageDialog(frame,
						    "请输入用户名！",
						    "警告",
						    JOptionPane.WARNING_MESSAGE);
					return;
				}
				Util.openBrowser(UserInterface.host + name);
			}
		});
		browser_button.setIcon(new ImageIcon(GUI.class.getResource("/icons/browser.png")));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(root_label)
									.addGap(5)
									.addComponent(root_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(root_button))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(user_label)
									.addGap(5)
									.addComponent(user_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(browser_button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(21)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(abort_button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(start_button, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))
							.addGap(18, 27, Short.MAX_VALUE)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(reset_button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(openRoot_button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addContainerGap(105, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(1)
							.addComponent(user_label))
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(user_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(browser_button)))
					.addGap(23)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(root_label))
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(root_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(root_button)))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(openRoot_button)
						.addComponent(start_button))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(abort_button)
						.addComponent(reset_button))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		profile_label = new JLabel("\u5934\u50CF");
		profile_label.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel userInfo_panel = new JPanel();
		userInfo_panel.setForeground(Color.DARK_GRAY);
		userInfo_panel.setLayout(new GridLayout(3, 2, 0, 0));
		
		JLabel visited_label = new JLabel("\u8BBF\u95EE");
		visited_label.setHorizontalAlignment(SwingConstants.CENTER);
		userInfo_panel.add(visited_label);
		
		visited_textField = new JTextField();
		visited_textField.setEditable(false);
		userInfo_panel.add(visited_textField);
		visited_textField.setColumns(10);
		
		JLabel score_label = new JLabel("\u79EF\u5206");
		score_label.setHorizontalAlignment(SwingConstants.CENTER);
		userInfo_panel.add(score_label);
		
		score_textField = new JTextField();
		score_textField.setEditable(false);
		userInfo_panel.add(score_textField);
		score_textField.setColumns(10);
		
		JLabel rank_label = new JLabel("\u6392\u540D");
		rank_label.setHorizontalAlignment(SwingConstants.CENTER);
		userInfo_panel.add(rank_label);
		
		rank_textField = new JTextField();
		rank_textField.setEditable(false);
		userInfo_panel.add(rank_textField);
		rank_textField.setColumns(10);
		
		JPanel blogInfo_panel = new JPanel();
		blogInfo_panel.setLayout(new GridLayout(4, 2, 0, 0));
		
		JLabel original_label = new JLabel("\u539F\u521B");
		original_label.setHorizontalAlignment(SwingConstants.CENTER);
		blogInfo_panel.add(original_label);
		
		original_textField = new JTextField();
		original_textField.setEditable(false);
		blogInfo_panel.add(original_textField);
		original_textField.setColumns(10);
		
		JLabel repost_label = new JLabel("\u8F6C\u8F7D");
		repost_label.setHorizontalAlignment(SwingConstants.CENTER);
		blogInfo_panel.add(repost_label);
		
		repost_textField = new JTextField();
		repost_textField.setEditable(false);
		blogInfo_panel.add(repost_textField);
		repost_textField.setColumns(10);
		
		JLabel translate_label = new JLabel("\u8BD1\u6587");
		translate_label.setHorizontalAlignment(SwingConstants.CENTER);
		blogInfo_panel.add(translate_label);
		
		translate_textField = new JTextField();
		translate_textField.setEditable(false);
		blogInfo_panel.add(translate_textField);
		translate_textField.setColumns(10);
		
		JLabel comment_label = new JLabel("\u8BC4\u8BBA");
		comment_label.setHorizontalAlignment(SwingConstants.CENTER);
		blogInfo_panel.add(comment_label);
		
		comment_textField = new JTextField();
		comment_textField.setEditable(false);
		blogInfo_panel.add(comment_textField);
		comment_textField.setColumns(10);
		GroupLayout gl_info_panel = new GroupLayout(info_panel);
		gl_info_panel.setHorizontalGroup(
			gl_info_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_info_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(profile_label, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_info_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(userInfo_panel, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
						.addComponent(blogInfo_panel, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_info_panel.setVerticalGroup(
			gl_info_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_info_panel.createSequentialGroup()
					.addGroup(gl_info_panel.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_info_panel.createSequentialGroup()
							.addGap(14)
							.addComponent(profile_label, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING, gl_info_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(userInfo_panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(blogInfo_panel, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		info_panel.setLayout(gl_info_panel);
		
		system_textArea = new JTextArea();
		system_textArea.setEditable(false);
		system_Pane.setViewportView(system_textArea);
		system_textArea.setWrapStyleWord(true);
		system_textArea.setLineWrap(true);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);
		frame.getContentPane().setLayout(groupLayout);
	}


	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String property = event.getPropertyName();
		switch (property) {
			case UserInterface.STATE_BLOGINFO : {
				@SuppressWarnings("unchecked")
				List<String> blogInfo = (List<String>) event.getNewValue();
				visited_textField.setText(blogInfo.get(0));
				score_textField.setText(blogInfo.get(1));
				rank_textField.setText(blogInfo.get(2));
				original_textField.setText(blogInfo.get(3));
				repost_textField.setText(blogInfo.get(4));
				translate_textField.setText(blogInfo.get(5));
				comment_textField.setText(blogInfo.get(6));
			} break;
			case UserInterface.STATE_PROFILE : {
				String imgUrl = (String)event.getNewValue();
				profile_label.setIcon(new ImageIcon(imgUrl));
			}break;
			case UserInterface.STATE_SYSTEM : {
				String systemInfo = (String)event.getNewValue();
				system_textArea.append(systemInfo);
			} break;
			case UserInterface.STATE_PROGRESS : {
				int progress = (int)event.getNewValue();
				progressBar.setValue(progress);
			}break;
			case UserInterface.STATE_DONE :{
				String systemInfo = (String)event.getNewValue();
				system_textArea.append(systemInfo);
		    	start_button.setEnabled(true);
		    	root_button.setEnabled(true);
		    	reset_button.setEnabled(true);
		    	abort_button.setEnabled(false);
		    	user_textField.setEditable(true);
		    	root_textField.setEditable(true);
		    	loadUser_menuItem.setEnabled(true);
		    	frame.setCursor(null);
			}
		}
	}
}
