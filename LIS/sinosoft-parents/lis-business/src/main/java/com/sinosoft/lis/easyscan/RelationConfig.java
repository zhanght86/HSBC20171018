/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
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
public class RelationConfig {
private static Logger logger = Logger.getLogger(RelationConfig.class);
	// 定义静态变量(单例模式)
	private static RelationConfig _config = new RelationConfig();

	// 判断是否成功载入XML文件
	private static boolean loadSuccess;

	// HttpServer在操作系统中的实际根目录
	public String serverBasePath = ":NULL PATH";

	// 图象文件的默认后缀
	public String fileSuffix = ".gif";

	// 是否分多级目录存储
	public boolean saveMultipleDir = true;

	public List tlist = null;
	// 图像存储格式
	public String imageType = "TIF";

	// 过滤地址数组,判断是否需要转发
	private String[] serverFilter = null;
	// 本地URL
	private String[][] SourceURL = null;
	// Apache转发URL，用于替换SourceURL
	private String[][] ApacheURL = null;

	// 存储Apache服务器地址
	// LQ 由于使用单例模式，当心模块变量会冲突
	private String apacheServer;

	/**
	 * 获取xml配置文件
	 */
	private RelationConfig() {
		try {
			// logger.debug("----- Reading ESRELATION File ----");
			// LDSysVarDB tLDSysVarDB = new LDSysVarDB();
			// tLDSysVarDB.setSysVar("EasyScanConfigFile");
			// LDSysVarSet tLDSysVarSet = tLDSysVarDB.query();
			// String configFile = tLDSysVarSet.get(1).getSysVarValue().trim() ;
			SAXBuilder builder = new SAXBuilder();
			// String configFile = "d:/esrelation.xml";
			// com/sinosoft/lis/easyscan/
			String configFile = "esrelation.xml";
			// String configFile = "lisconf/EasyScan.xml";
			logger.debug("EasyScanConfigFile=" + configFile);
			// Document doc = builder.build(configFile);
			ClassLoader loader = Thread.currentThread().getContextClassLoader();

			InputStream in = loader.getResourceAsStream(configFile);
			// FileInputStream in = new FileInputStream(configFile);

			Document doc = builder.build(in);
			Element root = doc.getRootElement();
			tlist = root.getChildren("DocType");
		} catch (Exception e) {
			e.printStackTrace();
			loadSuccess = false;
		}
	}

	/**
	 * 获得自身实例的方法
	 * 
	 * @return RelationConfig
	 */
	public static RelationConfig getInstance() {
		return _config;
	}

	/**
	 * xml解析
	 * 
	 * @param base
	 *            String
	 * @return String
	 */
	public String getrelation(String base) {
		String returnurl = "";
		for (int i = 0; i < tlist.size(); i++) {
			if (((Element) tlist.get(i)).getAttributeValue("standard").equals(
					base)) {
				returnurl = ((Element) tlist.get(i))
						.getAttributeValue("target");
			}
		}
		return returnurl;
	}

	/**
	 * xml反解析
	 * 
	 * @param base
	 *            String
	 * @return String
	 */
	public String getBackRelation(String base) {
		String returnurl = "";
		for (int i = 0; i < tlist.size(); i++) {
			if (((Element) tlist.get(i)).getAttributeValue("target").equals(
					base)) {
				returnurl = ((Element) tlist.get(i))
						.getAttributeValue("standard");
				break;
			}
		}
		return returnurl;
	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		RelationConfig tRelationConfig = RelationConfig.getInstance();
		String base = tRelationConfig.getrelation("1001");
		logger.debug(base);
	}
}
