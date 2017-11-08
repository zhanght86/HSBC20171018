package com.sinosoft.print.show.pdf;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * ClassName: PdfDataFormat
 * </p>
 * <p>
 * Description: 描述PDF打印中一些数据的公共格式化方法
 * </p>
 * @author ZhuXF
 * @version 1.0
 * @modify 2011-7-29
 * @depict 人生五十載，去事恍如夢幻，普天之下，豈有長生不滅者！
 */
public class PdfDataFormat
{
private static Logger logger = Logger.getLogger(PdfDataFormat.class);

	/**
	 * 日志输出类
	 */
	private Logger mLogger = Logger.getLogger(PdfDataFormat.class);
	
	/**
	 * 格式化数据主入口，可扩展
	 * @param cPattern
	 * @param cData
	 * @return
	 */
	public String formatData(String cPattern, String cData)
	{
		// 格式采用配置处理，例如：num=1，date=2
		String[] tStrArr = cPattern.split("=");
		String tStrValue = "";
		// 判断格式类型
		if ("num".equals(tStrArr[0]))
		{
			tStrValue = formatNumber(tStrArr[1], cData);
		}
		else
		{
			tStrValue = formatDate(tStrArr[1], cData);
		}
		return tStrValue;
	}

	/**
	 * 根据指定格式，格式日期型数据
	 * @param cPattern
	 * @param cDateData
	 * @return
	 */
	private String formatDate(String cPattern, String cDateData)
	{
		String tPattern = "EEE, MMM d, yyyy";
		// 可根据实际项目情况，配置格式类型
		if("1".equals(cPattern))
		{
			tPattern = "EEE, MMM d, yyyy";
		}
		// 使用new DateFormatSymbols(Locale.getDefault())设置区域
		SimpleDateFormat tSimpleDateFormat = new SimpleDateFormat(tPattern, new DateFormatSymbols(Locale.getDefault()));
		String tString;
		try
		{
			SimpleDateFormat tSimpleDateFormatIn = new SimpleDateFormat("yyyy-MM-dd");
			tString = tSimpleDateFormat.format(tSimpleDateFormatIn.parse(cDateData));
		}
		catch (Exception e)
		{
			tString = cDateData;
			mLogger.error("PdfDataFormat - formatDate - 格式日期数据异常" + cPattern + " - " + cDateData);
//			e.printStackTrace();
		}
		return tString;
	}

	/**
	 * 根据指定格式，格式数值型数据
	 * @param cPattern
	 * @param cNumData
	 * @return
	 */
	private String formatNumber(String cPattern, String cNumData)
	{
		String tPattern = "0.00";
		// 可根据实际项目情况，配置格式类型
		if("1".equals(cPattern))
		{
			tPattern = "0.00";
		}
		
		DecimalFormat tDecimalFormat = new DecimalFormat(tPattern);
		String tString;
		try
		{
			tString = tDecimalFormat.format(Double.parseDouble(cNumData));
		}
		catch (NumberFormatException e)
		{
			tString = cNumData;
			mLogger.error("PdfDataFormat - formatNumber - 格式数字数据异常" + cPattern + " - " + cNumData);
			// e.printStackTrace();
		}
		return tString;
	}

	public static void main(String[] args)
	{
		DateFormat fmt = new SimpleDateFormat("EEE,d MMM yyyy hh:mm:ss z", new DateFormatSymbols(Locale.US));
		try
		{
			logger.debug(fmt.format(new Date()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		logger.debug(Locale.getDefault());
		PdfDataFormat tPdfDataFormat = new PdfDataFormat();
		logger.debug(tPdfDataFormat.formatData("0.00", "-1112131.1161"));
		logger.debug(tPdfDataFormat.formatData("EEE, MMM d, yyyy", "2011-04-21"));
	}
}
