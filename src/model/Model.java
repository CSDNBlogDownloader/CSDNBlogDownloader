package model;

import javax.swing.SwingWorker;

/**
 * Model 接口
 * @author Geurney
 *
 */
public abstract class Model extends SwingWorker<Void, Void> {

	/**
	 * host
	 */
	public static final String host = "http://blog.csdn.net/";
	
	/**
	 * 博客信息消息Flag
	 */
	public static final String STATE_BLOGINFO = "STATE_BLOGINFO";
	
	/**
	 * 用户头像消息Flag
	 */
	public static final String STATE_PROFILE = "STATE_PROFILE";
	
	/**
	 * 系统消息Flag
	 */
	public static final String STATE_SYSTEM = "STATE_SYSTEM";
	
	
	/**
	 * 进度消息Flag
	 */
	public static final String STATE_PROGRESS = "STATE_PROGRESS";
	
	
	/**
	 * 用户模式完成消息Flag
	 */
	public static final String USER_STATE_DONE = "USER_STATE_DONE";

	
	/**
	 * 分类模式完成消息Flag
	 */
	public static final String CATEGORY_STATE_DONE = "CATEGORY_STATE_DONE";
	
	/**
	 * 博客模式完成消息Flag
	 */
	public static final String URL_STATE_DONE = "URL_STATE_DONE";
	
	/**
	 * 工作状态。True 工作中。False 未工作。
	 */
	protected boolean status;

	/**
	 * 获取工作状态
	 * @return True 工作中。False 未工作。
	 */
	public boolean isBusy() {
		return status;
	}
	
	/**
	 * 开始时间
	 */
	protected long startTime;
	
	/**
	 * 完成时间
	 */
	protected long endTime;
	
	
}
