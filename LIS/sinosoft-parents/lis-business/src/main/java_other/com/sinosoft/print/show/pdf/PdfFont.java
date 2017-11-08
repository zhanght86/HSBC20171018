package com.sinosoft.print.show.pdf;
import org.apache.log4j.Logger;

import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * ClassName: PdfFont
 * </p>
 * <p>
 * Description: 定义pdf输出的字符集，一种是使用系统自带字库，另一种是引入iTextAsian.jar文件
 * </p>
 * @author ZhuXF
 * @version 2.0
 * @modify 2011-8-1
 * @depict 人生五十載，去事恍如夢幻，普天之下，豈有長生不滅者！
 */
public class PdfFont
{
private static Logger logger = Logger.getLogger(PdfFont.class);


	private static Font chineseFont;

	/**
	 * 创建字体样式
	 * @param cFontSize
	 * @param cFontStyle
	 * @return
	 */
	public final static Font createChineseFont(int cFontSize, String cFontStyle)
	{
		try
		{
			chineseFont = new Font(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));
			chineseFont.setSize(cFontSize);
			
			// Font.BOLD 粗体
			// Font.BOLDITALIC 粗体斜体
			// Font.ITALIC 斜体
			// Font.STRIKETHRU 中间线
			// Font.UNDERLINE 下划线，超级难看
			if ("000".equals(cFontStyle))
			{
				chineseFont.setStyle(Font.NORMAL);	// 正常字体
			}
			else
			{
				if (cFontStyle.length() == 3)
				{
					if (cFontStyle.substring(0, 1).equals("1") && cFontStyle.substring(1, 2).equals("1"))
					{
						chineseFont.setStyle(Font.BOLDITALIC);	// 粗斜体
					}
					else if (cFontStyle.substring(0, 1).equals("1") && cFontStyle.substring(1, 2).equals("0"))
					{
						chineseFont.setStyle(Font.BOLD);	// 粗体
					}
					else if (cFontStyle.substring(0, 1).equals("0") && cFontStyle.substring(1, 2).equals("1"))
					{
						chineseFont.setStyle(Font.ITALIC);	// 斜体
					}
					else
					{
						chineseFont.setStyle(Font.NORMAL);	// 正常字体
					}
					// 尽量不考虑，太难看了……
					if (cFontStyle.substring(2, 3).equals("1"))
					{
						chineseFont.setStyle(Font.UNDERLINE); // 下划线
					}
				}
				else
				{
					// 如果设置出现异常，默认字体
					chineseFont.setStyle(Font.NORMAL);	// 正常字体
				}

			}
			chineseFont.setColor(BaseColor.BLACK);
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return chineseFont;
	}
}
