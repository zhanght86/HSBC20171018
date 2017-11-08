package com.sinosoft.print.show.pdf;
import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * ClassName: PdfPageEvent
 * </p>
 * <p>
 * Description: 定制pdf文件的页脚信息
 * </p>
 * @author ZhuXF
 * @version 2.0
 * @modify 2011-8-1
 * @depict 人生五十載，去事恍如夢幻，普天之下，豈有長生不滅者！
 */
public class PdfPageEvent extends PdfPageEventHelper
{
private static Logger logger = Logger.getLogger(PdfPageEvent.class);


	private PdfTemplate mPdfTemplate;

	private BaseFont mBaseFont;
	
	/**
	 * 打开doc对象时操作
	 */
	public void onOpenDocument(PdfWriter cPdfWriter, Document cDocument)
	{
		try
		{
			mPdfTemplate = cPdfWriter.getDirectContent().createTemplate(100, 100);
			mBaseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		}
		catch (Exception e)
		{
			throw new ExceptionConverter(e);
		}
	}

	/**
	 * 页面结束时操作，增加页码信息
	 */
	public void onEndPage(PdfWriter cPdfWriter, Document cDocument)
	{
		// 页面的大小
		Rectangle tRectangle = cPdfWriter.getPageSize();
		
		// 在每页结束的时候把“第x页”信息写道模版指定位置
		String tFooterText = " - " + cPdfWriter.getCurrentPageNumber() + " - ";

		PdfContentByte tPdfContentByte = cPdfWriter.getDirectContent();
		tPdfContentByte.beginText();
		tPdfContentByte.setFontAndSize(mBaseFont, 8);
		tPdfContentByte.setTextMatrix(tRectangle.getWidth()/2 - 16, 16);
		tPdfContentByte.showText(tFooterText);
		tPdfContentByte.endText();

		tPdfContentByte.addTemplate(mPdfTemplate, tRectangle.getWidth()/2 + 16, 16);
	}
	
	/**
	 * doc对象关闭时操作， 可用来增加总页数信息
	 */
	public void onCloseDocument(PdfWriter cPdfWriter, Document cDocument)
	{
		// 关闭document的时候获取总页数，并把总页数按模版写道之前预留的位置
//		System.err.println("xxxxxxxxxxxxxxx");
//		mPdfTemplate.beginText();
//		mPdfTemplate.setFontAndSize(mBaseFont, 8);
//		mPdfTemplate.showText(Integer.toString(cPdfWriter.getPageNumber() - 1) + "页");
//		mPdfTemplate.endText();
//		mPdfTemplate.closePath();// sanityCheck();
	}
}
