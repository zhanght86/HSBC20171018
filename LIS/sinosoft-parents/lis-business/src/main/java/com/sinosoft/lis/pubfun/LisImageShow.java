package com.sinosoft.lis.pubfun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.ES_DOC_PAGESDB;
import com.sinosoft.lis.db.ES_SERVER_INFODB;
import com.sinosoft.lis.easyscan.PathHelper;
import com.sinosoft.lis.easyscan.cloud.CloudObjectStorageDownloader;
import com.sinosoft.lis.schema.ES_DOC_PAGESSchema;
import com.sinosoft.lis.schema.ES_SERVER_INFOSchema;
import com.sinosoft.utility.CacheUtil;

public class LisImageShow {
	private static Logger logger = Logger.getLogger(LisImageShow.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 LisImageShow tLisImageShow = new LisImageShow();
		 tLisImageShow.getImage("10012518");
		 System.out.println("22");
	}
	

	/**
	 * 从云端获取图像流
	 * @param pageId
	 * @return 返回值将是一个java.io.ByteArrayInputStream 实例
	 */
	public java.io.InputStream getImageFromCloudObjectStorage(String pageId, CloudObjectStorageDownloader cosd){
		if(cosd == null)
			return null;
		java.io.InputStream result = null;
		try{
			ES_DOC_PAGESSchema tES_DOC_PAGESSchema = this.queryES_DOC_PAGES(pageId);
			if(tES_DOC_PAGESSchema == null){
				return null;
			}
			//影像使用的服务器 EASYSCANTEST
			//String hostName = tES_DOC_PAGESSchema.getHostName();
			//页名 F10012540
			String pageName = tES_DOC_PAGESSchema.getPageName();
			pageName = PathHelper.autoTrimPath(pageName, false, true, '/');
			//页类型 .gif
			String pageSuffix = tES_DOC_PAGESSchema.getPageSuffix();
			//页相对路径 xerox/EasyScan/2016/05/05/861100/
			String pagePath = tES_DOC_PAGESSchema.getPicPath();
			logger.debug("正在从 " + cosd.getFriendlyCloudName() + "获取图像数据.");
			String cloudPath = cosd.getCloudFilePath(pagePath, pageName + pageSuffix);
			result = cosd.downloadFileFromCloud(cloudPath);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return result;
	}
	
	public FileInputStream getImage(String pageId){	
		
		ES_DOC_PAGESSchema tES_DOC_PAGESSchema = this.queryES_DOC_PAGES(pageId);
		if(tES_DOC_PAGESSchema==null){
			return null;
		}else{
			//影像使用的服务器
			String hostName = tES_DOC_PAGESSchema.getHostName();
			//页名
			String pageName = tES_DOC_PAGESSchema.getPageName();
			//页类型
			String pageSuffix = tES_DOC_PAGESSchema.getPageSuffix();
			//页相对路径
			String pagePath = tES_DOC_PAGESSchema.getPicPath();
			
			String basePath = this.getServerBasePath(hostName);
			
			String truePath = basePath + pagePath + pageName + pageSuffix;
			
			logger.info("truePath:"+truePath);
			
			File imageFile = new File(truePath);
			CacheUtil.putObjectCached("", truePath, imageFile);
			if(imageFile.exists()){
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(imageFile);
					return fis;
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}else{
				return null;
			}
		}
	
	}
	
	
	/**
	 * 查询影像页信息
	 * @param pageId
	 * @return
	 */
	private ES_DOC_PAGESSchema queryES_DOC_PAGES(String pageId){
		ES_DOC_PAGESDB tES_DOC_PAGESDB = new ES_DOC_PAGESDB();
		tES_DOC_PAGESDB.setPageID(pageId);
		if(tES_DOC_PAGESDB.getInfo()){
			return tES_DOC_PAGESDB.getSchema();
		}else{
			return null;
		}	
	}
	
	
	/**
	 * 获取服务器的实际地址
	 * @param hostName
	 * @return
	 */
	private String getServerBasePath(String hostName){
		String serverBasePath = ""; 
		ES_SERVER_INFOSchema tES_SERVER_INFOSchema = this.queryES_SERVER_INFO(hostName);
		if(tES_SERVER_INFOSchema!=null){
			serverBasePath = tES_SERVER_INFOSchema.getServerBasePath();
		}else{
			serverBasePath = "";
		}
		return serverBasePath;
		
	}
	/**
	 * 查找服务器信息
	 * @param hostName
	 * @return
	 */
	private ES_SERVER_INFOSchema queryES_SERVER_INFO(String hostName){
		ES_SERVER_INFODB tES_SERVER_INFODB = new ES_SERVER_INFODB();
		tES_SERVER_INFODB.setHostName(hostName);
		if(tES_SERVER_INFODB.getInfo()){
			return tES_SERVER_INFODB.getSchema();
		}else{
			return null;
		}
		
	}
}
