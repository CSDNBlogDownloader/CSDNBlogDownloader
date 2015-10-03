package model;

import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import parser.Parser;
import type.Blog;
import type.Category;
import type.User;
import crawler.BlogCrawler;
import crawler.CategoryCrawler;
import crawler.IndexCrawler;

public class UserModel extends Model {
	/**
	 * 用户
	 */
	private User user;
	
	/**
	 * 状态
	 */
	private boolean status;

	/**
	 * 待下载的文章数
	 */
	private int numberOfBlogs;
	
	/**
	 * BlogModel构造方法
	 */
	public UserModel() {
		user = new User();
	}
	
	/**
	 * 获取用户名
	 * @return 用户名
	 */
	public String getUser() {
		return user.getName();
	}

	/**
	 * 设置用户名
	 * @param user 用户名
	 */
	public void setUser(String user) {
		this.user.setName(user);
		this.user.setUrl(host + user);
	}
	
	/**
	 * 设置用户目录
	 * @param root the root to set
	 */
	public void setRoot(String root) {
		user.setPath(root);
	}
	
	/**
	 * 是否工作
	 * @return 工作状态
	 */
	public boolean isBusy() {
		return status;
	}
	
	/**
	 * 爬取用户
	 */
	public void crawl() {
		IndexCrawler indexCrawler = new IndexCrawler();
		user = indexCrawler.crawl(user.getUrl(), user.getPath());
		if (user == null) {
			return;
		}
		List<Category> categories = user.getCategoreis();
		for (int i = 0; i < categories.size(); i++) {
			Category category = categories.remove(i);
			
			CategoryCrawler categoryCrawler = new CategoryCrawler(); 
			category = categoryCrawler.crawl(category.getUrl(), category.getPath());
			if (category == null) {
				i--;
				continue;
			}
			categories.add(i, category);

			BlogCrawler blogCrawler = new BlogCrawler();
			List<Blog> blogs = category.getBlogs();
			for (int j = 0; j < blogs.size(); j++) {
				Blog blog = blogs.remove(j);
				blog = blogCrawler.crawl(blog.getUrl(),blog.getPath());
				if (blog == null) {
					j--;
					continue;
				}
				List<String> list = new ArrayList<String>();
				list.add(category.getTitle());
				blog.setCategory(list);
				blogs.add(j, blog);
			}
			createIndex();
		}
	}
	
	/**
	 * 创建索引
	 * @return True 创建成功。False 创建失败
	 */
	public boolean createIndex() {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(user.getPath() + "\\" + "index.html"));
			writer.write(Parser.blogInfoToIndex(user.getName(), user.getProfileImage(), user.getBlogInfo()));
			writer.write(Parser.categoryOpenToIndex(user.getBlogFolder()));
			for (Category category : user.getCategoreis()) {
				writer.write("\n" + Parser.categoryToIndex(category.getTitle(), category.getPath()));
				for (Blog blog: category.getBlogs()) {
					writer.write(Parser.blogToIndex(blog.getTitle(), blog.getPath()));
				}
				writer.write(Parser.categoryCloseToIndex());
			}
			writer.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	protected Void doInBackground() {
		long start;
		long end;
		status = true;
		double progress = 5;
		double categoryStep;
		double blogStep;
		startTime = System.currentTimeMillis();
		
		start = startTime;
		IndexCrawler indexCrawler = new IndexCrawler();
		firePropertyChange(STATE_SYSTEM, null, "获取用户博客信息...");
		user = indexCrawler.crawl(user.getUrl(), user.getPath());
		if (user == null) {
			firePropertyChange(STATE_SYSTEM, null, "下载用户" + user.getName()+"失败！\n");
			return null;
		}
		end = System.currentTimeMillis();
		if(isCancelled()) {
			return null;
		}
		firePropertyChange(STATE_BLOGINFO, null, user.getBlogInfo());
		firePropertyChange(STATE_PROFILE, null, user.getProfileImage());
		firePropertyChange(STATE_SYSTEM, null, "完成 ！    " + (end - start) + "毫秒\n");
		firePropertyChange(STATE_PROGRESS, null, (int) progress);
		
		CategoryCrawler categoryCrawler = new CategoryCrawler(); 
		BlogCrawler blogCrawler = new BlogCrawler();
		List<Category> categories = user.getCategoreis();
		categoryStep = 90 / categories.size();
		for (int i = 0; i < categories.size(); i++) {
			start = System.currentTimeMillis();
			Category category = categories.remove(i);
			category = categoryCrawler.crawl(category.getUrl(), user.getBlogFolder());
			if(isCancelled()) {
				return null;
			}
			if (category == null) {
				i--;
				firePropertyChange(STATE_SYSTEM, null, i + "分类解析失败！\n");
				continue;
			}
			categories.add(i, category);
			List<Blog> blogs = category.getBlogs();
			if (blogs.size() == 0) {
				continue;
			}
			blogStep = categoryStep / blogs.size();
			firePropertyChange(STATE_SYSTEM, null,
					"下载分类：" + category.getTitle() + "("
							+ category.getBlogs().size() + ") ");
			for (int j = 0; j < blogs.size(); j++) {
				Blog blog = blogs.remove(j);
				blog = blogCrawler.crawl(blog.getUrl(), category.getPath());
				if(isCancelled()) {
					return null;
				}
				firePropertyChange(STATE_PROGRESS, null, (int) (progress += blogStep));
				if (blog == null) {
					j--;
					firePropertyChange(STATE_SYSTEM, null, "！");
					continue;
				}
				List<String> list = new ArrayList<String>();
				list.add(category.getTitle());
				blog.setCategory(list);
				blogs.add(j, blog);
				firePropertyChange(STATE_SYSTEM, null, ">");
			}
			numberOfBlogs += blogs.size();
			end = System.currentTimeMillis();
			firePropertyChange(STATE_SYSTEM, null, " 完成 ！    " + (end - start) + "毫秒\n");
		}
		firePropertyChange(STATE_PROGRESS, null, 90);
		firePropertyChange(STATE_SYSTEM, null, "创建索引...");
		start = System.currentTimeMillis();
		if (createIndex()) {
			end = System.currentTimeMillis();
			firePropertyChange(STATE_SYSTEM, null, "完成 ！   " + (end - start) + "毫秒\n");
		} else {
			firePropertyChange(STATE_SYSTEM, null, "失败！\n");	
		}
		endTime = System.currentTimeMillis();
		List<String> logs = categoryCrawler.getErrorLog();
		if (logs.size() != 0) {
			for (String error : logs) {
				firePropertyChange(STATE_SYSTEM, null, error + "\n");
			}
		}
		List<String> bloglogs = blogCrawler.getErrorLog();
		if (bloglogs.size() != 0) {
			for (String error : bloglogs) {
				firePropertyChange(STATE_SYSTEM, null, error + "\n");
			}
		}
		return null;
	}

	 @Override
	 public void done() {
		 if (!isCancelled() && user != null) {
			 Toolkit.getDefaultToolkit().beep();
			 firePropertyChange(STATE_PROGRESS, null, 100);
			 firePropertyChange(USER_STATE_DONE, null, "成功下载" + user.getCategoreis().size() + "类" + numberOfBlogs + "篇文章， 耗时" + (endTime - startTime) / 1000 +"秒!\n");
		 } else {
			 firePropertyChange(USER_STATE_DONE, null, "\n终止！\n");
		 }
		 status = false;
	 }

}
