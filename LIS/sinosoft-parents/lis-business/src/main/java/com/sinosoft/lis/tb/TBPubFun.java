package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.DOMBuilder;

import com.sinosoft.utility.CErrors;

/**
 * <p>
 * Title:TBPubFun
 * </p>
 * <p>
 * Description: 投保公共处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author Fuqx
 * @version 1.0
 */

public class TBPubFun {
private static Logger logger = Logger.getLogger(TBPubFun.class);
	/** 错误类 */
	CErrors mErrors = new CErrors();

	public TBPubFun() {
	}

	/**
	 * 依据InputStream 产生Document
	 * 
	 * @param is
	 * @return Document
	 */
	public static Document produceXmlDoc(InputStream is) {
		Document doc = null;
		DOMBuilder domB = new DOMBuilder();
		try {
			doc = domB.build(is);
		} catch (JDOMException ex) {
			ex.printStackTrace();
		}
		return doc;
	}

	/**
	 * 依据文件名产生Document
	 * 
	 * @param tFileName
	 * @return Document
	 */
	public static Document produceXmlDoc(String tFileName) {

		Document doc = null;
		DOMBuilder domB = new DOMBuilder();
		try {
			InputStream is = new FileInputStream(tFileName);
			doc = domB.build(is);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return doc;
	}

	/**
	 * 得到制定目录下的多个文件名称
	 * 
	 * @param tPath
	 *            指定路径
	 * @param tMaxCount
	 *            提取文件名的最大数量
	 * @return 包含文件名的字符数组
	 */
	public static String[] getFilesList(String tPath, int tMaxCount) {
		String[] tFilesList;
		try {
			String[] tReturnFilesList;
			File tFile = new File(tPath);
			tFilesList = tFile.list();
			if (tFilesList != null && tFilesList.length > 0) {
				tReturnFilesList = new String[tMaxCount>tFilesList.length?tFilesList.length:tMaxCount];
				for (int i = 0; i < tReturnFilesList.length; i++) {
				tReturnFilesList[i] = tFilesList[i];
				}

				tFilesList = tReturnFilesList;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return tFilesList;
	}

	/**
	 * 移动指定目录下的多个文件
	 * 
	 * @param tPath
	 *            指定路径
	 * @param tMaxCount
	 *            提取文件名的最大数量
	 * @return 包含文件名的字符数组
	 */
	public static boolean moveFiles(String[] tFilesList, String tOldPath,
			String tNewPath) {
		try {
			if (tFilesList != null && tFilesList.length > 0) {
				// 如果目标路径为空，则默认新路径和源路径一致
				if ("".equals(tNewPath)) {
					tNewPath = tOldPath;
				}

				File tOldPathFile = new File(tOldPath);
				if (!tOldPathFile.exists()) {
					logger.debug("***源路径不存在: " + tOldPath);
					return false;
				}
				// 判断目标路径是否存在，如果不存在，新建目标路径
				File tNewPathFile = new File(tNewPath);
				if (!tNewPathFile.exists()) {
					logger.debug("***目标路径不存在，需要新建目录: " + tNewPath);
					if (!tNewPathFile.mkdirs()) // Creates the directory named by this
					// abstract pathname, including any
					// necessary but nonexistent parent
					// directories
					{
						logger.debug("***目标路径新建失败！");
						return false;
					}
					logger.debug("***目标路径新建成功！");
				}

				for (int i = 0; i < tFilesList.length; i++) {
					if (tFilesList[i] == null || "".equals(tFilesList[i])) {
						logger.debug("源文件为空！");
						continue;
					}
					File tOldFile = new File(tOldPath + tFilesList[i]);
					if (!tOldFile.exists()) {
						logger.debug("源文件不存在：" + tOldPath + tFilesList[i]);
						continue;
					}

					// copy
					logger.debug("Start CopyFile：" + tOldPath
							+ tFilesList[i]);
					copyFile(tOldPath + tFilesList[i], tNewPath + tFilesList[i]
							+ ".Z");
					// delete old
					logger.debug("Start Delete：" + tOldPath
							+ tFilesList[i]);
					if (!tOldFile.delete()) {
						logger.debug("文件转移失败：" + tOldPath + tFilesList[i]);
					}
					// if(!tOldFile.renameTo(new File(tNewPath+tFilesList[i]+".Z")))
					// {
					// logger.debug("文件转移失败："+tOldPath+tFilesList[i]);
					// }
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 移动指定目录下的一个文件
	 * 
	 * @param tPath
	 *            指定路径
	 * @param tMaxCount
	 *            提取文件名的最大数量
	 * @return 包含文件名的字符数组
	 */
	public static boolean moveFiles(String tFilesName, String tOldPath,
			String tNewPath) {
		try {
			if (tFilesName != null && !"".equals(tFilesName)) {
				// 如果目标路径为空，则默认新路径和源路径一致
				if ("".equals(tNewPath)) {
					tNewPath = tOldPath;
				}
				File tOldPathFile = new File(tOldPath);
				if (!tOldPathFile.exists()) {
					logger.debug("***源路径不存在: " + tOldPath);
					return false;
				}
				// 判断目标路径是否存在，如果不存在，新建目标路径
				File tNewPathFile = new File(tNewPath);
				if (!tNewPathFile.exists()) {
					logger.debug("***目标路径不存在，需要新建目录: " + tNewPath);
					if (!tNewPathFile.mkdirs()) // Creates the directory named by this
					// abstract pathname, including any
					// necessary but nonexistent parent
					// directories
					{
						logger.debug("***目标路径新建失败！");
						return false;
					}
					logger.debug("***目标路径新建成功！");
				}
				// 转移文件
				File tOldFile = new File(tOldPath + tFilesName);
				if (!tOldFile.exists()) {
					logger.debug("源文件不存在：" + tOldPath + tFilesName);
					return false;
				}
				// copy
				logger.debug("Start CopyFile：" + tOldPath + tFilesName);
				copyFile(tOldPath + tFilesName, tNewPath + tFilesName + ".Z");
				// delete old
				logger.debug("Start Delete：" + tOldPath + tFilesName);
				if (!tOldFile.delete()) {
					logger.debug("文件转移失败：" + tOldPath + tFilesName);
				}
				// if(!tOldFile.renameTo(new File(tNewPath+tFilesName+".Z")))
				// {
				// logger.debug("文件转移失败："+tOldPath+tFilesName);
				// }
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 复制文件 参数 ： fromFile 源文件 返回值： toFile 目的文件
	 */
	public static void copyFile(String fromFile, String toFile)
			throws FileNotFoundException, IOException, Exception {
		FileInputStream in = new FileInputStream(fromFile);
		FileOutputStream out = new FileOutputStream(toFile);

		byte b[] = new byte[1024];
		int len;
		while ((len = in.read(b)) != -1) {
			out.write(b, 0, len);
		}
		out.flush();
		out.close();
		in.close();
	}

	/**
	 * 去除换行符
	 * 
	 * @param s
	 *            String
	 * @return String
	 */
	public static String changForBR(String s) {
		char[] arr = s.toCharArray();
		s = "";

		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == '"' || arr[i] == '\'') {
				s = s + "\\";
			}

			if (arr[i] == '\n') {
				continue;
			}
			if (arr[i] == '\r')
				continue;

			s = s + arr[i];
		}

		return s;
	}

	public static void main(String[] args) {
		// TBPubFun TBPubFun1 = new TBPubFun();
		// String [] tArrayFiles = new String [3] ;
		// tArrayFiles[0] = "项目2.mpp.Z";
		// tArrayFiles[1] = "";
		// tArrayFiles[2] = "";
		// TBPubFun.moveFiles(tArrayFiles,"E:\\bpo\\bpoinputpath\\","");

		String tFileName = "1.xml";
		TBPubFun.moveFiles(tFileName, "/sdb/insource/", "/sdb/insourcebak/");
		// String tBOPID ="";
		// Document tAllDocument =
		// TBPubFun.produceXmlDoc("E:/bpo/bpoinputpath/外包方承保数据返回格式.xml");
		// Element root = tAllDocument.getRootElement();
		// tBOPID = root.getChild("Company").getTextTrim();
		// logger.debug("tBOPID: "+tBOPID);
	}
}
