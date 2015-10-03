package crawler_test;

import static org.junit.Assert.*;

import org.junit.Test;

import type.Category;
import crawler.CategoryCrawler;

public class CategoryCrawlerTest {

	@Test
	public void test() {
		String url = "http://blog.csdn.net/Geurney/article/category/3053887";
		String blogFolder = "f:\\blogtest";
		CategoryCrawler crawler = new CategoryCrawler();
		Category category = crawler.crawl(url, blogFolder);
		assertEquals(url, category.getUrl());
		assertEquals("LintCode", category.getTitle());
		assertEquals("f:\\blogtest\\LintCode", category.getPath());
		assertEquals(38, category.getBlogs().size());
	}

}
