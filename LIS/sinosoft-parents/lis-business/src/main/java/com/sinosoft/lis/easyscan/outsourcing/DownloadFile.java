package com.sinosoft.lis.easyscan.outsourcing;
import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: HTTP文件下载
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author liuxin
 * @version 1.0
 */

public class DownloadFile {
private static Logger logger = Logger.getLogger(DownloadFile.class);

	public final static boolean DEBUG = true; // 调试用
	private static int BUFFER_SIZE = 8096; // 缓冲区大小
	private Vector vDownLoad = new Vector(); // URL列表
	private Vector vFileList = new Vector(); // 下载后的保存文件名列表

	public DownloadFile() {
	}

	/** 清除下载列表* */
	public void resetList() {
		vDownLoad.clear();
		vFileList.clear();
	}

	/**
	 * 增加下载列表项
	 * 
	 * @param url
	 *            String
	 * @param filename
	 *            String
	 */
	public void addItem(String url, String fileName) {
		vDownLoad.add(url);
		vFileList.add(fileName);
	}

	/** 根据列表下载资源* */
	public boolean downLoadByList() {
		String url = null;
		String filename = null;
		// 按列表顺序保存资源
		for (int i = 0; i < vDownLoad.size(); i++) {
			url = (String) vDownLoad.get(i);
			filename = (String) vFileList.get(i);
			try {
				saveToFile(url, filename);
			} catch (IOException e) {
				e.printStackTrace();
				logger.debug("资源[" + url + "]下载失败!!!");
				return false;
			}
		}
		if (DEBUG) {
			logger.debug("下载完成!!!");
		}
		resetList();
		return true;
	}

	/**
	 * 将HTTP资源另存为文件
	 * 
	 * @param destUrl
	 *            String
	 * @param fileName
	 *            String
	 * @throws Exception
	 */
	public void saveToFile(String destUrl, String fileName) throws IOException {
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;
		try {
			// 建立链接
			url = new URL(destUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			int downsize = httpUrl.getContentLength();
			logger.debug(downsize);
			if (downsize == -1) {
				logger.debug("连接失败！");
			}
			// 连接指定的资源
			httpUrl.connect();
			// 获取网络输入流
			bis = new BufferedInputStream(httpUrl.getInputStream());
			// 建立文件
			fos = new FileOutputStream(fileName);
			if (DEBUG) {
				logger.debug("正在获取链接[" + destUrl + "]的内容...\n将其保存为文件["
						+ fileName + "]");
			}
			// 保存文件
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		fos.close();
		bis.close();
		httpUrl.disconnect();
	}

	public static void main(String[] args) {
		DocTBImageMoveRelationService p = new DocTBImageMoveRelationService();
		// p.createDir("d:/temp");
		DownloadFile downloadfile = new DownloadFile();
		// downloadfile.addItem("http://192.168.70.181:8080/ui/200772031563187E2.tif",
		// "d:/temp/1.tif");
		downloadfile.addItem(
				"http://192.168.70.181:8080/ui/86/2007/08/13/F231.gif",
				"d:/temp/F231.gif");
		downloadfile.downLoadByList();
	}
}
