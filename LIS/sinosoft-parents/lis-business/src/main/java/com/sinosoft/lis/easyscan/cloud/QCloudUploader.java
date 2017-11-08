package com.sinosoft.lis.easyscan.cloud;

/**
 * <p>表示一个腾讯云文件上载器。其调用了 qcloud 提供的API进行文件上传。</p>
 * @author Wang Zhang
 */
public class QCloudUploader extends CloudObjectStorageUploader {

	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(QCloudUploader.class);
	
	private static final int MAX_ATTEMPT_COUNT = 3;
	private final String friendlyCloudName = "腾讯 qcloud";
	public static String bucketName = "";
	
	@Override
	public String getFriendlyCloudName() {
		return friendlyCloudName;
	}
	
	@Override
	public void deleteFileFromCloud(String remotePath){
		this.deleteFileFromQCloud(remotePath);
	}
	
	/**
	 * 从腾讯云中删除文件
	 * @param remotePath 文件在腾讯云的路径
	 */
	private void deleteFileFromQCloud(String remotePath){
		if (remotePath == null || remotePath.length() == 0)
			return;
		com.tencent.qcloud.api.CosCloud cc = null;
		try {
			cc = CloudObjectStorageFactory.getQCloudInstance();
			if (cc == null) {
				throw new Exception("Failed to get the qcloud instance.");
			}
			if(bucketName == null || bucketName.length() == 0)
 		    	throw new Exception("The bucketName must neither be null nor empty. Make sure that you called CloudObjectStorageFactory.getQCloudInstance, before calling this method.");
			cc.deleteFile(bucketName, remotePath);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 上传文件至腾讯云
	 * @param localFilename 本地文件完整路径
	 * @param remotePath 远端文件路径，例如 "/xerox/EasyScan/2016/05/06/861100/F10011975.tif"
	 * @return 上传的结果。当其中的 code 属性值为 0 时表示成功
	 */
	private com.tencent.qcloud.api.UploadResult uploadFileToQCloud(String localFilename, String remotePath) {
		if (localFilename == null || localFilename.length() == 0)
			return null;
		if (remotePath == null || remotePath.length() == 0)
			return null;
		com.tencent.qcloud.api.UploadResult result = null;
		com.tencent.qcloud.api.CosCloud cc = null;
		try {
			cc = CloudObjectStorageFactory.getQCloudInstance();
			if (cc == null) {
				throw new Exception("Failed to get the qcloud instance.");
			}
			if(bucketName == null || bucketName.length() == 0)
 		    	throw new Exception("The bucketName must neither be null nor empty. Make sure that you called CloudObjectStorageFactory.getQCloudInstance, before calling this method.");
			String resultJsonStr = cc.uploadFile(bucketName, remotePath, localFilename);
			result = com.tencent.qcloud.api.ResultJsonParser.getUploadResultFromJsonString(resultJsonStr);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 上传文件至腾讯云。根据不同的传输结果自动进行重试
	 * @param localFilename 本地文件完整路径
	 * @param remotePath 远端文件路径，例如 "/xerox/EasyScan/2016/05/06/861100/F10011975.tif"
	 * @param attemptCount 已尝试次数
	 * @return 是否成功。若成功返回true, 否则返回false
	 */
	private boolean uploadFileToQCloud(String localFilename, String remotePath, int attemptCount){
		if(attemptCount >= MAX_ATTEMPT_COUNT)
    		return false;
    	com.tencent.qcloud.api.UploadResult uploadResult = null;
    	int resultCode = 0;
    	try{
    		uploadResult = uploadFileToQCloud(localFilename, remotePath);
    		if(uploadResult == null){
			    throw new Exception("The method uploadFileToQCloud terminated unexpectedly");
		    }
	        if(uploadResult.getCode() == null){
	    	    throw new Exception("An invalid result code was caught.");
	        }
	        resultCode = uploadResult.getCode();
	        switch(resultCode){
                case com.tencent.qcloud.api.ResultCode.Succeeded:
        	        logger.info("已成功上传文件" + localFilename + "至腾讯云，云端路径: " + remotePath);
        	        return true;
                case com.tencent.qcloud.api.ResultCode.IdenticalFileExists:
                	logger.info("已在腾讯云云端路径: \"" + remotePath + "\" 发现完全相同的文件。将按上传成功处理");
                	return true;
                case com.tencent.qcloud.api.ResultCode.Sha1NotMatch:
                	return uploadFileToQCloud(localFilename, remotePath, attemptCount + 1);
                case com.tencent.qcloud.api.ResultCode.UnknownHostException:
                	return uploadFileToQCloud(localFilename, remotePath, attemptCount + 1);
                case com.tencent.qcloud.api.ResultCode.FileAlreadyExists:
                	this.deleteFileFromQCloud(remotePath);
                	return uploadFileToQCloud(localFilename, remotePath, attemptCount + 1);
    	        default:
    		        throw new Exception(uploadResult.getMessage());
	        }
    	}catch(Exception e){
            logger.error(e.getMessage());
            return false;
    	}
	}
	
	@Override
	public boolean uploadFileToCloud(String localFilename, String remotePath) {
		// TODO Auto-generated method stub
		boolean result = false;
		try{
 		    if(localFilename == null || localFilename.length() == 0)
 			    throw new Exception("The localFilename must neither be null nor empty.");
 		    if(remotePath == null || remotePath.length() == 0)
 			    throw new Exception("The remotePath must neither be null nor empty."); 		    
 		    java.io.File file = new java.io.File(localFilename);
 		    if(!file.exists()){
 			    throw new Exception("The localFilename does not Exist");
 		    }
		    result = this.uploadFileToQCloud(localFilename, remotePath, 0);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return result;
	}

}
