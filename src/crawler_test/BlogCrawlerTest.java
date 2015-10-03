package crawler_test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import type.Blog;
import crawler.BlogCrawler;

public class BlogCrawlerTest {

	@Test
	public void test() {
		String url = "http://blog.csdn.net/geurney/article/details/46955007";
		String blogFolder = "f:\\blogtest";
		BlogCrawler crawler = new BlogCrawler();
		Blog blog = crawler.crawl(url, blogFolder);
		assertEquals(0, crawler.getErrorLog().size());
		assertEquals(url, blog.getUrl());
		assertEquals("Java Collection Java集合总结", blog.getTitle());
		assertEquals("f:\\blogtest\\Java Collection Java集合总结.html", blog.getPath());
		assertEquals(Arrays.asList("Head First Java", "Data Structure"), blog.getCategory());
	}

}
