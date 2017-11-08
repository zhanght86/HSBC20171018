package com.sinosoft.lis.easyscan.cloud;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import com.sinosoft.lis.easyscan.PathHelper;

/**
 * <p>表示一个腾讯云文件下载器。其调用了 qcloud 提供的API进行文件下载。</p>
 * @author <span>Wang Zhang</span>
 *
 */
public class QCloudDownloader extends CloudObjectStorageDownloader {

	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(QCloudDownloader.class);
	private final String friendlyCloudName = "腾讯 qcloud";
	private static String accessChannelSqlString = "SELECT SysVarValue FROM LdSysVar WHERE SysVar = \'QCloudAccessChannel\'";
	private String accessChannel;
	
	public QCloudDownloader(){
		this.accessChannel = this.queryAccessChannel();
	}
	
	private String queryAccessChannel(){
		String result = null;
		try{
		    com.sinosoft.utility.ExeSQL eSql = new com.sinosoft.utility.ExeSQL();
		    result = eSql.getOneValue(accessChannelSqlString);
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		if(result == null || result.isEmpty()){
			return "file";
		} else {
			return result;
		}
	}
	
	/**
	 * 自动为文件路径创建目录
	 * @param localFileName 包含文件名的完整的本地文件路径
	 */
	private void autoMakeDirectories(String localFileName){
		java.io.File file = new java.io.File(localFileName);
		String parentDirStr = file.getParent();
		if(file.exists()){
			return;
		}
		if(!file.isAbsolute()){
			return;
		}
		if(parentDirStr == null){
			return;
		}
		java.io.File parentDir = new java.io.File(parentDirStr);
		try{
			parentDir.mkdirs();
		}catch(Exception e){}
	}
	/**
	 * 在所给路径创建新文件，若文件已存在，则删除后，创建新文件.
	 * @param localFilename 要创建的文件的本地路径.
	 * @return 一个表示所创建文件的 java.io.File 实例.
	 * @throws IOException 当删除已存在文件或创建新文件时可能出现的异常
	 * @throws java.lang.SecurityException 当用户权限不足时可能出现的异常
	 */
	private java.io.File createNewFile(String localFilename) throws IOException, java.lang.SecurityException{
		java.io.File file = new java.io.File(localFilename);
		
		if(file.exists())
		{
            if(!file.delete()){
			    throw new java.io.IOException("File: \"" + localFilename + "\" cannot be deleted.");
			}
		}
		autoMakeDirectories(localFilename);
		if(!file.createNewFile()){
			throw new java.io.IOException("File: \"" + localFilename + "\" cannot be created.");
		}
		return file;
	}
	
	/**
	 * 根据给出的 remotePath 拼接出文件下载地址.
	 * @param remotePath 云端的相对路径 例如  "xerox/EasyScan/2016/05/11/861100/F10012577.tif".
	 * @param cc 腾讯 qcloud 实体，其提供了文件的上传、验证、签名生成等功能和服务。可通过调用 CloudObjectStorageFactory.getQCloudInstance方法获得. 
	 * @return 云端的下载路径<br/> 例如 "<a style="word-break:keep-all;white-space:nowrap" href="http://easyscan-10015661.file.myqcloud.com/xerox/EasyScan/2016/05/11/861100/F10012577.tif">http://easyscan-10015661.file.myqcloud.com/xerox/EasyScan/2016/05/11/861100/F10012577.tif</a>"
	 */
	private String getDownloadUrl(String remotePath, com.tencent.qcloud.api.CosCloud cc){
		if(cc == null)
			throw new java.lang.NullPointerException("The cc cannot be null.");
		return "http://" + cc.getDefaultBucketName() + "-" + 
		           cc.getAppId() + "." + this.accessChannel.trim() + ".myqcloud.com/" + PathHelper.autoTrimPath(remotePath, false, true, '/') ;
	}
	
	@Override
	public FileInputStream downloadFileFromCloud(String remotePath, String localFilename) {
		// TODO Auto-generated method stub
		com.tencent.qcloud.api.CosCloud cc = com.sinosoft.lis.easyscan.cloud.CloudObjectStorageFactory.getQCloudInstance();
		String urlStr = this.getDownloadUrl(remotePath, cc);
		logger.info("Download url:" + urlStr);
		FileInputStream result = null;
		java.net.HttpURLConnection getFileRequest = null;
		java.io.File file = null;
		try{
			file = createNewFile(localFilename);
		    java.net.URL url = new java.net.URL(urlStr);
		    getFileRequest = (java.net.HttpURLConnection)url.openConnection();
		    getFileRequest.setRequestProperty("User-Agent",  "Sinosoft Lis Server");
		    getFileRequest.setRequestMethod("GET");
		    java.io.InputStream feedbackStream = getFileRequest.getInputStream();
		    java.io.OutputStream outputFileStream = new java.io.FileOutputStream(file);
		    byte[] bytes = new byte[1048576];
		    int readSize = 0;
		    while((readSize = feedbackStream.read(bytes, 0, bytes.length)) > 0){
		    	outputFileStream.write(bytes, 0, readSize);
		    }
		    outputFileStream.flush();
		    outputFileStream.close();
		    feedbackStream.close();
		    result = new java.io.FileInputStream(file);
		}catch(Exception e){
			logger.error(e.getMessage());
		}finally{
			if(getFileRequest != null)
			{
				try{
					getFileRequest.disconnect();
				}catch(Exception e1){
					
				}
			}
		}
		return result;
	}

	@Override
	public ByteArrayInputStream downloadFileFromCloud(String remotePath) {
		// TODO Auto-generated method stub
		com.tencent.qcloud.api.CosCloud cc = com.sinosoft.lis.easyscan.cloud.CloudObjectStorageFactory.getQCloudInstance();
		String urlStr = this.getDownloadUrl(remotePath, cc);
		logger.info("Download url:" + urlStr);
		ByteArrayInputStream result = null;
		java.net.HttpURLConnection getFileRequest = null;
		try{
		    java.net.URL url = new java.net.URL(urlStr);
		    getFileRequest = (java.net.HttpURLConnection)url.openConnection();
		    getFileRequest.setRequestProperty("User-Agent",  "Sinosoft Lis Server");
		    getFileRequest.setRequestMethod("GET");
		    java.io.InputStream feedbackStream = getFileRequest.getInputStream();
		    java.io.OutputStream buffer = new java.io.ByteArrayOutputStream();
		    byte[] bytes = new byte[1048576];
		    int readSize = 0;
		    while((readSize = feedbackStream.read(bytes, 0, bytes.length)) > 0){
		    	buffer.write(bytes, 0, readSize);
		    }
		    buffer.flush();
		    byte[] resultBytes =  ((java.io.ByteArrayOutputStream)buffer).toByteArray();
		    buffer.close();
		    feedbackStream.close();
		    result = new java.io.ByteArrayInputStream(resultBytes);
		}catch(Exception e){
			logger.error(e.getMessage());
		}finally{
			if(getFileRequest != null)
			{
				try{
					getFileRequest.disconnect();
				}catch(Exception e1){
					
				}
			}
		}
		return result;
	}

	@Override
	public String getFriendlyCloudName() {
		// TODO Auto-generated method stub
		return this.friendlyCloudName;
	}
	

}
