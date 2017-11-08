package com.sinosoft.lis.easyscan.outsourcing;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 文件操作类
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

public class FileOperate {
private static Logger logger = Logger.getLogger(FileOperate.class);

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public FileOperate() {
	}

	/**
	 * 新建目录
	 * 
	 * @param folderPath
	 *            String 如 c:/fqf
	 * @return boolean
	 */
	public boolean newFolder(String folderPath) {
		try {
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
		} catch (Exception e) {
			logger.debug("新建目录操作出错");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 新建文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String 文件内容
	 * @return boolean
	 */
	public boolean newFile(String filePathAndName, String fileContent) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			resultFile.close();
		} catch (Exception e) {
			logger.debug("新建目录操作出错");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public boolean delFile(File filePathAndName) {
		try {
			filePathAndName.delete();

		} catch (Exception e) {
			logger.debug("删除文件操作出错");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param filePathAndName
	 *            String 文件夹路径及名称 如c:/fqf
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public boolean delFolder(File folderPath) {
		try {
			if (folderPath == null)
				return true;
			delAllFile(folderPath); // 删除完里面所有内容
			folderPath.delete(); // 删除空文件夹
		} catch (Exception e) {
			logger.debug("删除文件夹操作出错");
			e.printStackTrace();
			return false;

		}
		return true;
	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            String 文件夹路径 如 c:/fqf
	 */
	public void delAllFile(File file) {
		if (file == null)
			return;
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			temp = new File(file, tempList[i]);
			if (temp.isFile()) {
				temp.delete();
			} else if (temp.isDirectory()) {
				delAllFile(temp); // 先删除文件夹里面的文件
				delFolder(temp); // 再删除空文件夹
			}
		}
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public boolean copyFile(File oldPath, File newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = oldPath;
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				logger.debug("file size: "+bytesum);
				inStream.close();
				fs.close();
			} else {
				logger.debug("复制单个文件操作出错——文件不存在！");
				return false;
			}
		} catch (Exception e) {
			logger.debug("复制单个文件操作出错");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @return boolean
	 */
	public boolean copyFolder(File oldPath, File newPath) {

		try {
			newPath.mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = oldPath;
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				temp = new File(oldPath, file[i]);

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(new File(
							newPath, temp.getName()));
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) { // 如果是子文件夹
					copyFolder(temp, new File(newPath, file[i]));
				}
			}
		} catch (Exception e) {
			logger.debug("复制整个文件夹内容操作出错");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 移动文件到指定目录
	 * 
	 * @param oldPath
	 *            String 如：c:/fqf.txt
	 * @param newPath
	 *            String 如：d:/fqf.txt
	 */
	public boolean moveFile(File oldPath, File newPath) {
		if (!copyFile(oldPath, newPath)) {
			CError tError = new CError();
			tError.moduleName = "FileOperate";
			tError.functionName = "moveFile";
			tError.errorNo = "-500";
			tError.errorMessage = "移动文件到指定目录出错!";
			return false;
		}
		if (!delFile(oldPath)) {
			CError tError = new CError();
			tError.moduleName = "FileOperate";
			tError.functionName = "moveFile";
			tError.errorNo = "-500";
			tError.errorMessage = "移动文件到指定目录出错!";
			return false;
		}
		return true;
	}

	/**
	 * 移动文件到指定目录
	 * 
	 * @param oldPath
	 *            Str 如：c:/fqf.txt
	 * @param newPath
	 *            String 如：d:/fqf.txt
	 */
	public boolean moveFolder(File oldPath, File newPath) {

		if (!copyFolder(oldPath, newPath)) {
			CError tError = new CError();
			tError.moduleName = "FileOperate";

			tError.functionName = "moveFolder";
			tError.errorNo = "-500";
			tError.errorMessage = "移动文件夹到指定目录出错!";
			return false;
		}
		if (!delFolder(oldPath)) {
			CError tError = new CError();
			tError.moduleName = "FileOperate";
			tError.functionName = "moveFolder";
			tError.errorNo = "-500";
			tError.errorMessage = "移动文件夹到指定目录出错!";
			return false;
		}
		return true;
	}

	// 创建路径
	public String createDir(File path) {

		String msg = null;
		java.io.File dir;
		// 建文件对象

		dir = path;
		// logger.debug("Absolute Pth = " + dir.getAbsolutePath());

		if (dir == null) {
			msg = "错误原因:＜BR＞对不起，不能创建空目录！";
			return msg;
		}
		if (dir.isFile()) {
			msg = "错误原因:＜BR＞已有同名文件＜B＞" + dir.getAbsolutePath() + "＜/B＞存在。";

			return msg;
		}
		if (!dir.exists()) {
			boolean result = dir.mkdirs();

			if (result == false) {
				msg = "错误原因:＜BR＞目录＜b＞" + dir.getAbsolutePath()
						+ "＜/B＞创建失败，原因不明！";

				return msg;
			}
			return msg;
		}

		return msg;
	}
}
