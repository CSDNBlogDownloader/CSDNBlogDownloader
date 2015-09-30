package crawler;

import java.io.File;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;

import parser.Parser;
import util.Util;

/**
 * 首页爬虫. 爬取用户首页信息：博客信息、用户头像和文章分类。
 * 
 * @author Geurney
 *
 */
public class IndexCrawler extends Crawler {
	/**
	 * 下载地址
	 */
	private String root;
	
	/**
	 * 博客信息。依次为：访问、积分、排名、原创、转载、译文和评论。
	 */
	private List<String> blogInfo;
	
	/**
	 * 文章分类。键值对：类名和url地址 
	 */
	private List<SimpleEntry<String, String>> categories;
	
	/**
	 * 构造首页爬虫
	 * @param root 用户头像保存地址
	 */
	public IndexCrawler(String root) {
		this.root = root;
		blogInfo = new ArrayList<String>();
	}

	@Override
	public boolean crawl(SimpleEntry<String, String> link) {
		if (connect(link.getValue())) {
			String html = document.html();
			blogInfo = Parser.bloggerParser(html);
			categories = Parser.categoryParser(html);
			for (SimpleEntry<String, String> category : categories) {
				new File(root + "\\blog\\" + category.getKey()).mkdirs();
			}
			Element imageUrl = document.select(
					"img[src^=http://avatar.csdn.net/]").first();
			String profileUrl = imageUrl.absUrl("src");
			Util.downloadImage(profileUrl, root, "user.jpg");
			return true;
		}
		return false;
	}

	/**
	 * 获取博客信息
	 * @return 博客信息
	 */
	public List<String> blogInfo() {
		return blogInfo;
	}
	
	/**
	 * 获取分类信息
	 * @return 分类信息
	 */
	public List<SimpleEntry<String, String>> categories() {
		return categories;
	}
}
