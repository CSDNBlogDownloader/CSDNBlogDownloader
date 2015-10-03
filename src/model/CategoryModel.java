package model;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import type.Blog;
import type.Category;
import crawler.BlogCrawler;
import crawler.CategoryCrawler;

public class CategoryModel extends Model {
	/**
	 * 文章保存目录
	 */
	private String root;

	/**
	 * 分类链接
	 */
	private List<String> urls;

	/**
	 * 下载的Categories
	 */
	private List<Category> categories;
	
	/**
	 * 下載的文章數
	 */
	private int numberOfBlogs;

	/**
	 * BlogModel构造方法
	 */
	public CategoryModel() {
		urls = new ArrayList<String>();
		categories = new ArrayList<Category>();
	}

	/**
	 * 设置文章保存目录
	 * 
	 * @param root
	 *            文章保存目录
	 */
	public void setRoot(String root) {
		this.root = root;
	}

	/**
	 * 设置文章链接
	 * 
	 * @param urls
	 *            文章链接
	 */
	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	/**
	 * 获取输入中的文章链接
	 * 
	 * @param input
	 *            文章链接
	 */
	public void addCategories(String input) {
		urls.addAll(Arrays.asList(input.trim().split("\n")));
	}

	/**
	 * 爬取分类和各分类的文章
	 */
	public void crawl() {
		CategoryCrawler categoryCrawler = new CategoryCrawler();
		BlogCrawler blogCrawler = new BlogCrawler();
		for (String url : urls) {
			Category category = categoryCrawler.crawl(url, root);
			if (category != null) {
				categories.add(category);
				List<Blog> blogs = category.getBlogs();
				for (int i = 0; i < blogs.size(); i++) {
					Blog blog = blogs.remove(i);
					blog = blogCrawler.crawl(blog.getUrl(), category.getPath());
					if (blog != null) {
						List<String> list = new ArrayList<String>();
						list.add(category.getTitle());
						blog.setCategory(list);
						blogs.add(i, blog);
					}
				}
			}
		}
	}

	@Override
	protected Void doInBackground() throws Exception {
		long start;
		long end;
		status = true;
		double progress = 0;
		double categoryStep = 100.0 / urls.size();
		double blogStep;
		startTime = System.currentTimeMillis();
		
		CategoryCrawler categoryCrawler = new CategoryCrawler();
		BlogCrawler blogCrawler = new BlogCrawler();
		for (String url : urls) {
			start = System.currentTimeMillis();
			Category category = categoryCrawler.crawl(url, root);
			if(isCancelled()) {
				return null;
			}
			if (category == null) {
				firePropertyChange(STATE_SYSTEM, null, "分类解析失败: " + url + "\n");
				continue;
			}
			categories.add(category);
			List<Blog> blogs = category.getBlogs();
			if (blogs.size() == 0) {
				continue;
			}
			blogStep = categoryStep / blogs.size();
			firePropertyChange(STATE_SYSTEM, null,
					"下载分类：" + category.getTitle() + "("
							+ category.getBlogs().size() + ") ");
			for (int i = 0; i < blogs.size(); i++) {
				Blog blog = blogs.remove(i);
				blog = blogCrawler.crawl(blog.getUrl(), category.getPath());
				if (isCancelled()) {
					return null;
				}
				firePropertyChange(STATE_PROGRESS, null, (int) (progress += blogStep));
				if (blog == null) {
					i--;
					firePropertyChange(STATE_SYSTEM, null, "！");
					continue;
				}
				List<String> list = new ArrayList<String>();
				list.add(category.getTitle());
				blog.setCategory(list);
				blogs.add(i, blog);
				firePropertyChange(STATE_SYSTEM, null, ">");
			}
			numberOfBlogs += blogs.size();
			end = System.currentTimeMillis();
			firePropertyChange(STATE_SYSTEM, null, " 完成 ！    " + (end - start) + "毫秒\n");
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
		if (!isCancelled()) {
			Toolkit.getDefaultToolkit().beep();
			firePropertyChange(STATE_PROGRESS, null, 100);
			firePropertyChange(CATEGORY_STATE_DONE, null,
					"成功下载" + categories.size() + "类" + numberOfBlogs + "篇文章， 总耗时" + (endTime - startTime) / 1000 +"秒！\n");
		} else {
			firePropertyChange(CATEGORY_STATE_DONE, null, "\n终止！\n");
		}
		status = false;
	}

}
