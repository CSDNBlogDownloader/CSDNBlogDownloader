package type;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章类
 * @author Geurney
 *
 */
public class Blog {
	/**
	 * 文章标题
	 */
	private String title;
	
	/**
	 * 文章分类
	 */
	private List<String> category;
	
	/**
	 * 文章url地址
	 */
	private String url;
	
	/**
	 * 文章保存路徑
	 */
	private String path;
	
	/**
	 * Blog 构造函数
	 */
	public Blog() {
		category = new ArrayList<String>();
	}

	/**
	 * 获取文章标题
	 * @return 文章标题
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * 设置文章标题
	 * @param title 标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取文章分类
	 * @return 文章分类
	 */
	public List<String> getCategory() {
		return category;
	}

	/**
	 * 设置文章分类
	 * @param category 类名
	 */
	public void setCategory(List<String> category) {
		this.category = category;
	}

	/**
	 * 获取文章url
	 * @return 文章url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置文章url
	 * @param url 文章url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取文章保存路径
	 * @return 文章保存路径
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 设置文件保存路径 
	 * @param path 文件保存路径 
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
}
