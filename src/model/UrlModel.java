package model;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import type.Blog;
import crawler.BlogCrawler;
/**
 * Blog模式根据文章链接下载
 * @author Geurney
 *
 */
public class UrlModel extends Model {

	/**
	 * 文章保存目录
	 */
	private String root;

	/**
	 * 文章链接 
	 */
	private List<String> urls;
	
	/**
	 * 下载的Blogs
	 */
	private List<Blog> blogs;
	
	/**
	 * BlogModel构造方法
	 */
	public UrlModel() {
		urls =  new ArrayList<String>();
		blogs = new ArrayList<Blog>();
	}

	/**
	 * 设置文章保存目录
	 * @param root 文章保存目录
	 */
	public void setRoot(String root) {
		this.root = root;
	}
	
	/**
	 * 获取输入中的文章链接
	 * @param input 文章链接
	 */
	public void addUrls(String input) {
		urls.addAll(Arrays.asList(input.trim().split("\n")));
	}
	
	/**
	 * 爬取文章
	 */
	public void crawl() {
		BlogCrawler crawler = new BlogCrawler(); 
		for (String url : urls) {
			Blog blog = crawler.crawl(url, root);
			if(blog != null) {
				blogs.add(blog);
			}
		}
	}
	
	@Override
	protected Void doInBackground() {
		long start;
		long end;
		status = true;
		double progress = 0;
		double progressStep = 100.0 /  urls.size();
		startTime = System.currentTimeMillis();
		
		BlogCrawler crawler = new BlogCrawler(); 
		for (String url : urls) {
			start = System.currentTimeMillis();
			Blog blog = crawler.crawl(url, root);
			end = System.currentTimeMillis();
			if(isCancelled()) {
				return null;
			}
			firePropertyChange(STATE_PROGRESS, null, (int) (progress += progressStep));
			if(blog == null) {
				continue;
			} 
			blogs.add(blog);
			firePropertyChange(STATE_SYSTEM, null, "下载文章：\"" + blog.getTitle() + "\"    " + (end - start) + "毫秒\n");
		}
		endTime = System.currentTimeMillis();
		
		List<String> logs = crawler.getErrorLog();
		if(logs.size() != 0) {
			for (String error : logs) {
				firePropertyChange(STATE_SYSTEM, null, error + "\n");
			}
		}
		return null;
	}

	 @Override
	 public void done() {
		 if (!isCancelled()) {
			 Toolkit.getDefaultToolkit().beep();
			 firePropertyChange(STATE_PROGRESS, null, 100);
			 firePropertyChange(URL_STATE_DONE, null, "成功下载" + blogs.size() + "篇文章，总耗时" + (endTime - startTime) / 1000 +"秒！\n");
		 } else {
			 firePropertyChange(URL_STATE_DONE, null, "终止！\n");
		 }
		 status = false;
	 }
	 
}
