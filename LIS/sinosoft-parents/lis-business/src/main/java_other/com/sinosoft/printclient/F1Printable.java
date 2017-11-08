package com.sinosoft.printclient;
import org.apache.log4j.Logger;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import com.sinosoft.report.f1report.BarCode;
import com.sinosoft.report.f1report.DBarCode;

/**
 * @author Hunger
 */
public class F1Printable implements Printable
{
private static Logger logger = Logger.getLogger(F1Printable.class);

	private final static int WidthRate = 78;

	private final static int HeightRate = 71;

	private Printable mPrintable = null;

	private String[] mBarCode;

	private String[] mBarPara;

	private String[] mLocations;

	private String[] mBarType;

	public void setBarCode(String[] aBarPara, String[] alocations, String[] aBarCode, String[] aBarType)
	{
		mLocations = alocations;
		mBarPara = aBarPara;
		mBarCode = aBarCode;
		mBarType = aBarType;
	}

//	public void setBarCode(String[] aBarPara, String[] alocations, String[] aBarCode)
//	{
//		mLocations = alocations;
//		mBarPara = aBarPara;
//		mBarCode = aBarCode;
//		mBarType = null;
//	}

	public F1Printable(Printable aPrintable)
	{
		mPrintable = aPrintable;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.print.Printable#print(java.awt.Graphics,
	 * java.awt.print.PageFormat, int)
	 */
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException
	{
		try
		{
			drawBarCode(graphics, pageIndex);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return mPrintable.print(graphics, pageFormat, pageIndex);
	}

	private boolean isNumeric(String sVar)
	{
		boolean b = true;
		try
		{
			Integer.parseInt(sVar);
		}
		catch (NumberFormatException e)
		{
			b = false;
		}
		return b;
	}

	private boolean drawBarCode(Graphics graphics, int pageIndex)
	{
		String sParam = null;
		if (mBarCode == null)
			return true;

		// 按顺序绘制条形码
		for (int k = 0; k < mBarCode.length; k++)
		{
			// 按类型输出一维或者二维条码
			if (mBarType != null && mBarType[k].equals("DBarCode"))
			{

				DBarCode tDBarCode = new DBarCode(mBarCode[k]);
				String[] pos = mLocations[k].split(",");
				graphics.drawImage(tDBarCode.getImage(), (int) Double.parseDouble(pos[1]), (int) (Double.parseDouble(pos[2])), (int) (tDBarCode.getImage().getWidth()
						* WidthRate / 100.0), (int) (tDBarCode.getImage().getHeight() * HeightRate / 100.0), null);
				// 绘制条形码外框
				graphics.drawRect((int) Double.parseDouble(pos[1]), (int) (Double.parseDouble(pos[2])), (int) (tDBarCode.getImage().getWidth()
						* WidthRate / 100.0), (int) (tDBarCode.getImage().getHeight() * HeightRate / 100.0));
			}
			else
			{
				BarCode sCode = new BarCode(mBarCode[k]);
				sParam = mBarPara[k];
				// 获得条形码参数
				if (sCode != null && sParam != null && !sParam.equalsIgnoreCase(""))
				{
					String[] pos = mLocations[k].split(",");
					if (pageIndex == Integer.parseInt(pos[0]))
					{
						String t_Str = "";
						// 获得条形码参数
						if (sParam != null && !sParam.equalsIgnoreCase(""))
						{

							String params[] = sParam.split("&");
							for (int j = 0; j < params.length; j++)
							{
								// 获得条形码高度
								if (params[j].toLowerCase().startsWith("barheight"))
								{
									t_Str = params[j].split("=")[1];
									if (isNumeric(t_Str))
									{
										sCode.setBarHeight(Integer.parseInt(t_Str));
									}
								}
								// 获得条形码宽度
								if (params[j].toLowerCase().startsWith("barwidth"))
								{
									t_Str = params[j].split("=")[1];
									if (isNumeric(t_Str))
									{
										sCode.setBarWidth(Integer.parseInt(t_Str));
									}
								}
								// 获得条形码粗细线条比例
								if (params[j].toLowerCase().startsWith("barratio"))
								{
									t_Str = params[j].split("=")[1];
									if (isNumeric(t_Str))
									{
										sCode.setBarRatio(Integer.parseInt(t_Str));
									}
								}
								// 获得条形码图片背景色
								if (params[j].toLowerCase().startsWith("bgcolor"))
								{
									t_Str = params[j].split("=")[1];
									sCode.setBgColor(t_Str);
								}
								// 获得条形码颜色
								if (params[j].toLowerCase().startsWith("forecolor"))
								{
									t_Str = params[j].split("=")[1];
									sCode.setForeColor(t_Str);
								}
								// 获得条形码图片横向空白区长度
								if (params[j].toLowerCase().startsWith("xmargin"))
								{
									t_Str = params[j].split("=")[1];
									if (isNumeric(t_Str))
									{
										sCode.setXMargin(Integer.parseInt(t_Str));
									}
								}
								// 获得条形码图片竖向空白区长度
								if (params[j].toLowerCase().startsWith("ymargin"))
								{
									t_Str = params[j].split("=")[1];
									if (isNumeric(t_Str))
									{
										sCode.setYMargin(Integer.parseInt(t_Str));
									}
								}
							}
						}
						sCode.setFormatType(BarCode.FORMAT_JPEG);
						// 绘制条形码
						graphics.drawImage(sCode.getImage(), (int) Double.parseDouble(pos[1]), (int) (Double.parseDouble(pos[2])), (int) (sCode.getImage().getWidth()
								* WidthRate / 100.0), (int) (sCode.getImage().getHeight() * HeightRate / 100.0), null);
						// 绘制条形码外框
						graphics.drawRect((int) Double.parseDouble(pos[1]), (int) (Double.parseDouble(pos[2])), (int) (sCode.getImage().getWidth()
								* WidthRate / 100.0), (int) (sCode.getImage().getHeight() * HeightRate / 100.0));

					}
				}
			}
		}
		return true;
	}

}
