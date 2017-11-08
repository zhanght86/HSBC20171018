package com.sinosoft.lis.pubfun.diskimport;

import java.io.*;
import java.util.*;
import com.f1j.ss.BookModelImpl;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;
import org.jdom.*;
import com.f1j.util.*;

/**
 * <p>Title: 磁盘导入</p>
 * <p>Description: 从xls文件中导入数据</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author QiuYang
 * @version 1.0
 */

public class XlsImporter
{
    /** 要导入的文件名 */
	private String fileName = null;

    /** 表头配置文件名 */
    private String configFileName = null;

    /** 表头配置xml文件中的子结点名 */
    private String[] sheetNames = null;

    /** 存放导入的数据，分sheet存放 */
    private List sheetList = new ArrayList();

    /**
     * 构造函数
     * @param fileName String
     * @param configFileName String
     * @param sheetNames String[]
     */
    public XlsImporter(String fileName, String configFileName, String[] sheetNames)
    {
        this.fileName = fileName;
        this.configFileName = configFileName;
        this.sheetNames = sheetNames;
    }

    /**
     * 导入xls文件
     * @return boolean
     */
    public boolean doImport()
    {
    	try
    	{
			BookModelImpl book = new BookModelImpl();
			book.read(fileName, new com.f1j.ss.ReadParams());
			if (sheetNames.length > book.getNumSheets())
			{
				return false;
			}
			for (int i = 0; i < sheetNames.length; i++)
			{
                List headList = getHeadList(sheetNames[i]);
                if (headList == null)
                {
                    return false;
                }
				sheetList.add(new Sheet(book, i, headList));
			}
		}
    	catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
    	catch (F1Exception e)
		{
			e.printStackTrace();
			return false;
		}
    	return true;
    }

    /**
     * 返回导入结果
     * @return Sheet[]
     */
    public Sheet[] getSheets()
    {
    	return (Sheet[]) sheetList.toArray(new Sheet[0]);
    }

    /**
     * 得到表头列表
     * @param sheetName String
     * @return List
     */
    private List getHeadList(String sheetName)
    {
    	List headList = new ArrayList();
//        try
//        {
//            DOMBuilder builder = new DOMBuilder();
////            Document document = builder.build(new FileInputStream(configFileName));
////            Element rootElement = document.getRootElement();
////            Element configElement = rootElement.getChild(sheetName);
////            List columenElementList = configElement.getChildren();
////            for (Iterator iter = columenElementList.iterator(); iter.hasNext();)
////            {
////            	Element columnElement = (Element)iter.next();
////            	headList.add(columnElement.getText());
////            }
//        }
//        catch (FileNotFoundException ex)
//        {
//            ex.printStackTrace();
//            return null;
//        }
//        catch (JDOMException ex)
//        {
//            ex.printStackTrace();
//            return null;
//        }
        return headList;
    }
}
