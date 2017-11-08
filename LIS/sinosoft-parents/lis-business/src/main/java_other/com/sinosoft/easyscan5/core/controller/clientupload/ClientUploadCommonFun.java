package com.sinosoft.easyscan5.core.controller.clientupload;

import java.net.URL;

public class ClientUploadCommonFun {
	private String moduleName = "客户端上载公共类";

	/**
	 * 生成图像保存的子目录
	 * 
	 * @param manageCom
	 * @param inputDate
	 * @return
	 */
	public String getPicPath(String manageCom, String inputDate) {
		StringBuffer bufSubDir = new StringBuffer(manageCom);
		bufSubDir.append("/");
		bufSubDir.append(inputDate.substring(0, 4));
		bufSubDir.append("/");
		bufSubDir.append(inputDate.substring(5, 7));
		bufSubDir.append("/");
		bufSubDir.append(inputDate.substring(8, 10));
		bufSubDir.append("/");
		return bufSubDir.toString();
	}

	public String getReturnMessage(String strErr) {
		
		StringBuffer bufXML = new StringBuffer(1024);
		bufXML.delete(0, bufXML.length());
		bufXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><data><head></head><result code=\"-500\">");
		bufXML.append("<message>");
		bufXML.append(strErr);
		bufXML.append("</message></result><body></body></data>");
		return "<IndexXML>" + bufXML.toString() + "</IndexXML>";
	}

	public String getNewReturnMessage(String strErr) {
		
		StringBuffer bufXML = new StringBuffer(1024);
		bufXML.delete(0, bufXML.length());
		bufXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><data><head></head><result code=\"-500\">");
		bufXML.append("<message>");
		bufXML.append(strErr);
		bufXML.append("</message></result><body></body></data>");
		return "<IndexXML>" + bufXML.toString() + "</IndexXML>";
	}

	/**
	 * 获取问题件ID
	 * 
	 * @param comAndIssueid
	 * @return
	 */
	public String getIssueid(String comAndIssueid) {
		int pos = comAndIssueid.indexOf("||");
		String issueId = comAndIssueid.substring(pos + 2);
		return issueId;
	}

	/**
	 * 获取机构信息
	 * 
	 * @param comAndIssueid
	 * @return
	 */
	public String getCom(String comAndIssueid) {
		int pos = comAndIssueid.indexOf("||");
		if (pos == -1) {
			return comAndIssueid;
		}
		String manageCom = comAndIssueid.substring(0, pos);
		return manageCom;
	}

	/**
	 * 获取项目根路径
	 * 返回：/D:/workspace2/ImageNew/WebRoot/
	 * @return
	 */
	public String getServerBasePath() {
		URL url = this.getClass().getResource("");
		String path = url.getPath();
		path = path.substring(0, path.lastIndexOf("WEB-INF"));
		String serverBasePath = path;
		return serverBasePath;
	}

	/**
	 * 获取全路径 
	 * 参数：/D:/workspace2/ImageNew/WebRoot/||xerox/EasyScan/||86/2013/05/22/
	 * 返回：/D:/workspace2/ImageNew/WebRoot/xerox/EasyScan/86/2013/05/22/
	 * @param tempPath
	 * @return
	 */
	public String getSaveAllPath(String tempPath) {
		String serBasePath = getServerBasePath();
		String dbPicPath = getDBPicPath(tempPath);
		String relativPath = getRelativePath(tempPath);
		String saveAllPath = serBasePath + relativPath + dbPicPath;
		return saveAllPath;
	}

	/**
	 * 获取相对路径 
	 * 参数：/D:/workspace2/ImageNew/WebRoot/||xerox/EasyScan/||86/2013/05/22/
	 * 返回：xerox/EasyScan/
	 * @param tempPath
	 * @return
	 */
	public String getRelativePath(String tempPath) {
		int endIndex = tempPath.lastIndexOf("||");
		int beginIndex = tempPath.indexOf("||");
		tempPath = tempPath.substring(beginIndex + 2, endIndex);
		return tempPath;
	}

	/**
	 * 获取数据库存储的相对路径 
	 * 参数：/D:/workspace2/ImageNew/WebRoot/||xerox/EasyScan/||86/2013/05/22/
	 * 返回：86/2013/05/22/
	 * @param tempPath
	 * @return
	 */
	public String getDBPicPath(String tempPath) {
		int endIndex = tempPath.lastIndexOf("||");
		tempPath = tempPath.substring(endIndex + 2);
		return tempPath;
	}

	/**
	 * 获取文件保存绝对路径
	 * D:/workspace2/ImageNew/WebRoot/||xerox/EasyScan/||86/2013/05/22/
	 * @return
	 * @throws Exception
	 */
	public String createSavePath(String manageCom, String currentDateStr, String picPath) throws Exception {
		String comPath = getPicPath(manageCom, currentDateStr);
		String serverBasePath = getServerBasePath();
		return serverBasePath + "||" + picPath + "||" + comPath;
	}

	public String getReturnXmlMessage(String strErr, String resultCode) {
		// �������쳣�������쳣��Ϣ
		StringBuffer bufXML = new StringBuffer(1024);
		bufXML.delete(0, bufXML.length());
		bufXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><data><head></head><result code=\"");
		bufXML.append(resultCode);
		bufXML.append("\">");
		bufXML.append("<message>");
		bufXML.append(strErr);
		bufXML.append("</message></result><body></body></data>");
		return "<IndexXML>" + bufXML.toString() + "</IndexXML>";
	}
}
