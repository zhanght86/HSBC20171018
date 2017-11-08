package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Liuqiang
 * @version 1.0
 */
public class EasyScanConfig {
private static Logger logger = Logger.getLogger(EasyScanConfig.class);
	// 定义静态变量(单例模式)
	private static EasyScanConfig _config = new EasyScanConfig();

	// 判断是否成功载入XML文件
	private static boolean loadSuccess;

	// HttpServer在操作系统中的实际根目录
	public String serverBasePath = ":NULL PATH";

	// 图象文件的默认后缀
	public String fileSuffix = ".gif";

	// 是否分多级目录存储
	public boolean saveMultipleDir = true;

	// 图像存储格式
	public String imageType = "TIF";

	// 过滤地址数组,判断是否需要转发
	private String[] serverFilter = null;
	// 本地URL
	private String[][] SourceURL = null;
	// Apache转发URL，用于替换SourceURL
	private String[][] ApacheURL = null;

	// 存储Apache服务器地址
	private String apacheServer; // LQ 由于使用单例模式，当心模块变量会冲突

	private EasyScanConfig() {
		try {
			logger.debug("----- Reading EasyScanConfig File ----");

			// LDSysVarDB tLDSysVarDB = new LDSysVarDB();
			// tLDSysVarDB.setSysVar("EasyScanConfigFile");
			// LDSysVarSet tLDSysVarSet = tLDSysVarDB.query();
			// String configFile = tLDSysVarSet.get(1).getSysVarValue().trim() ;

			SAXBuilder builder = new SAXBuilder();

			String configFile = "EasyScan.xml"; // com/sinosoft/lis/easyscan/
			// String configFile = "lisconf/EasyScan.xml";
			logger.debug("EasyScanConfigFile=" + configFile);
			// Document doc = builder.build(configFile);
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream in = loader.getResourceAsStream(configFile);
			Document doc = builder.build(in);

			Element root = doc.getRootElement();
			// 直接写在数据库,不用EasyScan.xml的节点
			// serverBasePath = root.getChild("ServerBasePath").getText();
			fileSuffix = root.getChild("FileSuffix").getText();
			imageType = root.getChild("SaveFileType").getText();

			if (root.getChild("SaveMultipleDir").getText().equalsIgnoreCase(
					"true")) {
				saveMultipleDir = true;
			} else {
				saveMultipleDir = false;
			}

			List nodeList = root.getChildren("ApacheServerName");
			// Element enode = root.getChild("Relation");
			serverFilter = new String[nodeList.size()];
			int tCount;

			SourceURL = new String[nodeList.size()][10];
			ApacheURL = new String[nodeList.size()][10];

			for (int i = 0; i < nodeList.size(); i++) {
				tCount = ((Element) nodeList.get(i)).getChildren("Relation")
						.size();

				serverFilter[i] = ((Element) nodeList.get(i))
						.getAttributeValue("url");
				Element node = (Element) root.getChildren("ApacheServerName")
						.get(i);
				List childList = node.getChildren("Relation");
				for (int j = 0; j < tCount; j++) {
					SourceURL[i][j] = ((Element) childList.get(j))
							.getAttributeValue("SourceURL");
					ApacheURL[i][j] = ((Element) childList.get(j))
							.getAttributeValue("ApacheURL");
				}
			}
			loadSuccess = true;
			logger.debug("----- EasyScanConfig File Readed ----");
		} catch (Exception e) {
			e.printStackTrace();
			loadSuccess = false;
		}
	}

	// 获得自身实例的方法
	public static EasyScanConfig getInstance() {
		if (!loadSuccess) {
			logger.debug("EasyScanConfig警告:载入EasyScan.xml文件出现错误，EasyScan相关处理可能会不正常");
		}
		return _config;
	}

	// 判断clientURL是不是Apache转发的URL
	public boolean isForward(String clientURL, StringBuffer ImageUrl) {
		if ((clientURL == null) || (clientURL.trim().length() == 0)) {
			return false;
		}
		if (serverFilter == null) {
			return false;
		}

		String strUrl = new String(ImageUrl);
		for (int i = 0; i < serverFilter.length; i++) {
			// StringBuffer buf = new StringBuffer();
			if (clientURL.length() >= serverFilter[i].length()) {
				if (clientURL.substring(0, serverFilter[i].length())
						.equalsIgnoreCase(serverFilter[i])) {
					apacheServer = serverFilter[i];
					for (int j = 0; j < SourceURL[i].length; j++) {
						if (strUrl.equals(SourceURL[i][j]) == true) {
							ImageUrl.delete(0, SourceURL[i][j].length());
							ImageUrl.append(ApacheURL[i][j]);
							return true;
						}
						if (SourceURL[i][j] == null) {
							break;
						}
					}
				}
			}
		}

		apacheServer = "";
		return false;
	}

	public boolean setServerBasePath(String strServerBasePath) {
		serverBasePath = strServerBasePath;
		return true;
	}

	public static void main(String[] args) {
		StringBuffer buf = new StringBuffer();
		buf.append("http://localhost:8080/");

		logger.debug("BasePath="
				+ EasyScanConfig.getInstance().serverBasePath);
		logger.debug("FileSuffix="
				+ EasyScanConfig.getInstance().fileSuffix);
		logger.debug("saveMultipleDir="
				+ EasyScanConfig.getInstance().saveMultipleDir);
		logger.debug("isForward="
				+ EasyScanConfig.getInstance().isForward("http://localhost/",
						buf));
		logger.debug(buf);
	}
}
