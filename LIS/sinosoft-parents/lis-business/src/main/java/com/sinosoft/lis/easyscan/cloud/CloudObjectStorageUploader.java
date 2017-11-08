package com.sinosoft.lis.easyscan.cloud;

import com.sinosoft.lis.easyscan.PathHelper;

/**
 * <p>一个抽象类，提供了方法用于将文件上传至云端。</p>
 * @author Wang Zhang
 */
public abstract class CloudObjectStorageUploader {
	/**
	 * 获取云端上传路径
	 * @param pathPatterns pathPatterns 通过 com.sinosoft.lis.easyscan.PathHelper.getPathPatternsAndFilename方法获取的 pathPatterns
	 * @return 云端上传路径 例如 "/xerox/EasyScan/86/2005/10/11/F10012563.tif"
	 */
	public String getCloudFilePath(String[] pathPatterns){
		if(pathPatterns == null || pathPatterns.length <= 1)
        	return "";
		// 从UploadPrepare传过来的路径
		// Example: D:/lis_hx/ui/||xerox/EasyScan/||86/2005/10/11/
		String strPath = pathPatterns[0];
		String filename = pathPatterns[1];
		int intPos1 = strPath.indexOf("||");
		// Example: xerox/EasyScan/||86/2005/10/11/
		String strPath1 = strPath.substring(intPos1 + 2);
		int intPos2 = strPath1.indexOf("||");
		// Example: xerox/EasyScan/
		String strPicPath = strPath1.substring(0, intPos2);
		strPicPath = PathHelper.autoTrimPath(strPicPath, false, false, '/');
		// strSavePath="xerox/EasyScan/86/2005/10/11/";
		String strSavePath = strPicPath
				+ PathHelper.autoTrimPath(strPath1.substring(intPos2 + 2), false, false, '/');
		String cloudFilePath = "/" + strSavePath + filename;
		return cloudFilePath;
	}
	
	/**
	 * 获取云端上传路径
	 * @param picFilePath 包含文件名的路径 例如 "xerox/EasyScan/86/2005/10/11/F10012563.tif"
	 * @return 云端上传路径 例如 "/xerox/EasyScan/86/2005/10/11/F10012563.tif"
	 */
	public String getCloudFilePath(String picFilePath){
		if(picFilePath == null || picFilePath.length() == 0){
			return "";
		}
		return "/" + PathHelper.autoTrimPath(picFilePath, false, true, '/');
	}
	
	/**
	 * 从云端删除文件
	 * @param remotePath 云端路径 例如 "/xerox/EasyScan/86/2005/10/11/F10012563.tif"
	 */
	public abstract void deleteFileFromCloud(String remotePath);
	
	/**
	 * 上传文件至云端
	 * @param localFilename 本地文件完整路径
	 * @param remotePath 远端文件路径，例如 "/xerox/EasyScan/2016/05/06/861100/F10011975.tif"
	 * @return 上传的结果。当值为 true 时 表示成功， 当值为 false 时表示失败。
	 */
    public abstract boolean uploadFileToCloud(String localFilename,String remotePath);
    
    /**
     * 获取此云端的通用名称
     * @return 云端的通用名称，如 腾讯 qcloud, 阿里云 等
     */
    public abstract String getFriendlyCloudName();
}
