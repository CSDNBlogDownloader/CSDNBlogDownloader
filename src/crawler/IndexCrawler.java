package crawler;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import parser.Parser;
import type.Category;
import type.User;
import util.Util;

/**
 * 首页爬虫. 爬取用户首页信息：博客信息、用户头像和文章分类。
 * 
 * @author Geurney
 *
 */
public class IndexCrawler extends Crawler {

	@Override
	public User crawl(String url, String path) {
		if (connect(url)) {
			User user = new User();
			user.setUrl(url);
			
			String html = document.html();
			String name = document.select("#blog_title").first().text();
			name = name.substring(0, name.lastIndexOf("的"));
			user.setName(name);
			
			name = Parser.fileNameValify(name);
			user.setPath(path + "\\" + name);

			user.setBlogInfo(Parser.bloggerParser(html));
			
			Elements categories = document.select("#panel_Category a[href]");
			for (Element e : categories) {
				Category category = new Category();
				category.setTitle(e.text());
				category.setUrl(e.absUrl("href"));
				category.setPath(user.getBlogFolder() + "\\" + Parser.fileNameValify(category.getTitle()));
				user.getCategoreis().add(category);
			}
			Element imageUrl = document.select(
					"img[src^=http://avatar.csdn.net/]").first();
			
			if (Util.downloadImage(imageUrl.absUrl("src"), user.getPath(), "user.jpg")) {		
				user.setProfileImage(user.getPath()+"\\user.jpg");
			} else {
				errorLog.add("用户头像下载失败：" + imageUrl.absUrl("src"));
			}
			return user;
		}
		return null;
	}

}
