package crawler;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 分类爬虫。爬取一个分类下的所有文章链接。 
 * @author Geurney
 *
 */
public class CategoryCrawler extends Crawler {
	/**
	 * 文章链接。键值对：标题和url地址。
	 */
	private List<SimpleEntry<String, String>> links;
	
	/**
	 * 构造分类爬虫
	 */
	public CategoryCrawler() {
		links = new ArrayList<SimpleEntry<String, String>>();
	}

	@Override
	public boolean crawl(SimpleEntry<String, String> link) {
		int index = 1;
		String url = link.getValue();
		while(connect(url + "/" + index)) {
			// Elements linksOnPage = html.select("a[href][title=\"阅读次数\"]");
			Elements pagelinks = document.select(".link_title a[href]");
			for (Element pagelink : pagelinks) {
				links.add(new SimpleEntry<String, String>(pagelink.text(),
						pagelink.absUrl("href")));
			}
			if (document.text().contains("下一页")) {
				index++;
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取文章链接
	 * @return 文章链接
	 */
	public List<SimpleEntry<String, String>> links() {
		return links;
	}

}
