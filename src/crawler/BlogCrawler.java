package crawler;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import parser.Parser;
import type.Blog;
import util.Util;

/**
 * ÎÄÕÂÅÀ³æ¡£ÅÀÈ¡ÎÄÕÂÄÚÈİ¡£
 * @author Geurney
 *
 */
public class BlogCrawler extends Crawler {

	@Override
	public Blog crawl(String url, String blogFolder) {
		if (connect(url)) {
			Blog blog = new Blog();
			blog.setUrl(url);
			
			String title = document.select(".link_title").first().text();
			blog.setTitle(title);
			title = Parser.fileNameValify(title);
			
			blog.setPath(blogFolder + "\\" + title +".html");
			
			Elements categories = document.select(".link_categories a[href]");
			for (Element category : categories) {
				blog.getCategory().add(category.text());
			}
			
			String imageFolder = blogFolder + "\\" + title;
			Elements images = document.select(".article_content img[src]");
			for (int i = 0; i < images.size(); i++) {
				if (!Util.downloadImage(images.get(i).absUrl("src"), imageFolder, (i + 1) + "")) {
					errorLog.add("Í¼Æ¬ÏÂÔØÊ§°Ü: " + title + ": " + images.get(i));
				}
			}
			
			String html = Parser.docParser(document.html());
			html = Parser.imageLocalize(html, title);
			html = Parser.updateLinks(html);
			
			if (!Util.writeToFile(blogFolder, title +".html", html)) {
				errorLog.add("ÎÄÕÂ±£´æÊ§°Ü£º" + blog.getPath());
			}
			return blog;
		}
		return null;
	}
	
}
