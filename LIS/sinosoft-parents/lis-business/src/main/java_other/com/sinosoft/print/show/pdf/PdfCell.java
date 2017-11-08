package com.sinosoft.print.show.pdf;

import com.itextpdf.text.pdf.*;
import com.itextpdf.text.*;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * ClassName: PdfCell
 * </p>
 * <p>
 * Description: 定制单元格的属性
 * </p>
 * @author ZhuXF
 * @version 2.0
 * @modify 2011-8-1
 * @depict 人生五十載，去事恍如夢幻，普天之下，豈有長生不滅者！
 */
public class PdfCell extends PdfPCell
{
	/**
	 * pdf单元格设置for图片
	 * @param cImage 图像对象
	 * @param cColSpan 单元格所占列
	 * @throws BadElementException
	 */
	public PdfCell(Image cImage, int cColSpan) throws BadElementException
	{
		super(cImage, true);
		setHorizontalAlignment(Element.ALIGN_CENTER);// 横向位置
		super.setBorderWidth(0); // 表格线的控制
		setColspan(cColSpan);
	}

	/**
	 * pdf单元格设置for文字
	 * @param cText 文字内容
	 * @param cRowSpan 单元格所占行
	 * @param cColSpan 单元格所占列
	 * @throws BadElementException
	 */
	public PdfCell(String cText, int cRowSpan, int cColSpan) throws BadElementException
	{
		super(new Paragraph(cText, PdfFont.createChineseFont(10, "000")));
		setVerticalAlignment(Element.ALIGN_TOP); // 纵向位置？设置无效
		setHorizontalAlignment(Element.ALIGN_CENTER);// 横向位置
		super.setBorderWidth(1); // 表格线的控制
		setRowspan(cRowSpan);
		setColspan(cColSpan);
	}

	/**
	 * pdf单元格设置for文字
	 * @param cText 文字内容
	 * @param cRowSpan 单元格所占行
	 * @param cColSpan 单元格所占列
	 * @param cCellDescArr 单元格属性集
	 * @throws BadElementException
	 */
	public PdfCell(String cText, int cRowSpan, int cColSpan, String[] cCellDescArr) throws BadElementException
	{
		super(new Paragraph(cText, PdfFont.createChineseFont(Integer.valueOf(cCellDescArr[5]), cCellDescArr[7])));
		// super.setNoWrap(true);
		super.setUseBorderPadding(true);

		super.setHorizontalAlignment(Integer.valueOf(cCellDescArr[3]));// 文字位置，横向位置
		super.setVerticalAlignment(Element.ALIGN_MIDDLE); // 纵向位置？设置无效

		super.setBorderWidth(Rectangle.NO_BORDER);
		super.setMinimumHeight(Float.valueOf(cCellDescArr[13]));
		
		if("false".equalsIgnoreCase(cCellDescArr[12]))
			super.setNoWrap(true);	//不自动换行	
		
		// 表格线设置：0(0000) - 无；1(1111) - 有；0101 - 左无上有右无下有
		String tBorderStyle = cCellDescArr[2];
		if ("0".equals(tBorderStyle) || "0000".equals(tBorderStyle))
		{
			tBorderStyle = "0000";
			super.setBorderWidth(0); // 表格线的控制
		}
		else if ("1".equals(tBorderStyle) || "1111".equals(tBorderStyle))
		{
			tBorderStyle = "1111";
			super.setBorderWidth((float) 0.1); // 表格线的控制
		}
		else
		{
			if (tBorderStyle.length() == 4)
			{
				if (!tBorderStyle.substring(0, 1).equals("0"))
				{
					// 左边框
					super.setBorderWidthLeft((float) 0.1);
				}
				if (!tBorderStyle.substring(1, 2).equals("0"))
				{
					// 上边框
					super.setBorderWidthTop((float) 0.1);
				}
				if (!tBorderStyle.substring(2, 3).equals("0"))
				{
					// 右边框
					super.setBorderWidthRight((float) 0.1);
				}
				if (!tBorderStyle.substring(3, 4).equals("0"))
				{
					// 下边框
					super.setBorderWidthBottom((float) 0.1);
				}
			}
			else
			{
				// 如果设置出现异常，则默认无表格线
				super.setBorderWidth(0); // 表格线的控制
			}
		}
		
		// 如果是虚线、双线的边框则需要特殊处理，线需要自己画，呵呵
		if (cCellDescArr[11].indexOf("hair") >= 0 || cCellDescArr[11].indexOf("double") >= 0)
		{
			PdfCellEvent tPdfCellEvent = new PdfCellEvent();
			tPdfCellEvent.setLineType(cCellDescArr[11]);
			tPdfCellEvent.setBorderStyle(tBorderStyle);
			super.setBorder(Rectangle.NO_BORDER);
			super.setCellEvent(tPdfCellEvent);
		}

		// super.setBorderWidth(1);
		super.setRowspan(cRowSpan);
		super.setColspan(cColSpan);
		// super.setHeader(false);
	}

	/**
	 * pdf单元格设置for文字
	 * @param cText 文字内容
	 * @param cRowSpan 单元格所占行
	 * @param cColSpan 单元格所占列
	 * @param cBorderStyle 单元格边框设置
	 * @param cFontSize 文字大小
	 * @param cFontStyle 文字样式（加粗、斜体）
	 * @param cFontPosition 文字位置
	 * @throws BadElementException
	 */
	@SuppressWarnings("unused")
	private PdfCell(String cText, int cRowSpan, int cColSpan, String cBorderStyle, int cFontSize, String cFontStyle,
			int cFontPosition) throws BadElementException
	{
		super(new Paragraph(cText, PdfFont.createChineseFont(cFontSize, cFontStyle)));
		// super.setNoWrap(true);
		super.setUseBorderPadding(true);

		super.setHorizontalAlignment(cFontPosition);// 横向位置
		super.setVerticalAlignment(Element.ALIGN_MIDDLE); // 纵向位置？设置无效

		super.setBorderWidth(Rectangle.NO_BORDER);
		// 表格线设置：0(0000) - 无；1(1111) - 有；0101 - 左无上有右无下有
		if ("0".equals(cBorderStyle) || "0000".equals(cBorderStyle))
		{
			super.setBorderWidth(0); // 表格线的控制
		}
		else if ("1".equals(cBorderStyle) || "1111".equals(cBorderStyle))
		{
			super.setBorderWidth((float) 0.1); // 表格线的控制
		}
		else
		{
			if (cBorderStyle.length() == 4)
			{
				if (!cBorderStyle.substring(0, 1).equals("0"))
				{
					// 左边框
					super.setBorderWidthLeft((float) 0.1);
				}
				if (!cBorderStyle.substring(1, 2).equals("0"))
				{
					// 上边框
					super.setBorderWidthTop((float) 0.1);
				}
				if (!cBorderStyle.substring(2, 3).equals("0"))
				{
					// 右边框
					super.setBorderWidthRight((float) 0.1);
				}
				if (!cBorderStyle.substring(3, 4).equals("0"))
				{
					// 下边框
					super.setBorderWidthBottom((float) 0.1);
				}
			}
			else
			{
				// 如果设置出现异常，则默认无表格线
				super.setBorderWidth(0); // 表格线的控制
			}
		}
		// super.setBorderWidth(1);
		super.setRowspan(cRowSpan);
		super.setColspan(cColSpan);
		// super.setHeader(false);
	}
}
