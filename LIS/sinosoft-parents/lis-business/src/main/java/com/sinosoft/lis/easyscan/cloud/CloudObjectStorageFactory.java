package com.sinosoft.lis.easyscan.cloud;

/**
 * <p>一个静态类，用于获取当前可用的云端存储的名字; 生成CloudObjectStorageUploader实例; </p>
 * @author Wang Zhang
 */
public class CloudObjectStorageFactory {
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(CloudObjectStorageFactory.class);
	
	// 腾讯 qcloud 实体，其提供了文件的上传、验证、签名生成等功能和服务。
	private static com.tencent.qcloud.api.CosCloud qCloud = null;
    private static final String sqlStringLdsysvar = "SELECT CONCAT( CONCAT( SYSVARVALUE,  ':'),  SYSVARTYPE) FROM Ldsysvar WHERE SYSVAR = 'CloudObjectStorage'";
    private static final String sqlStringQCloudAppId = "SELECT SYSVARVALUE FROM Ldsysvar WHERE SYSVAR = 'QCloudAppId'";
    private static final String sqlStringQCloudSecretId = "SELECT SYSVARVALUE FROM Ldsysvar WHERE SYSVAR = 'QCloudSecretId'";
    private static final String sqlStringQCloudSecretKey = "SELECT SYSVARVALUE FROM Ldsysvar WHERE SYSVAR = 'QCloudSecretKey'";
    private static final String sqlStringQCloudBucketName = "SELECT SYSVARVALUE FROM Ldsysvar WHERE SYSVAR = 'QCloudBucketName'";
    private static final String resultDelimiter = ":";
    
    /**
     * 获取当前可用的云端存储的名字
     * @return 当前可用的云端存储的名字。例如 腾讯云的名称为"QCd"或"qcloud"
     */
    public static String getCloudObjectStorageName(){
    	String result = null;
		com.sinosoft.utility.ExeSQL esql = new com.sinosoft.utility.ExeSQL();
        String sysvarvalueAndSysvartype = null;
        sysvarvalueAndSysvartype = esql.getOneValue(sqlStringLdsysvar);
        if(sysvarvalueAndSysvartype == null || sysvarvalueAndSysvartype.length() == 0 || sysvarvalueAndSysvartype.indexOf(resultDelimiter) < 0){
        	return null;
        }
        String[] temp = sysvarvalueAndSysvartype.split(resultDelimiter);
        String sysvarvalue = null;
        String sysvartype = null;
        for(int i = 0; i < temp.length; i++){
        	if(temp[i].equalsIgnoreCase("Enabled")){
        		sysvarvalue = new String(temp[i]);
        	}else if(temp[i].equalsIgnoreCase("Disabled")){
        		sysvarvalue = new String(temp[i]);
        		return null;
        	} else {
        		sysvartype = new String(temp[i]);
        	}
        }
        if(sysvarvalue == null || !sysvarvalue.equalsIgnoreCase("Enabled")){
        	if(sysvartype != null && !sysvartype.isEmpty()){
        		logger.debug("A disabled cloud service " + sysvartype + " was found.");
        	}
        	return null;
        }
        result = sysvartype;
        return result;
    }
    
    /**
     * 关闭 腾讯 CosCloud 实体的所有后台线程并销毁CosCloud 实体
     */
    public static void disposeQCloudInstance(){
    	synchronized(QCloudUploader.bucketName){
		    if(qCloud != null){
		    	qCloud.shutdown();
		    	qCloud = null;
		    	QCloudUploader.bucketName = "";
		    }
		}
    }
    /**
     * 获取 腾讯 CosCloud 实体, <br/>
     * 初次调用时会自动创建新的实体.
     * @return 腾讯 qcloud 实体，其提供了文件的上传、验证、签名生成等功能。
     */
    public static com.tencent.qcloud.api.CosCloud getQCloudInstance(){
    	synchronized(QCloudUploader.bucketName){
		    if(qCloud != null)
			    return qCloud;
		    com.sinosoft.utility.ExeSQL esql = new com.sinosoft.utility.ExeSQL();
		    String appId = null;
		    String secretId = null;
		    String secretKey = null;
		    QCloudUploader.bucketName = null;
		    try{
		    	appId = esql.getOneValue(sqlStringQCloudAppId);
		    	secretId = esql.getOneValue(sqlStringQCloudSecretId);
		    	secretKey = esql.getOneValue(sqlStringQCloudSecretKey);
		    	QCloudUploader.bucketName = esql.getOneValue(sqlStringQCloudBucketName);		    	
		    	qCloud = new  com.tencent.qcloud.api.CosCloud(Integer.parseInt(appId), secretId, secretKey, QCloudUploader.bucketName);			    
		    }catch(Exception e){
		    	logger.error(e.getMessage());
		    }
		    return qCloud;		    
		}
    }
    
    /**
     * <p>获取CloudObjectStorageUploader, 以上传文件至云端。将自动判断可用云端，然后生成并返回相应上载器. </p>
     * @return CloudObjectStorageUploader 实例。若没有找到可用云则返回 <span style="font-weight:bold;color:#7F0055">null</span>.
     */
    public static CloudObjectStorageUploader getCloudObjectStorageUploader(){
    	String cloudName = null;
    	CloudObjectStorageUploader cloudUploader = null;
 	    
 	    try{
 		    cloudName =	getCloudObjectStorageName();
 		    if(cloudName == null || cloudName.length() == 0)
 			    throw new Exception("No enabled cloud was found.");
 		
 		    // Determines which method needs to be called according to the cloud name
 		    if(cloudName.equals("QCd") || cloudName.equalsIgnoreCase("qcloud")){
 		    	cloudUploader = new QCloudUploader();
 		    }else{
 			    throw new Exception("No such cloud object storage called \"" + cloudName + "\" is available.");
 		    }
 		
 	    }catch(Exception e){
 		    logger.error(e.getMessage());
 	    }
 	    return cloudUploader;
    }
    
    /**
     * <p>获取CloudObjectStorageDownloader, 以从云端下载数据。将自动判断可用云端，然后生成并返回相应下载器. </p>
     * @return CloudObjectStorageDownloader 实例。若没有找到可用云则返回 <span style="font-weight:bold;color:#7F0055">null</span>.
     */
    public static CloudObjectStorageDownloader getCloudObjectStorageDownloader(){
    	String cloudName = null;
    	CloudObjectStorageDownloader cloudDownloader = null;
 	    
 	    try{
 		    cloudName =	getCloudObjectStorageName();
 		    if(cloudName == null || cloudName.length() == 0)
 			    throw new Exception("No enabled cloud was found.");
 		
 		    // Determines which method needs to be called according to the cloud name
 		    if(cloudName.equals("QCd") || cloudName.equalsIgnoreCase("qcloud")){
 		    	cloudDownloader = new QCloudDownloader();
 		    }else{
 			    throw new Exception("No such cloud object storage called \"" + cloudName + "\" is available.");
 		    }
 		
 	    }catch(Exception e){
 		    logger.error(e.getMessage());
 	    }
 	    return cloudDownloader;
    }
}
