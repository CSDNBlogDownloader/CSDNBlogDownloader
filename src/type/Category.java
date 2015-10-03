package type;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类类
 * @author Geurney
 *
 */
public class Category {
	/**
	 * 分类类名
	 */
	private String title;

	/**
	 * 分类url
	 */
	private String url;
	
	/**
	 * 分类保存路径
	 */
	private String path;
	
	/**
	 * 分类下的文章 
	 */
	private List<Blog> blogs;
	
	/**
	 * Category构造函数
	 */
	public Category() {
		blogs = new ArrayList<Blog>();
	}
	
	/**
	 * 获取分类的标题
	 * @return 分类标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置分类的标题
	 * @param title 分类标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 获取分类的url
	 * @return 分类的url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置分类的url
	 * @param url 分类的url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取分类保存路径
	 * @return 分类保存路径
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 设置分类保存路径
	 * @param path 分类保存路径
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * 获取分类的文章
	 * @return 分类文章
	 */
	public List<Blog> getBlogs() {
		return blogs;
	}
}
