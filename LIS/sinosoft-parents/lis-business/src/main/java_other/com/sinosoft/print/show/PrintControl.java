package com.sinosoft.print.show;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPrtCodeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.vschema.LPrtCodeSet;
import com.sinosoft.print.func.PrintFunc;
import com.sinosoft.print.func.XmlFunc;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * ClassName: PrintControl
 * </p>
 * <p>
 * Description: 打印引擎的入口类
 * </p>
 * @author ZhuXF
 * @version 1.0
 * @modify 2011-1-24
 * @depict 人生五十載，去事恍如夢幻，普天之下，豈有長生不滅者！
 */
public class PrintControl implements BusinessService
{
	/**
	 * 输入流解析类
	 */
	private XmlFunc mXmlFunc;

	public CErrors mErrors = new CErrors();

	/**
	 * 日志输出类
	 */
	private Logger mLogger = Logger.getLogger(PrintControl.class);

	private VData mResult = new VData();
	
	private String mStr = "";

	/**
	 * 缓存输入流的byte数组
	 */
	private byte[] mbyte;

	/**
	 * 缺省的构造函数
	 */
	public PrintControl()
	{}

	/**
	 * 测试函数
	 * @param args
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception
	{
		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "001";

		File tFile = new File("c:/printtest/2.xml");
		FileInputStream tFis = new FileInputStream(tFile);

		Object tObj = tFis;
		InputStream is = (InputStream) tObj;

		VData tVData = new VData();
		tVData.add(is);

		PrintControl tPrintControl = new PrintControl();
		tPrintControl.submitData(tVData, "");

	}

	@SuppressWarnings("unchecked")
	public boolean submitData(VData cVData, String cOperater)
	{
		if (!getInputData(cVData))
		{
			return false;
		}

		// 先缓存输入流信息，可恶的inputstream，读取过一次后用dom4j解析就会出现问题。
		/**
		 * Problem: Input stream is opened and read bytes from it, <br>
		 * passed the same to document builder to parse method. <br>
		 * so it caused the exception saying premature end of file. <br>
		 * Solution: Pass fresh input stream which is opened <br>
		 * and not read anything (bytes) before passing to parse<br>
		 * method of DocumentBuilder object.
		 */

		mLogger.debug("解析输入流的xml");
		mXmlFunc = new XmlFunc(new ByteArrayInputStream(mbyte),true);

		mLogger.debug("获取xml中的打印类型");

		// 准备传回前台的数据
		String[] tPrintInfo = PrintFunc.getPrintInfo(mXmlFunc,mStr);
		// 返回的信息包含：语言、输出类型、模板ID、打印ID；注意顺序！！
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("Language", tPrintInfo[0]);
		tTransferData.setNameAndValue("OutputType", tPrintInfo[1]);	// 实际上是templetetype
		tTransferData.setNameAndValue("TempleteID", tPrintInfo[2]);
		tTransferData.setNameAndValue("PrintID", tPrintInfo[3]);
		String tOutputType = tPrintInfo[5];
		tTransferData.setNameAndValue("Output", tOutputType);// 真实输出类型

		StringBuffer tSBSql = new StringBuffer(128);
		StringBuffer tSBParamSql = new StringBuffer(128);
		StringBuffer tSBPositionSql = new StringBuffer(128);
		StringBuffer tSBCodeSql = new StringBuffer(128);
		StringBuffer tSBTypeSql = new StringBuffer(128);
		// 模板存在才进行下面的条形码信息查询
		if (!tPrintInfo[2].equals(""))
		{
			LPrtCodeDB tLPrtCodeDB = new LPrtCodeDB();
			tLPrtCodeDB.setTempleteID(tPrintInfo[2]);
			tLPrtCodeDB.setCodeType("barcode");
			LPrtCodeSet tLPrtCodeSet = tLPrtCodeDB.query();
			int tSize = tLPrtCodeSet.size();
			if (tPrintInfo[1].equals("0")&&tOutputType.equals("0"))
			{
				// vts条形码查询处理

				// 读取BarCode的Code信息
				String tBarCodePath = "/DATASET/BarCodeInfo/BarCode";

				mXmlFunc.query(tBarCodePath);
				int tCodeIndex = mXmlFunc.getColIndex("Code");
				int tNameIndex = mXmlFunc.getColIndex("Name");

				// 条形码生成逻辑判断
				String tCode = "";
				String tName = "";

				TransferData tBarCodeTransferData = new TransferData();
				while (mXmlFunc.next())
				{
					tName = mXmlFunc.getString(tNameIndex);
					tCode = mXmlFunc.getString(tCodeIndex);
					tBarCodeTransferData.setNameAndValue(tName, tCode);
				}

				if (tSize > 0)
				{
					for (int i = 1; i <= tSize; i++)
					{
						tSBParamSql.append("BarHeight=20&amp;BarRation=3&amp;BarWidth=1&amp;BgColor=FFFFFF&amp;ForeColor=000000&amp;XMargin=10&amp;YMargin=10#");
						tSBPositionSql.append(tLPrtCodeSet.get(i).getContent2() + "#");
						tCode = (String) tBarCodeTransferData.getValueByName(tLPrtCodeSet.get(i).getCode());
						tSBCodeSql.append(tCode + "#");
						tSBTypeSql.append(tLPrtCodeSet.get(i).getContent1() + "#");
					}
					tSBSql.append(tSBParamSql);
					tSBSql.append("!");
					tSBSql.append(tSBPositionSql);
					tSBSql.append("!");
					tSBSql.append(tSBCodeSql);
					tSBSql.append("!");
					tSBSql.append(tSBTypeSql);
				}
				// logger.debug(tSBSql.toString());
				tTransferData.setNameAndValue("BarCodeStr", tSBSql.toString());
			}
			else if (tPrintInfo[1].equals("1")&&tOutputType.equals("1"))
			{
				// pdf条形码查询处理
				if (tSize > 0)
				{
					for (int i = 1; i <= tSize; i++)
					{
						tTransferData.setNameAndValue("B" + tLPrtCodeSet.get(i).getCode(), tLPrtCodeSet.get(i).getContent2());
					}
				}
			}
		}
		mResult.add(tTransferData);
		mResult.add(new ByteArrayInputStream(mbyte));

		mLogger.debug("OutputType is " + tOutputType);

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		InputStream tInputStream = (InputStream) cInputData.get(0);
		mbyte = PrintFunc.transformInputstream(tInputStream);
		try{
			if(tInputStream!=null)
			{
				tInputStream.close();tInputStream =null;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		mStr = (String )cInputData.get(1);
		return true;
	}

	public VData getResult()
	{
		// TODO Auto-generated method stub
		return mResult;
	}

	public CErrors getErrors()
	{
		// TODO Auto-generated method stub
		return mErrors;
	}
}
