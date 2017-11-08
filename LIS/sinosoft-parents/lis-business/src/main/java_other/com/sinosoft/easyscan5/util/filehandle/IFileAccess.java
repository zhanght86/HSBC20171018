package com.sinosoft.easyscan5.util.filehandle;

import java.io.InputStream;
import java.util.HashMap;


public interface IFileAccess {

	/**
	 * 保存文件
	 * @param fileOutputStream
	 * @param fileInfo
	 * @return
	 */
	public ResultInfo saveFile(InputStream inputStream,FileInfo fileInfo,HashMap propMap);
	/**
	 * 查询文件
	 * @param filePath
	 * @param itemID
	 * @return
	 */
	public ResultInfo findFile(String filePath,String itemID);
	
	/**
	 * 设置保存设置
	 * @param saveType
	 */
	public void setSaveType(String saveType);
}
