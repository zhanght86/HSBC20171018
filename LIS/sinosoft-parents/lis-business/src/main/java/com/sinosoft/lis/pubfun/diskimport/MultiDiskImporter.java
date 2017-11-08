package com.sinosoft.lis.pubfun.diskimport;

/**
 * <p>Title: 磁盘导入抽象类</p>
 * <p>Description: 用于处理导入多个sheet，每个sheet处理方式类似的情况</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author QiuYang
 * @version 1.0
 */

public abstract class MultiDiskImporter extends DiskImporter
{
    /**
     * 构造函数
     * @param fileName String
     * @param configFileName String
     * @param sheetNames String[]
     */
    public MultiDiskImporter(String fileName, String configFileName,
            String[] sheetNames)
    {
        super(fileName, configFileName, sheetNames);
    }

    /**
     * 构造函数
     * @param fileName String
     * @param configFileName String
     * @param sheetName String
     */
    public MultiDiskImporter(String fileName, String configFileName,
            String sheetName)
    {
        super(fileName, configFileName, sheetName);
    }

    /**
     * 处理所有导入的sheet
     * @param sheets Sheet[]
     * @return boolean
     */
    protected boolean dealSheets(Sheet[] sheets)
    {
        for (int i = 0; i < sheets.length; i++)
        {
            if (!dealOneSheet(sheets[i]))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 处理多个sheet中的一个
     * @param sheet Sheet
     * @return boolean
     */
    abstract protected boolean dealOneSheet(Sheet sheet);
}
