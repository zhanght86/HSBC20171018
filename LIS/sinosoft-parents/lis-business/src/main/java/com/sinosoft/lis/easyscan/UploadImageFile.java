package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

import java.io.File;

import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 上载文件时索引的操作与管理处理 Edited by wellhi 2005.08.22 设计思路：
 * 1、在UloadPrepareBL，从数据库Es_Server_Info读取出ServerPort、PicPath、ServerBasePath；
 * 2、把ServerbasePath放在静态类EasyScanConfig中，以提高效率； 3、ServerBasePath + PicPath +
 * ManageCom + Date +文件名（“F” + PageID + 文件后缀） 组成上载时文件的保存路径； 4、ServerPort +
 * PicPath + ManageCom + Date + 文件名（“F” + PageID + 文件后缀）， 组成浏览器访问影像图片的路径；
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
public class UploadImageFile {
    private static Logger logger = Logger.getLogger(UploadImageFile.class);
    
	private VData mIndexData;
	private ES_DOC_MAINSet tES_DOC_MAINSet;
	private ES_DOC_PAGESSet tES_DOC_PAGESSet;
	private String strServerBasePath = "";
	private String strTempPath = "";
	private static final String CON_UPLOAD_TEMPPATH = "UploadTemp";

	public UploadImageFile(VData vData) {
		mIndexData = vData;
		tES_DOC_MAINSet = (ES_DOC_MAINSet) vData.get(0);
		tES_DOC_PAGESSet = (ES_DOC_PAGESSet) vData.get(1);
	}

	public String getTempPath() {
		String strTemp = strTempPath + CON_UPLOAD_TEMPPATH;
		createDir(strTemp);
		return strTemp;
	}	

	
	/**
	 * 获取文件路径的片段
	 * @param strPageCode
	 * @param strFileName
	 * @return 一个包含有2个元素的一维String数组，其为文件路径的片段 例如 String[] { 
	 *     "D:/lis_hx/ui/||xerox/EasyScan/||86/2005/10/11/", 
	 *     "F10012563.tif" }
	 */
	public String[] getPathPatterns(String strPageCode, String strFileName){
		String[] result = null;
		try{
			result = PathHelper.getPathPatterns(strPageCode, strFileName, tES_DOC_PAGESSet);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return result;
	}

    
    /**
     * 获得文件保存路径与名称，自动创建文件目录
     * @param pathPatterns 通过getPathPatternsAndFilename方法获取的pathPatterns
     * @return 例如 "/xerox/EasyScan/86/2005/10/11/F10012563.tif"
     */
	public String getSaveAsName(String[] pathPatterns) {
		if(pathPatterns == null || pathPatterns.length <= 1)
        	return "";
			// Edited by wellhi 2005.10.18
			// 从UploadPrepare传过来的路径
			// strPath="D:/lis_hx/ui/||xerox/EasyScan/||86/2005/10/11/";
				String strPath = pathPatterns[0];
                String filename = pathPatterns[1];
				// 解析从UploadPrepare传过来的路径
				// 基点路径
				// strServerBasePath="D:/lis_hx/ui/";
				int intPos1 = strPath.indexOf("||");
				strServerBasePath = strPath.substring(0, intPos1);
				strServerBasePath = PathHelper.autoTrimPath(strServerBasePath, true, false, '/');
				
				// 上载临时文件夹路径
				// strTempPath="D:/lis_hx/ui/xerox/EasyScan/";
				String strPath1 = strPath.substring(intPos1 + 2);
				int intPos2 = strPath1.indexOf("||");
				String strPicPath = strPath1.substring(0, intPos2);
				strPicPath = PathHelper.autoTrimPath(strPicPath, false, false, '/');
				strTempPath = strServerBasePath + strPicPath;

				// 文件保存的相对路径
				// strSavePath="xerox/EasyScan/86/2005/10/11/";
				String strSavePath = strPicPath
						+ PathHelper.autoTrimPath(strPath1.substring(intPos2 + 2), false, false, '/');

				// 文件保存的绝对路径
				// strPath="D:/lis_hx/ui/xerox/EasyScan/86/2005/10/11/";
				strPath = strServerBasePath + strSavePath;
				// 创建目标目录
				createDir(strPath);

				// 生成保存全路径文件名
				String strSaveAsName = strPath + filename;
				return strSaveAsName;
	}
    
	// 获得文件保存路径与名称，自动创建文件目录
	public String getSaveAsName(String strPageCode, String strFileName) {
		for (int i = 1; i <= tES_DOC_PAGESSet.size(); i++) {
			if (tES_DOC_PAGESSet.get(i).getPageCode() == Integer
					.parseInt(strPageCode.trim())) { // (int)
														// tES_DOC_PAGESSet.get(i).getPageCode()
														// ==
														// Integer.parseInt(strPageCode.trim()))//
			// Edited by wellhi 2005.10.18
			// 从UploadPrepare传过来的路径
			// strPath="D:/lis_hx/ui/||xerox/EasyScan/||86/2005/10/11/";
				String strPath = tES_DOC_PAGESSet.get(i).getPicPath();

				// 解析从UploadPrepare传过来的路径
				// 基点路径
				// strServerBasePath="D:/lis_hx/ui/";
				int intPos1 = strPath.indexOf("||");
				strServerBasePath = strPath.substring(0, intPos1);
				strServerBasePath = PathHelper.autoTrimPath(strServerBasePath, true, false, '/');
				
				// 上载临时文件夹路径
				// strTempPath="D:/lis_hx/ui/xerox/EasyScan/";
				String strPath1 = strPath.substring(intPos1 + 2);
				int intPos2 = strPath1.indexOf("||");
				String strPicPath = strPath1.substring(0, intPos2);
				strPicPath = PathHelper.autoTrimPath(strPicPath, false, false, '/');
				strTempPath = strServerBasePath + strPicPath;

				// 文件保存的相对路径
				// strSavePath="xerox/EasyScan/86/2005/10/11/";
				String strSavePath = strPicPath
						+ PathHelper.autoTrimPath(strPath1.substring(intPos2 + 2), false, false, '/');

				// 文件保存的绝对路径
				// strPath="D:/lis_hx/ui/xerox/EasyScan/86/2005/10/11/";
				strPath = strServerBasePath + strSavePath;
				// 创建目标目录
				createDir(strPath);

				// createDir(EasyScanConfig.getInstance().serverBasePath +
				// strSavePath);
				// 获得文件名后缀 Edited by wellhi 2005.08.22
				strFileName = strFileName.trim();
				int intDotPos = strFileName.lastIndexOf(".");
				String strSuffix = "";
				if(intDotPos >= 0)
					strSuffix =	strFileName.substring(intDotPos);
				// 生成保存全路径文件名
				String strSaveAsName = strPath + "F"
						+ (int) tES_DOC_PAGESSet.get(i).getPageID() + strSuffix;
				return strSaveAsName;
			}
		}
		return "";
	}

	public static String createDir(String path) {
		String msg = null;
		java.io.File dir;

		// 新建文件对象
		dir = new java.io.File(path);

		logger.debug("Absolute Path = " + dir.getAbsolutePath());

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

	public static void main(String[] args) {
		String str = "D:/test/abc.tif";
		File dir = new java.io.File("/abc.tif");
		logger.debug("Absolute Path = " + dir.getAbsolutePath());
	}
}
