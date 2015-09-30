package crawler;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import parser.Parser;
import util.Util;
/**
 * 文章爬虫。爬取文章内容。
 * @author Geurney
 *
 */
public class BlogCrawler extends Crawler {
	/**
	 * 文章保存目录
	 */
	private String root;
	
	/**
	 * 文章标题
	 */
	private String title;
	
	/**
	 * 构造文章爬虫
	 * @param root 文章保存目录
	 */
	public BlogCrawler(String root) {
		this.root = root;
	}

	@Override
	public boolean crawl(SimpleEntry<String, String> link) {
		if (connect(link.getValue())) {
			String html = document.html();
			title = Parser.fileNameValify(link.getKey());
			html = Parser.docParser(html);
			String blogPath = root + "\\" + title + ".html";
			List<String> images = Parser.imageParser(html);
			String imagePath = root + "\\" + title;
			for (int i = 0; i < images.size(); i++) {
				Util.downloadImage(images.get(i), imagePath, (i+1) + "");
			}
			html = Parser.imageLocalize(html, title);
			html = Parser.updateLinks(html);
			return Util.writeToFile(blogPath, html);
		}
		return false;
	}

	/**
	 * 获取文章保存路径
	 * @return 文章保存路径
	 */
	public String blogPath() {
		return root + "\\" + title + ".html";
	}
	
	/**
	 * 获取文章标题
	 * @return 文章标题
	 */
	public String title() {
		return title;
	}

}
