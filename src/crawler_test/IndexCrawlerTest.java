package crawler_test;

import static org.junit.Assert.*;

import org.junit.Test;

import type.User;
import crawler.IndexCrawler;

public class IndexCrawlerTest {

	@Test
	public void test() {
		String url = "http://blog.csdn.net/geurney";
		String blogFolder = "f:\\blogtest";
		IndexCrawler crawler = new IndexCrawler();
		User user = crawler.crawl(url, blogFolder);
		assertEquals(url, user.getUrl());
		assertEquals("Geurney", user.getName());
		assertEquals("f:\\blogtest\\Geurney", user.getPath());
		assertEquals("f:\\blogtest\\Geurney\\blog", user.getBlogFolder());
		assertEquals(20, user.getCategoreis().size());
		assertEquals("http://avatar.csdn.net/4/6/F/1_geurney.jpg", user.getProfileImage());
		assertEquals(7, user.getBlogInfo().size());
	}

}
