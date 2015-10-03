package type;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户类
 * @author Geurney
 *
 */
public class User {
	
	/**
	 * 用户名 
	 */
	private String name;
	
	/**
	 * 博客首页
	 */
	private String url;

	/**
	 * 用户保存路径 
	 */
	private String path;
	
	/**
	 * 文章保存目录
	 */
	private String blogFolder;
	
	/**
	 * 博客信息(访问、排名、积分、原创、转载、译文和评论)
	 */
	private List<String> blogInfo;

	/**
	 * 头像图片地址 
	 */
	private String profileImage;
	
	/**
	 * 文章分类
	 */
	private List<Category> categoreis;
	
	/**
	 * User构造函数
	 */
	public User() {
		blogInfo = new ArrayList<String>();
		categoreis = new ArrayList<Category>();
	}
	
	/**
	 * 获取用户名
	 * @return 用户名
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置用户名
	 * @param name 用户名
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 获取用户保存路径
	 * @return 用户保存路径
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 设置用户保存路径
	 * @param path 用户保存路径
	 */
	public void setPath(String path) {
		this.path = path;
		if (name != null) {
			setBlogFolder(path + "\\" + "blog");
		}
	}

	/**
	 * 获取用户文章目录
	 * @return 用户文章目录
	 */
	public String getBlogFolder() {
		return blogFolder;
	}

	/**‘
	 * 设置用户文章目录
	 * @param blogFolder 用户文章目录
	 */
	public void setBlogFolder(String blogFolder) {
		this.blogFolder = blogFolder;
	}

	/**
	 * 获取用户博客信息
	 * @return 用户博客信息
	 */
	public List<String> getBlogInfo() {
		return blogInfo;
	}

	/**
	 * 设置用户博客信息
	 * @param blogInfo 用户博客信息
	 */
	public void setBlogInfo(List<String> blogInfo) {
		this.blogInfo = blogInfo;
	}

	/**
	 * 获取用户头像保存路径
	 * @return 用户头像保存路径
	 */
	public String getProfileImage() {
		return profileImage;
	}

	/**
	 * 设置用户头像保存路径
	 * @param profileImage 用户头像保存路径
	 */
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	/**
	 * 获取用户文章分类
	 * @return 用户文章分类
	 */
	public List<Category> getCategoreis() {
		return categoreis;
	}

	/**
	 * 设置用户文章分类
	 * @param categoreis 用户文章分类
	 */
	public void setCategoreis(List<Category> categoreis) {
		this.categoreis = categoreis;
	}
	
	/**
	 * 获取用户博客首页url
	 * @return 用户博客首页url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置用户博客首页url
	 * @param url 用户博客首页url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
