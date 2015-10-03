package crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
/**
 * 爬虫抽象类。
 * @author Geurney
 *
 */
public abstract class Crawler {
	/**
	 * 浏览器header 
	 */
	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	
	/**
	 * 爬取的页面
	 */
	protected Document document;
	
	/**
	 * 爬虫错误日志
	 */
	protected List<String> errorLog = new ArrayList<String>();
	
	/**
	 * 爬取url链接
	 * @param url 链接地址
	 * @param path 保存地址
	 * @return Blog/Category. Return null if failed.
	 */
	public abstract Object crawl(String url, String path);

	/**
	 * 建立连接。最多尝试10次，重试间隔100ms。
	 * @param url url地址
	 * @return True 连接成功。False 连接失败
	 */
	protected boolean connect(String url) {
		int trytime = 1;
		while (true) {
			try {
				Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
				document = connection.get();
				if (connection.response().statusCode() == 200 && connection.response().contentType().contains("text/html")) {
					break;
				}
			} catch (IOException e1) {}

			if (trytime++ == 10) {
				errorLog.add("链接建立失败：" + url);
				return false;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			System.out.println("第" + trytime+ "次尝试连接:" + url);
		}
		return true;
	}
	
	/**
	 * 获取错误日志
	 * @return 错误日志
	 */
	public List<String> getErrorLog() {
		return errorLog;
	}
}
