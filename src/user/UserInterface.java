package user;

import java.io.IOException;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
/**
 * User接口
 * @author Geurney
 *
 */
public interface UserInterface {
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
	 * 完成消息Flag
	 */
	public static final String STATE_DONE = "STATE_DONE";
	
	/**
	 * 进度消息Flag
	 */
	public static final String STATE_PROGRESS = "STATE_PROGRESS";
	
	/**
	 * 终止消息Flag
	 */
	public static final String STATE_ABORT = "STATE_ABORT";

	/**
	 * 爬取首页博客信息、用户头像和文章分类。
	 * @return True 爬取成功。False 爬取失败
	 */
	public boolean profile();
	
	/**
	 * 爬取分类下的文章链接
	 * @param category 文章类名
	 * @return 分类下的所有文章链接
	 */
	public List<SimpleEntry<String, String>> crawlCategory(SimpleEntry<String, String> category);
	
	/**
	 * 爬取文章内容
	 * @param category 文章类名
	 * @param link 文章标题和url地址
	 * @return True 爬取成功。False 爬取失败
	 */
	public boolean crawlBlog(String category, SimpleEntry<String, String> link);
	
	/**
	 * 创建文章索引
	 * @throws IOException 创建索引失败
	 */
	public void createIndex() throws IOException;
	
}
