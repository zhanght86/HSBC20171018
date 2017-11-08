package com.sinosoft.lis.pubfun.diskimport;

import java.io.File;
import com.sinosoft.utility.CErrors;

/**
 * <p>Title: 磁盘导入抽象类</p>
 * <p>Description: 从磁盘导入数据</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author QiuYang
 * @version 1.0
 * update encoding
 * 
 */

public abstract class DiskImporter
{
    public CErrors mErrors = new CErrors();

    
    /** 要导入的文件名 */
    private String fileName = null;

    /** 表头配置文件名 */
    private String configFileName = null;

    /** 表头配置xml文件中的子结点名 */
    private String[] sheetNames = null;

    /**
     * 构造函数
     * @param fileName String
     * @param configFileName String
     * @param sheetNames String[]
     */
    public DiskImporter(String fileName, String configFileName, String[] sheetNames)
	{
		this.fileName = fileName;
		this.configFileName = configFileName;
		this.sheetNames = sheetNames;
	}

    /**
     * 构造函数
     * @param fileName String
     * @param configFileName String
     * @param sheetName String
     */
    public DiskImporter(String fileName, String configFileName, String sheetName)
	{
		this.fileName = fileName;
		this.configFileName = configFileName;
		this.sheetNames = new String[1];
		sheetNames[0] = sheetName;
	}

    /**
     * 执行磁盘导入
     * @return boolean
     */
    public boolean doImport()
	{
		XlsImporter importer = new XlsImporter(fileName, configFileName, sheetNames);
    	importer.doImport();
    	Sheet[] sheets = importer.getSheets();
    	if (!dealSheets(sheets))
    	{
    		return false;
    	}
//        deleteFile();
    	return true;
	}

    /**
     * 删除磁盘导入临时文件
     * @return boolean
     */
    private boolean deleteFile()
    {
        File file = new File(fileName);
        return file.delete();
    }

    /**
     * 处理所有导入的sheet
     * @param sheets Sheet[]
     * @return boolean
     */
    abstract boolean dealSheets(Sheet[] sheets);
}
