package com.sinosoft.lis.pubfun.diskimport;

import java.util.List;
import com.f1j.ss.BookModelImpl;
import com.f1j.util.F1Exception;

/**
 * <p>Title: excel文件的工作簿类</p>
 * <p>Description: 封装了BookModelImpl，对应excel的工作簿</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author QiuYang
 * 
 * @version 1.0
 */

public class Sheet
{
    /** 一个book对应一个excel文件 */
	private BookModelImpl book = null;

    /** sheet编号 */
	private int sheetId = 0;

    /** 表头列表 */
	private List headList = null;

    /**
     * 构造函数
     * @param book BookModelImpl
     * @param sheetId int
     * @param headList List
     */
    public Sheet(BookModelImpl book, int sheetId, List headList)
	{
		this.book = book;
		this.sheetId = sheetId;
		this.headList = headList;
	}

    /**
     * 得到sheet的名称
     * @return String
     */
    public String getName()
	{
		try
		{
			return book.getSheetName(sheetId);
		}
		catch (F1Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}

    /**
     * 根据列数得到相应的表头
     * @param col int
     * @return String
     */
    public String getHead(int col)
	{
		return (String) headList.get(col);
	}

    /**
     * 根据行数和列数得到sheet的数据
     * @param row int
     * @param col int
     * @return String
     */
    public String getText(int row, int col)
	{
		try
		{
			return book.getText(sheetId, row + 1, col);
		}
		catch (F1Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}

    /**
     * 根据行数和列名得到sheet的数据
     * @param row int
     * @param colName String
     * @return String
     */
    public String getText(int row, String colName)
	{
		try
		{
			for (int col = 0; col < headList.size(); col++)
			{
				String head = (String) headList.get(col);
				if (head.equalsIgnoreCase(colName))
				{
					return book.getText(sheetId, row + 1, col);
				}
			}
		}
		catch (F1Exception e) {
			e.printStackTrace();
		}
		return "";
	}

    /**
     * 得到总行数
     * @return int
     */
    public int getRowSize()
    {
    	try
    	{
			for (int i = 0; i < book.getLastRow(); i++)
			{
				String id = book.getText(i, 0);
				if ((id == null) || (id.equals("")))
				{
					return (i - 1);
				}
			}
            return book.getLastRow();
		}
    	catch (F1Exception e)
		{
			e.printStackTrace();
            return 0;
		}
    }

    /**
     * 得到总列数
     * @return int
     */
    public int getColSize()
	{
		return headList.size();
	}
}
