package com.sinosoft.print.show.pdf;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

/**
 * <p>
 * Title: Web涓氬姟绯荤粺
 * </p>
 * <p>
 * ClassName: PdfCellEvent
 * </p>
 * <p>
 * Description: 鍗曞厓鏍艰竟妗嗙壒娈婂鐞嗭紝瀹炵幇铏氱嚎銆佸弻瀹炵嚎杈规鍔熻兘
 * </p>
 * @author ZhuXF
 * @version 1.0
 * @modify 2011-9-21
 * @depict 浜虹敓浜斿崄杓夛紝鍘讳簨鎭嶅澶㈠够锛屾櫘澶╀箣涓嬶紝璞堟湁闀风敓涓嶆粎鑰咃紒
 */
public class PdfCellEvent implements PdfPCellEvent
{
	/**
	 * 杈规鏍峰紡锛屼緥濡傦細瀹炵幇銆佽櫄绾裤�鍙屽疄绾跨瓑

	 */
	public String mLineType = "none,none,none,none";
	
	
	public String mBorderStyle = "1111";

	/**
	 * 鎸囧畾杈规鏍峰紡
	 * @param cLineType
	 */
	public void setLineType(String cLineType)
	{
		this.mLineType = cLineType;
	}
	
	/**
	 * 鎸囧畾杈规鐘舵�

	 * @param cBorderStyle
	 */
	public void setBorderStyle(String cBorderStyle)
	{
		this.mBorderStyle = cBorderStyle;
	}

	/**
	 * 鐗规畩鍗曞厓鏍艰緭鍑轰簨浠�

	 * @param PdfPCell 鍗曞厓鏍煎璞�

	 * @param Rectangle 鍗曞厓鏍煎璞″潗鏍囩瓑淇℃伅
	 * @param PdfContentByte[]	鍗曞厓鏍兼牱寮忎俊鎭�

	 */
	public void cellLayout(PdfPCell cPdfPCell, Rectangle cRectangle, PdfContentByte[] cPdfContentByte)
	{
		PdfContentByte tPdfContentByte = cPdfContentByte[PdfPTable.LINECANVAS];
		tPdfContentByte.saveState();

		tPdfContentByte.setLineWidth(0.5f);

		// 鎷嗗垎杈规鏍峰紡淇℃伅锛屽彲鎵╁睍
//		String[] tLineTypeArr = mLineType.split(",");
		
		// 鏄惁鍙岀嚎鐨勬爣蹇楋紝鏀寔鍙岃櫄绾跨殑澶勭悊
		boolean tFlag = false;

		// 榛樿璁剧疆涓哄疄绾胯緭鍑�

		tPdfContentByte.setLineDash(new float[]
		{ 2.0f, 0.0f }, 0);
		if (mLineType.indexOf("hair") >= 0)
		{
			// 铏氱嚎璁剧疆锛岀涓�釜鍙傛暟鏄櫄绾跨殑闀垮害锛岀浜屼釜鍙傛暟鏄櫄绾块棿闅旂殑闀垮害

			tPdfContentByte.setLineDash(new float[]
			{ 0.3f, 2.0f }, 0);
		}
		else if (mLineType.indexOf("double") >= 0)
		{
			// 鍙屽疄绾�

			tFlag = true;
		}

		if (tFlag)
		{
			if (!mBorderStyle.substring(0, 1).equals("0"))
			{
				// 宸﹁竟妗�

				tPdfContentByte.moveTo(cRectangle.getLeft(), cRectangle.getTop() + 2);
				tPdfContentByte.lineTo(cRectangle.getLeft(), cRectangle.getBottom() - 2);

				tPdfContentByte.moveTo(cRectangle.getLeft() + 2, cRectangle.getTop() + 2);
				tPdfContentByte.lineTo(cRectangle.getLeft() + 2, cRectangle.getBottom() - 2);
			}
			if (!mBorderStyle.substring(1, 2).equals("0"))
			{
				// 涓婅竟妗�

				tPdfContentByte.moveTo(cRectangle.getLeft(), cRectangle.getTop());
				tPdfContentByte.lineTo(cRectangle.getRight() + 2, cRectangle.getTop());

				tPdfContentByte.moveTo(cRectangle.getLeft() , cRectangle.getTop() + 2);
				tPdfContentByte.lineTo(cRectangle.getRight() + 2, cRectangle.getTop() + 2);
			}
			if (!mBorderStyle.substring(2, 3).equals("0"))
			{
				// 鍙宠竟妗�

				tPdfContentByte.moveTo(cRectangle.getRight(), cRectangle.getTop() + 2);
				tPdfContentByte.lineTo(cRectangle.getRight(), cRectangle.getBottom() - 2);

				tPdfContentByte.moveTo(cRectangle.getRight() + 2, cRectangle.getTop() + 2);
				tPdfContentByte.lineTo(cRectangle.getRight() + 2, cRectangle.getBottom() - 2);
			}
			if (!mBorderStyle.substring(3, 4).equals("0"))
			{
				// 涓嬭竟妗�

				tPdfContentByte.moveTo(cRectangle.getLeft(), cRectangle.getBottom());
				tPdfContentByte.lineTo(cRectangle.getRight() + 2, cRectangle.getBottom());

				tPdfContentByte.moveTo(cRectangle.getLeft() , cRectangle.getBottom() - 2);
				tPdfContentByte.lineTo(cRectangle.getRight() + 2, cRectangle.getBottom() - 2);
			}
		}
		else
		{
			if (!mBorderStyle.substring(0, 1).equals("0"))
			{
				// 宸﹁竟妗�

				tPdfContentByte.moveTo(cRectangle.getLeft(), cRectangle.getTop());
				tPdfContentByte.lineTo(cRectangle.getLeft(), cRectangle.getBottom());
			}
			if (!mBorderStyle.substring(1, 2).equals("0"))
			{
				// 涓婅竟妗�

				tPdfContentByte.moveTo(cRectangle.getLeft(), cRectangle.getTop());
				tPdfContentByte.lineTo(cRectangle.getRight(), cRectangle.getTop());
			}
			if (!mBorderStyle.substring(2, 3).equals("0"))
			{
				// 鍙宠竟妗�

				tPdfContentByte.moveTo(cRectangle.getRight(), cRectangle.getTop());
				tPdfContentByte.lineTo(cRectangle.getRight(), cRectangle.getBottom());
			}
			if (!mBorderStyle.substring(3, 4).equals("0"))
			{
				// 涓嬭竟妗�

				tPdfContentByte.moveTo(cRectangle.getLeft(), cRectangle.getBottom());
				tPdfContentByte.lineTo(cRectangle.getRight(), cRectangle.getBottom());
			}
		}

		tPdfContentByte.stroke();
		tPdfContentByte.restoreState();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException, DocumentException
	{
		// TODO 鑷嫊鐢熸垚銇曘倢銇熴儭銈姐儍銉夈兓銈广偪銉�

		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("c:/output.pdf"));
		document.open();
		PdfCellEvent border = new PdfCellEvent();
		PdfPTable table = new PdfPTable(3);
		PdfPCell cell;
		for (int i = 1; i <= 6; i++)
		{
			cell = new PdfPCell(new Phrase("test"));
			cell.setBorder(Rectangle.NO_BORDER);
			border.mLineType = "1";
			cell.setCellEvent(border);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
		}

		border = new PdfCellEvent();
		for (int i = 1; i <= 6; i++)
		{
			cell = new PdfPCell(new Phrase("test"));
			cell.setBorder(Rectangle.NO_BORDER);
			border.mLineType = "2";
			cell.setCellEvent(border);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
		}
		document.add(table);
		document.close();
	}

}