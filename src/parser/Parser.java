package parser;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 剖析器.
 * @author Geurney
 *
 */
public class Parser {

	/**
	 * 文章标题剖析器
	 * @param html 待剖析的分类标题
	 * @param valifyName 是否合法化分类名
	 * @return 文章标题
	 */
	 public static String blogTitleParser(String html, boolean valifyName) {
		 String title = null;
		 Pattern pattern = Pattern.compile("<title>(.*?) -"); 
		 Matcher matcher = pattern.matcher(html); 
		 if (matcher.find()) { 
			 title = matcher.group(1); 
			 if (valifyName) {
				 title = Parser.fileNameValify(title);
			 }
		 }
		 return title; 
	 }
	 
	/**
	 * 文章分类剖析器
	 * @param html 待剖析的html内容
	 * @param valifyName 是否合法化分类名
	 * @return 文章分类
	 */
	public static List<String> blogCategoriesParser(String html, boolean valifyName) {
		List<String> categories = new ArrayList<String>();
		Pattern pattern = Pattern.compile("\'blog_articles_fenlei\']);\">(.*?)</a>"); 
		Matcher matcher = pattern.matcher(html); 
		while(matcher.find()) {
			String category = matcher.group(1);
			if (valifyName) {
				category = Parser.fileNameValify(category);
			}
			categories.add(category);
		}
		 return categories;
	}
	
	/**
	 * 文章剖析器。
	 * @param html 待剖析的html内容
	 * @return 文章内容
	 */
	public static String docParser(String html) {
		StringBuilder sb = new StringBuilder(html);
		int start;
		int end;
		// Remove toolbar
		start = 0;
		end = sb.indexOf("<div id=\"container\">");
		sb.delete(start, end);
		// Remove navigator
		start = sb.indexOf("<div id=\"navigator\">");
		end = sb.indexOf("<script type=\"text/javascript\">");
		sb.delete(start, end);
		// Remove ad
		start = sb.indexOf("<div class=\"ad_class\">");
		end = sb.indexOf("<div id=\"article_details\"");
		sb.delete(start, end);
		// Remove rest
		start = sb.indexOf("<!-- Baidu Button BEGIN -->");
		end = sb.length();
		sb.delete(start, end);
		return sb.toString();
	}
	
	/**
	 * 博客信息剖析器。
	 * @param html 待剖析的html内容
	 * @return 博客信息：访问、积分、排名、原创、转载、译文和评论。
	 */
	public static List<String> bloggerParser(String html) {
		List<String> result = new ArrayList<String>();
		int start;
		int end;
		// Get blog Info
		start = html.indexOf("<li>访问：<span>") + "<li>访问：<span>".length();
		end = html.indexOf("</span>", start);
		result.add(html.substring(start, end));

		start = html.indexOf("<li>积分：<span>") + "<li>积分：<span>".length();
		end = html.indexOf("</span>", start);
		result.add(html.substring(start, end));

		start = html.indexOf("<li>排名：<span>") + "<li>排名：<span>".length();
		end = html.indexOf("</span>", start);
		result.add(html.substring(start, end));

		// Get blog statistics
		start = html.indexOf("<li>原创：<span>") + "<li>原创：<span>".length();
		end = html.indexOf("</span>", start);
		result.add(html.substring(start, end));

		start = html.indexOf("<li>转载：<span>") + "<li>转载：<span>".length();
		end = html.indexOf("</span>", start);
		result.add(html.substring(start, end));

		start = html.indexOf("<li>译文：<span>") + "<li>译文：<span>".length();
		end = html.indexOf("</span>", start);
		result.add(html.substring(start, end));

		start = html.indexOf("<li>评论：<span>") + "<li>评论：<span>".length();
		end = html.indexOf("</span>", start);
		result.add(html.substring(start, end));
		return result;
	}
	
	/**
	 * 文章分类剖析器。
	 * @param html 待剖析的html内容
	 * @return 文章分类标题和链接
	 */
	public static List<SimpleEntry<String, String>> categoriresParser(String html) {
		List<SimpleEntry<String, String>> list = new ArrayList<SimpleEntry<String, String>>();
		String prefix = "http://blog.csdn.net";
		Pattern pattern;
		Matcher matcher;
		int start = html.indexOf("文章分类");
		while (true) {
			String category_url = null;
			pattern = Pattern.compile("<a href=\"(.*?)\" onclick");
			matcher = pattern.matcher(html);
			if (matcher.find(start)) {
				category_url = prefix + matcher.group(1);
			} else {
				break;
			}
			String category_name = null;
			pattern = Pattern
					.compile("wenzhangfenlei']\\); \">(.*?)</a><span>");
			matcher = pattern.matcher(html);
			if (matcher.find(start)) {
				category_name = matcher.group(1);
			}
			pattern = Pattern.compile("</a><span>(.*?)</span>");
			matcher = pattern.matcher(html);
			if (matcher.find(start)) {
				category_name = category_name + matcher.group(1);
			}
			Parser.fileNameValify(category_name);
			list.add(new SimpleEntry<String, String>(category_name,
					category_url));
			start = html.indexOf("</li>", start + 1);
		}
		return list;
	}
	
	
	/**
	 * 图片剖析器。
	 * @param html 待剖析的html内容
	 * @return 图片url链接
	 */
	public static List<String> imageParser(String html) {
		List<String> result = new ArrayList<String>();
		Pattern pattern = Pattern.compile("<img src=\"(.*?)\"");
		Matcher matcher = pattern.matcher(html);
		while(matcher.find()) {
			String url =matcher.group(1);
			if(url.length() != 0) {
				result.add(url);
			}
		}
		return result;
	}
	
	/**
	 * 分类文章数剖析器
	 * @param category 待剖析的分类标题
	 * @return 分类的文章数
	 */
	public static int numberOfLinksParser(String category) {
		int start = category.lastIndexOf("(") + 1;
		return Integer.valueOf(category.substring(start, category.length() - 1));
	}

	/**
	 * 图片地址本地化。将html中的图片地址变为本地的图片地址。
	 * @param html 待剖析的html内容
	 * @param title 文章标题
	 * @return 图片地址本地化后的文章内容
	 */
	public static String imageLocalize(String html, String title) {
		StringBuilder sb = new StringBuilder(html);
		int start = 0;
		int end = 0;
		int imgName = 1;
		while (true) {
			start = sb.indexOf("<img src=\"http", start);
			if (start == -1) {
				break;
			}
			start += "<img src=\"".length();
			end = sb.indexOf("\" ", start + 1);
			sb.replace(start, end, title + "\\" + (imgName++));
		}
		return sb.toString();
	}
	
	/**
	 * 更新链接。将html中的链接更新。
	 * @param html 待剖析的html内容
	 * @return 更新链接后的文章美容
	 */
	public static String updateLinks(String html) {
		StringBuilder sb = new StringBuilder(html);
		int start = sb.indexOf("\"link_title\"><a href=\"")
				+ "\"link_title\"><a href=\"".length();
		sb.insert(start, "http://blog.csdn.net");
		start = sb.indexOf("link_categories\"> 分类： <a href=\"")
				+ "link_categories\"> 分类： <a href=\"".length();
		sb.insert(start, "http://blog.csdn.net");
		return sb.toString();
	}

	/**
	 * 文件名合法化。替换文件名中的非法字符
	 * @param filename 待处理的文件名
	 * @return 处理后的文件名
	 */
	public static String fileNameValify(String filename) {
		String SPECIAL_CHARS = "[\\\\/:*?\"<>|]+";
		Pattern pattern = Pattern.compile(SPECIAL_CHARS);
		Matcher matcher = pattern.matcher(filename);
		if (matcher.find()) {
			filename = matcher.replaceAll("-");
		}
		return filename;
	}
	
	/**
	 * 为博客信息构建索引
	 * @param user 用户名
	 * @param imgUrl 头像地址
	 * @param blogInfo 博客信息
	 * @return 博客信息索引
	 */
	public static String blogInfoToIndex(String user, String imgUrl, List<String> blogInfo) {
		StringBuilder sb = new StringBuilder();
		sb.append("<title>"  +user + "的 CSDN博客</title>\n");		
		sb.append("<p><a href=\"" + "http://blog.csdn.net/"+ user
				+ "\"><font size=14px, color=orange>"  + user
				+ "</font></a></p>\n");
		sb.append("<table><tr>\n");
		sb.append("<td><img src=\"" + imgUrl + "\"" + "alt=\"头像\" align=\"left\"></td>\n");
		sb.append("<td><table>\n");
		sb.append("  <tr><td><table>\n");
		sb.append("    <tr><td><font size=2px>" + "访问：" + blogInfo.get(0) + "</font></td></tr>\n");
		sb.append("    <tr><td><font size=2px>" + "积分：" + blogInfo.get(1) + "</font></td></tr>\n");
		sb.append("    <tr><td><font size=2px>" + "排名：" + blogInfo.get(2) + "</font></td></tr>\n");
		sb.append("  </table></td></tr>\n");
		sb.append("  <tr><td><table>\n");
		sb.append("    <tr><td><font size=2px>" + "原创：" + blogInfo.get(3) + "</font></td></tr>\n");
		sb.append("    <tr><td><font size=2px>" + "转载：" + blogInfo.get(4) + "</font></td></tr>\n");
		sb.append("    <tr><td><font size=2px>" + "译文：" + blogInfo.get(5) + "</font></td></tr>\n");
		sb.append("    <tr><td><font size=2px>" + "翻译：" + blogInfo.get(6) + "</font></td></tr>\n");
		sb.append("  </table></td></tr>\n");
		sb.append("</table></td>\n");
		sb.append("</tr></table>\n");
		return sb.toString();
	}

	/**
	 * 为分类构建索引
	 * @param category 文章分类名称
	 * @param url 文章分类目录
	 * @return 文章分类索引
	 */
	public static String categoryToIndex(String category, String url) {
		StringBuilder sb = new StringBuilder();
		sb.append("<li><a href=\"" +  "file:\\\\\\" + url + "\" ");
		sb.append("title=\"" + category + "\">" + category);
		sb.append("</a></li>\n");
		sb.append("<ul style=\"list-style-type:none\">\n");
		return sb.toString();
	}

	/**
	 * 分类索引头
	 * @param url 博客分类下载地址
	 * @return 插入的首部
	 */
	public static String categoryOpenToIndex(String url) {
		return "<p><a href=\"" + "file:\\\\\\" + url + "\">博客目录:</a></p>\n";
	}
	
	/**
	 * 分类索引末尾
	 * @return 插入的结尾
	 */
	public static String categoryCloseToIndex() {
		return "</ul>\n";
	}
	
	/**
	 * 为文章构建索引
	 * @param title 文章标题 
	 * @param url 文章地址
	 * @return 文章索引
	 */
	public static String blogToIndex(String title, String url) {
		StringBuilder sb = new StringBuilder();
		sb.append("    <li><a href=\"" + "file:\\\\\\" + url + "\" ");
		sb.append("title=\"" + title + "\">" + title);
		sb.append("</li</a>\n");
		return sb.toString();
	}

}
