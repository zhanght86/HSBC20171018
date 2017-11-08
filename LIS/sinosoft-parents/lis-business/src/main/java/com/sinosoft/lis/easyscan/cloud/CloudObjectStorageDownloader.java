package com.sinosoft.lis.easyscan.cloud;

import com.sinosoft.lis.easyscan.PathHelper;

/** 
 * <p>一个抽象类，提供了方法用于将从云端获取文件。</p> 
 * @author <span>Wang Zhang</span>
 * 
 */
public abstract class CloudObjectStorageDownloader{
	
	/**
	 * 根据给出的 folderPath 和 filename 拼接出云端的文件路径
	 * @param folderPath 例如 xerox/EasyScan/2016/05/05/861100/
	 * @param filename 例如 F10012540.gif
	 * @return 云端的文件路径 例如 xerox/EasyScan/2016/05/05/861100/F10012540.gif
	 */
	public String getCloudFilePath(String folderPath, String filename){
		return PathHelper.autoTrimPath(folderPath, false, false, '/') + PathHelper.autoTrimPath(filename, false, true, '/');
	}
	
	/**
	 * 从云端下载文件到本地。
	 * @param remotePath 云端的文件路径
	 * @param localFilename 本地的文件保存路径，若文件已存在则会被覆盖
	 * @return 一个表示本地文件的数据流的 java.io.FileInputStream 
	 */
	public abstract java.io.FileInputStream downloadFileFromCloud(String remotePath, String localFilename);
	
	/**
	 * 从云端下载文件到内存中。下载过大的文件可能会导致内存溢出
	 * @param remotePath 云端的文件路径
	 * @return 一个表示内存数据流的 java.io.ByteArrayInputStream 
	 */
	public abstract java.io.ByteArrayInputStream downloadFileFromCloud(String remotePath);
	
	/**
     * 获取此云端的通用名称
     * @return 云端的通用名称，如 腾讯 qcloud, 阿里云 等
     */
    public abstract String getFriendlyCloudName();
}
