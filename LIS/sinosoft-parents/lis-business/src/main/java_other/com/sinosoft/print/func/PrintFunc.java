package com.sinosoft.print.func;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPrtDefinitionDB;
import com.sinosoft.lis.db.LPrtPrintLogDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LPrtTempleteLogSchema;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * ClassName: PrintFunc
 * </p>
 * <p>
 * Description: 打印引擎公共方法处理类
 * </p>
 * @author ZhuXF
 * @version 1.0
 * @modify 2011-1-26
 * @depict 人生五十載，去事恍如夢幻，普天之下，豈有長生不滅者！
 */
public class PrintFunc
{
private static Logger logger = Logger.getLogger(PrintFunc.class);

	public PrintFunc()
	{}

	/**
	 * 按指定格式输出日期型字段
	 * @return
	 */
	public static String getFormatDate()
	{
		String date = DateFormat.getDateTimeInstance().format(new Date());
		logger.debug(date);
		return date;
	}

	/**
	 * 按指定格式输出数值型字段
	 * @param args
	 */
	public static String getFormatNum()
	{
		double t = 0d;
		return String.valueOf(t);
	}

	/**
	 * 获取临时文件名
	 * @param cGI
	 * @return
	 */
	public static String getTempFileName(GlobalInput cGI)
	{
		return cGI.Operator + "_" + PrintQueue.getFileName();
	}
	
	/**
	 * 获取CSV文件名
	 * @param cGI
	 * @return
	 */
	public static String getCsvFileName(String tTemplateName)
	{
		StringBuffer aFileName = new StringBuffer();
		aFileName.append(tTemplateName).append("_");
		aFileName.append(PubFun.getCurrentDate()).append("_");
		aFileName.append(PubFun.getCurrentTime().replace(":", "_"));
		return aFileName.toString();
	}
	
	/**
	 * 将inputStream转换成byte数组
	 * @param cInputStream
	 * @return
	 */
	public static byte[] transformInputstream(InputStream cInputStream)
	{
		byte[] tbyte = null;
		try
		{
			BufferedInputStream bins = new BufferedInputStream(cInputStream);
			tbyte = new byte[bins.available()];
			bins.read(tbyte);
			bins.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return tbyte;
	}

	/**
	 * 将输入流写入文件
	 * @param cInputStream
	 */
	public static void writeXmlToFile(InputStream cInputStream)
	{
		OutputStream tOutputStream = null;
		try
		{
			tOutputStream = new FileOutputStream("c:/xx.xml");
			while (cInputStream.available() > 0)
			{
				int data = cInputStream.read();
				tOutputStream.write(data);
			}
			cInputStream.close();
			tOutputStream.close();
		}
		catch (Exception e)
		{
			if (cInputStream != null)
			{
				try
				{
					cInputStream.close();
				}
				catch (IOException ioe)
				{
					ioe.printStackTrace();
				}
			}
			if (tOutputStream != null)
			{
				try
				{
					tOutputStream.close();
				}
				catch (IOException ioe)
				{
					ioe.printStackTrace();
				}
			}
			e.printStackTrace();
		}
	}

	/**
	 * 获取打印请求所需的模板信息
	 * @return
	 */
	public static String getTemplete()
	{
		return "";
	}

	/**
	 * 返回打印模板路径，通过print.properties文件配置
	 * @return
	 */
	public static String getTempletePath()
	{
		PrintFunc tPrintFunc = new PrintFunc();
		InputStream inputStream = tPrintFunc.getClass().getClassLoader().getResourceAsStream("print.properties");
		Properties p = new Properties();
		try
		{
			p.load(inputStream);
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		logger.debug("templetepath:" + p.getProperty("templetepath"));
		return p.getProperty("templetepath");
	}

	/**
	 * 返回打印的输出类型
	 * @param cPrintName
	 * @param cLanguage
	 * @return
	 */
	public static String getOutputType(String cPrintName, String cLanguage)
	{
		StringBuffer tSBSql = new StringBuffer(128);
		tSBSql.append("select a.outputtype from lprttemplete a ,lprtrelated b ,lprtdefinition c where c.printname = '");
		tSBSql.append("?cPrintName?");
		tSBSql.append("' and b.language = '");
		tSBSql.append("?cLanguage?");
		tSBSql.append("' and a.templeteid = b.templeteid and b.printid = c.printid");
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSBSql.toString());
		sqlbv.put("cPrintName", cPrintName);
		sqlbv.put("cLanguage", cLanguage);

		ExeSQL tExeSQL = new ExeSQL();
		String tFilePath = tExeSQL.getOneValue(sqlbv);

		return tFilePath;
	}

	/**
	 * 返回打印定义的信息，包含：语言、输出类型
	 * @param cXmlFunc
	 * @return
	 */
	public static String[] getPrintInfo(XmlFunc cXmlFunc,String cType)
	{
		// 局部变量准备：打印名称、用户语言、系统语言、输出类型
		String tPrintName = cXmlFunc.getPrintName();
		String tUserLanguage = cXmlFunc.getUserLanguage();
		String tSysLanguage = cXmlFunc.getSysLanguage();
		String tOutputType = cXmlFunc.getOutputType();
		return getPrintInfo(tPrintName, tUserLanguage, tSysLanguage, tOutputType, cType);
	}

	/**根据打印名称获得该打印使用的语言类型
	 * @param tPrintName
	 * @return 0 代表用户语言，1 代表系统语言
	 */
	public static String getLaguageType(String tPrintName){
		LPrtDefinitionDB tLPrtDefinitionDB = new LPrtDefinitionDB();
		tLPrtDefinitionDB.setPrintName(tPrintName);
		String tLanguageType = tLPrtDefinitionDB.query(1,1).get(1).getLanguageType();//查询使用用户语言还是系统语言	
		return tLanguageType;
	}
	
	private static String[] getPrintInfo(String tPrintName, String tUserLanguage, String tSysLanguage, String tOutputType, String cType)
	{
		String[] tPrintInfo = new String[6];
//		 查询打印的语言选择
		String tLanguageType = getLaguageType(tPrintName);//查询使用用户语言还是系统语言	
		StringBuffer tSBSql = new StringBuffer(128);
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();



		tSBSql.append("select a.templetetype,b.templeteid,b.printid ,a.state,a.outputtype from lprttemplete a ,lprtrelated b ,lprtdefinition c where c.printname = '");
		tSBSql.append("?tPrintName?");
		sqlbv1.put("tPrintName", tPrintName);
		if ("0".equals(tLanguageType))
		{
			tPrintInfo[0] = tUserLanguage;
			// 0 - 使用用户语言
			if(!"".equals(tUserLanguage))
			tSBSql.append("' and b.language = '").append("?tUserLanguage?");
			sqlbv1.put("tUserLanguage", tUserLanguage);
		}
		else 
		{
			tPrintInfo[0] = tSysLanguage;
			// 1 - 使用系统语言
			if(!"".equals(tSysLanguage))
			tSBSql.append("' and b.language = '").append("?tSysLanguage?");
			sqlbv1.put("tSysLanguage", tSysLanguage);
		}
		if ("defaultType".equals(StrTool.cTrim(tOutputType)))
		{
			// 如果输出类型为空，查询默认的打印信息
			tSBSql.append("' and a.defaulttype = 'Y");
		}
		else if(!"".equals(StrTool.cTrim(tOutputType)))
		{
			// 如果输出类型不为空，以传入输出类型查询打印信息。传入的输出类型可能是错误的！
			tSBSql.append("' and b.outputtype = '");
			tSBSql.append("?tOutputType?");
			sqlbv1.put("tOutputType", tOutputType);
		}

		tSBSql.append("' and a.templeteid = b.templeteid and b.printid = c.printid");
		sqlbv1.sql(tSBSql.toString());
		SSRS tSSRS = tExeSQL.execSQL(sqlbv1);
		if (tSSRS.getMaxRow() > 0)
		{
			for (int i = 1; i <= 5; i++)
			{
				tPrintInfo[i] = tSSRS.GetText(1, i);
			}
			//如果测试标志不为空
			if(!cType.equals(""))
			{
				// 如果状态不是发布或测试，则把OutputType（输出类型）设为"a"
				if (!tPrintInfo[4].equals("1")&&!cType.equals("2"))
				{
					tPrintInfo[1] = "a";
				}
			}
			else
			{
				// 如果状态不是发布，则把OutputType（输出类型）设为"a"
				if (!tPrintInfo[4].equals("1"))
				{
					tPrintInfo[1] = "a";
				}
			}
		}
		else if(!"".equals(StrTool.cTrim(tOutputType)))
		{//指定类型没有找到，查找默认类型
			tPrintInfo = getPrintInfo(tPrintName, tUserLanguage, tSysLanguage, "defaultType", cType);
		}
		else
		{
			for (int i = 1; i <= 5; i++)
			{
				tPrintInfo[i] = "";
			}
		}
		return tPrintInfo;
	}
	/**
	 * 根据传入的信息查询模板文件的物理路径
	 * @param cPrintName
	 * @param cOutputType
	 * @param cLanguage
	 * @return
	 */
	public static String getTempleteFile(String cPrintName, String cOutputType, String cLanguage)
	{
		StringBuffer tSBSql = new StringBuffer(128);
		tSBSql.append("select a.filepath from lprttemplete a ,lprtrelated b ,lprtdefinition c where c.printname = '");
		tSBSql.append("?cPrintName?");
		tSBSql.append("' and b.outputtype = '");
		tSBSql.append("?cOutputType?");
		tSBSql.append("' and b.language = '");
		tSBSql.append("?cLanguage?");
		tSBSql.append("' and a.templeteid = b.templeteid and b.printid = c.printid");
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tSBSql.toString());
		sqlbv2.put("cPrintName", cPrintName);
		sqlbv2.put("cOutputType", cOutputType);
		sqlbv2.put("cLanguage", cLanguage);

		ExeSQL tExeSQL = new ExeSQL();
		String tFilePath = tExeSQL.getOneValue(sqlbv2);

		return tFilePath;
	}

	/**
	 * 生成模板操作轨迹日志
	 * @param cOperate
	 * @param cPrintID
	 * @param cTempleteID
	 * @param cBusinessType
	 * @param cOperator
	 * @return
	 */
	public static LPrtTempleteLogSchema getTemLog(String cOperate, String cPrintID, String cTempleteID,
			String cBusinessType, String cOperator)
	{
		String tSerialNo = PubFun1.CreateMaxNo("SerialNo", 6);
		LPrtTempleteLogSchema tLPrtTempleteLogSchema = new LPrtTempleteLogSchema();
		String tCurrentDate = PubFun.getCurrentDate();
		String tCurrentTime = PubFun.getCurrentTime();
		tLPrtTempleteLogSchema.setSerialNo(tSerialNo);
		tLPrtTempleteLogSchema.setPrintID(cPrintID);
		tLPrtTempleteLogSchema.setTempleteID(cTempleteID);
		tLPrtTempleteLogSchema.setOperateDate(tCurrentDate);
		tLPrtTempleteLogSchema.setOperateTime(tCurrentTime);
		tLPrtTempleteLogSchema.setOperateType(cOperate);
		tLPrtTempleteLogSchema.setBusinessType(cBusinessType);
		tLPrtTempleteLogSchema.setOperator(cOperator);
		return tLPrtTempleteLogSchema;
	}

	/**
	 * 生成打印操作轨迹日志
	 * @param cTransferData
	 * @param cState
	 * @param cRemark
	 * @param cOperator
	 * @return
	 */
	public static boolean addPrintLog(TransferData cTransferData, String cState, String cRemark, String cOperator)
	{
		// TransferData容器中有：语言、输出类型、模板ID、打印ID
		String tPrintId = (String) cTransferData.getValueByName("PrintID");
		String tTempleteID = (String) cTransferData.getValueByName("TempleteID");

		String tSerialNo = PubFun1.CreateMaxNo("SerialNo", 6);

		LPrtPrintLogDB tLPrtPrintLogDB = new LPrtPrintLogDB();
		String tCurrentDate = PubFun.getCurrentDate();
		String tCurrentTime = PubFun.getCurrentTime();
		tLPrtPrintLogDB.setSerialNo(tSerialNo);
		tLPrtPrintLogDB.setPrintID(tPrintId);
		tLPrtPrintLogDB.setTempleteID(tTempleteID);
		tLPrtPrintLogDB.setOperator(cOperator);
		tLPrtPrintLogDB.setState(cState); // 0 - 成功； 1 - 失败
		// tLPrtPrintLogSchema.setOutPut(""); // 暂时无用
		tLPrtPrintLogDB.setRemark(cRemark);// 错误信息
		tLPrtPrintLogDB.setPrintDate(tCurrentDate);
		tLPrtPrintLogDB.setPrintTime(tCurrentTime);
		if (!tLPrtPrintLogDB.insert())
		{
			return false;
		}
		return true;
	}

	/**
	 * 校验标签合法性
	 * @param cTempleteID
	 * @return
	 */
	public static String  CheckTag(String cTempleteID)
	{
		ExeSQL tExe = new ExeSQL();
		String tEorTag="";
		String tSql = "select distinct b.Description  from Lprtcode a ,Lprtxmlstyle b where  b.Templeteid = '?cTempleteID?'  and codetype = 'xmltags' and b.Description not in (a.Code)";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(tSql);
		sqlbv3.put("cTempleteID", cTempleteID);
		SSRS tResult = tExe.execSQL(sqlbv3);
		if (tResult != null && tResult.getMaxRow() > 0)
		{
			for(int i = 1 ;i<= tResult.getMaxRow();i++)
			{
				tEorTag = tResult.GetText(i, 1)+ tEorTag;
			}
		}
		return tEorTag;
	}

	public static void main(String[] args)
	{
		CheckTag("000019");
	}
}
