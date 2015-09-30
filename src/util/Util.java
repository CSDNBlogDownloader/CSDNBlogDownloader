package util;

import java.awt.Desktop;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * 工具类。
 * @author Geurney
 *
 */
public abstract class Util {
	/**
	 * 下载图片
	 * @param imageUrl 图片url地址
	 * @param folderName 图片保存目录
	 * @param fileName 图片文件名
	 * @return True 图片下载成功。False 图片下载失败
	 */
	public static boolean downloadImage(String imageUrl, String folderName,
			String fileName) {
		URL url;
		try {
			url = new URL(imageUrl);
		} catch (MalformedURLException e) {
			System.out.println("错误：无法识别图片地址！  " + folderName + "  " + imageUrl);
			return false;
		}

		File folder = new File(folderName);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		OutputStream out;
		try {
			out = new BufferedOutputStream(new FileOutputStream(folderName
					+ "\\" + fileName));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("错误：无法创建图片文件！"  + folderName + "  " + imageUrl);
			return false;
		}

		InputStream in;
		int buffer;
		try {
			in = url.openStream();
			while((buffer = in.read()) != -1) {
				out.write(buffer);
			}
			out.close();
			in.close();
		} catch (IOException e) {
			System.out.println("错误：下载失败！"  + folderName + "  " + imageUrl);
			return false;
		}
		return true;
	}
	
	/**
	 * 打开网页浏览器
	 * @param url 网页地址
	 * @return True 打开成功。False 打开失败。
	 */
	public static boolean openBrowser(String url) {
		try {
	    	Desktop.getDesktop().browse(new URL(url).toURI());
	    	return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 打开文件浏览器
	 * @param path 文件路径
	 * @return True 打开成功。False 打开失败。
	 */
	public static boolean openExplorer(String path) {
		try {
			Runtime.getRuntime().exec("explorer.exe /select," + path);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * 读取文件
	 * @param filePath 文件路径
	 * @return 文件内容
	 * @throws IOException 读取文件失败
	 */
	public static String readFile(String filePath) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line;
		while((line = reader.readLine()) != null) {
			sb.append(line);
		}
		int buffer;
		while((buffer = reader.read()) != -1) {
				sb.append(buffer);
		}
		reader.close();
		return sb.toString();
	}
	
	/**
	 * 写入文件
	 * @param filePath 待写入文件路径
	 * @param content 待写入内容
	 * @return True 写入成功。False 写入失败
	 */
	public static boolean writeToFile(String filePath, String content) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			writer.write(content);
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("无法写入文件！" + filePath);
			return false;
		}
	}

}
