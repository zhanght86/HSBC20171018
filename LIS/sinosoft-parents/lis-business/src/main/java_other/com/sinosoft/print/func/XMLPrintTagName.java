package com.sinosoft.print.func;

import java.util.Hashtable;

/**
 * 提供标准规范的XML的tag名称类
 * 
 * @author huangliang
 * @since 2012-07-04
 */
public final class XMLPrintTagName {
	private XMLPrintTagName() {
	}

	/**
	 * 存放XML标签的标准名称（作为Value）和中文名称（作为key）
	 * 
	 */
	private static Hashtable<String, String> tagName;

	private static boolean initFlag = false;

	/**
	 * 表头名称
	 */
	public final static String TAG_NAME_HEAD = "HEAD";

	/**
	 * 行名称
	 */
	public final static String TAG_NAME_ROW = "ROW";

	/**
	 * 列名称
	 */
	public final static String TAG_NAME_COL = "COL";

	/**
	 * 将模板中标准的行列名转化为，在xmlexportNew中压缩了的行名和列名
	 * 
	 * @param pathArr
	 * @return
	 */
	public static String[] changeROWToR(String[] pathArr) {
		// TODO Auto-generated method stub
		for (int i = 0, size = pathArr.length; i < size; i++) {
			if (pathArr[i].equalsIgnoreCase("ROW")) {
				pathArr[i] = "R";
			}
		}
		return pathArr;
	}

	/**
	 * 将模板中标准的行列名转化为，在xmlexportNew中压缩了的行名和列名
	 * 
	 * @param pathArr
	 * @return
	 */
	public static String changeROWToR(String pathArr) {
		// TODO Auto-generated method stub
		if (pathArr.matches(".*/ROW")) {
			pathArr = pathArr.replaceFirst("/ROW", "/R");
		}
		return pathArr;
	}
	
	/**
	 * 将模板中标准的行列名转化为，在xmlexportNew中压缩了的行名和列名
	 * 
	 * @param pathArr
	 * @return
	 */
	public static String changeCOLTOC(String strChildPath) {
		// TODO Auto-generated method stub
		if (strChildPath.matches("COL[0-9]*")) {
			strChildPath = strChildPath.replaceAll("COL", "C");
		}
		return strChildPath;
	}
     public static String changeRowColToRC(String path){
		if(path.matches(".*/ROW/COL[0-9].*"))
			path = path.replaceFirst("/ROW/COL", "/R/C");
		return path;
	}
	/**
	 * 从数据源中读取标签的标准命名，存放到hashtable中
	 * 
	 */
	public static void init() {
		tagName = new Hashtable<String, String>();
		tagName.put("投保人邮政编码", "Post");
		tagName.put("投保单号", "LCCont.ContNo");
		tagName.put("体检医院", "HospitName");
		tagName.put("体检医院地址", "HospitAddress");
		tagName.put("代理人姓名", "LAAgent.Name");
		tagName.put("代理人业务号", "LAAgent.AgentCode");
		tagName.put("营业机构", "LCCont.ManageCom");
		initFlag = true;
	}

	/**根据中文名称（或者英文名称）返回标准标签名称，否则返回原名
	 * @param name
	 * @return
	 */
	public static String getName(String name) {
		if (!initFlag)
			init();
		String standardName = name;
		if (tagName.containsKey(name)) {
			standardName = tagName.get(name);
		}
		return standardName;
	}

	public static void main(String[] args) {
		XMLPrintTagName.TAG_NAME_COL.toString();
	}
}
