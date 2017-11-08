package com.sinosoft.print.show.pdf;
import org.apache.log4j.Logger;

import jxl.format.PaperSize;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * ClassName: PdfPageSize
 * </p>
 * <p>
 * Description: 匹配pdf打印模板的纸张类型
 * </p>
 * @author ZhuXF
 * @version 2.0
 * @modify 2011-8-1
 * @depict 人生五十載，去事恍如夢幻，普天之下，豈有長生不滅者！
 */
public class PdfPageSize
{
private static Logger logger = Logger.getLogger(PdfPageSize.class);

	/**
	 * 根据传入参数，返回纸张的类型，待扩展
	 * @param cPaperSize
	 * @return Rectangle
	 */
	public Rectangle getPageSize(PaperSize cPaperSize)
	{
		// 默认纸张类型为A4
		Rectangle tRectangle = PageSize.A4;
		if (PaperSize.A4.equals(cPaperSize))
		{
			// A4
			tRectangle = PageSize.A4;
		}
		else if (PaperSize.A3.equals(cPaperSize))
		{
			// A3
			tRectangle = PageSize.A3;
		}
		else if (PaperSize.B5.equals(cPaperSize))
		{
			// B5
			tRectangle = PageSize.B5;
		}
		return tRectangle;
	}
}
