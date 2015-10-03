package controller;

import gui.GUI;
import model.CategoryModel;
import model.Model;
import model.UrlModel;
import model.UserModel;
/**
 * 控制器
 * @author Geurney
 *
 */
public class Controller {
	/**
	 * 当前使用的Model
	 */
	private Model model;
	
	/**
	 * 用户界面
	 */
	private GUI view;

	/**
	 * 控制器构造函数
	 */
	public Controller() {
		view = new GUI(this);;
	}
	
	/**
	 * 启动控制器，运行图形界面
	 */
	public void run() {
		view.GUIStart();
	}
	
	/**
	 * 设置工作模式
	 * @param model 工作模式
	 */
	public void setModel(Model model) {
		this.model = model;
	}

	/**
	 * 更新设置菜单
	 */
	public void updateConfigMenu() {
		view.updateConfigMenu();
	}
	
	/**
	 * 载入配置
	 */
	public void loadConfig() {
		view.loadConfig();
	}
	
	/**
	 * 保存配置
	 */
	public void saveConfig() {
		view.saveConfig();
	}
	
	/**
	 * 更新帮助
	 */
	public void updateHelp() {
		view.updateHelp();
	}
	/**
	 * 退出应用
	 */
	public void exit() {
		System.exit(0);
	}
	
	/**
	 * 执行工作模式
	 */
	public void execute() {
		if (model != null) {
			model.execute();
		}
	}
	
	/**
	 * 终止工作模式
	 */
	public void cancel() {
		if(model != null && model.isBusy()) {
			model.cancel(true);
		}
	}
	
	/**
	 * 是否忙碌
	 * @return True 忙碌。 False 空闲
	 */
	public boolean isBusy() {
		if (model == null) {
			return false;
		}
		return model.isBusy();
	}
	
	/**
	 * 用户工作模式启动
	 * @param name 用户名
	 * @param root 下载目录
	 */
	public void userPanelStart(String name, String root) {
		view.userpanelStart();
		/*ExecutorService backgroundExec = Executors.newCachedThreadPool();
			backgroundExec.execute(new Runnable() {
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
	    UserModel model = new UserModel();
	    model.setUser(name);
	    model.setRoot(root);
	    model.addPropertyChangeListener(view);
	    setModel(model);
	    execute();
	}

	/**
	 * 重置用户模式界面
	 */
	public void userpanelReset() {
		view.userpanelReset();
	}

	/**
	 * 文章工作模式启动
	 * @param root 下载目录
	 * @param urls 文章链接
	 */
	public void urlpanelStart(String root, String urls) {
		view.urlpanelStart();
	    UrlModel model = new UrlModel();
	    model.setRoot(root);
	    model.addUrls(urls);
	    model.addPropertyChangeListener(view);
	    setModel(model);
	    execute();
	}

	/**
	 * 文章工作模式清空
	 */
	public void urlpanelClear() {
		view.urlpanelClear();
	}

	/**
	 * 分类工作模式启动
	 * @param root 下载目录
	 * @param categories 分类链接
	 */
	public void categorypanelStart(String root, String categories) {
		view.categorypanelStart();
	    CategoryModel model = new CategoryModel();
	    model.setRoot(root);
	    model.addCategories(categories);
	    model.addPropertyChangeListener(view);
	    setModel(model);
	    execute();
	}

	/**
	 * 分类工作模式清空
	 */
	public void categorypanelClear() {
		view.categorypanelClear();
	}
	
}
