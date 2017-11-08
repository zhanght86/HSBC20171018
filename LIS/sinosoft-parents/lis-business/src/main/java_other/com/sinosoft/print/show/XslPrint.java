package com.sinosoft.print.show;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.print.func.PrintFunc;
import com.sinosoft.print.func.XmlFunc;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class XslPrint implements BusinessService
{

	/**
	 * 缓存输入流的byte数组
	 */
	private byte[] mbyte;

	/**
	 * 输入流解析类
	 */
	private XmlFunc mXmlFunc;

	/**
	 * 日志输出类
	 */
	private Logger mLogger = Logger.getLogger(XslPrint.class);

	/**
	 * 输出流
	 */
	@SuppressWarnings("unused")
	private ByteArrayOutputStream mOutputStream = new ByteArrayOutputStream();

	/**
	 * 临时文件名
	 */
	private String mTempFileName;

	private String mAppPath;

	private TransferData mTransferData;

	private GlobalInput mGlobalInput = new GlobalInput();

	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/**
	 * 缺省的构造函数
	 */
	public XslPrint()
	{}

	/**
	 * 解析传入流的xml信息
	 * @param cInputStream
	 * @return
	 */
	private boolean parserIS(InputStream cInputStream)
	{
		try
		{
			mLogger.debug("开始解析传入的xml数据。。。");
			mXmlFunc = new XmlFunc(cInputStream);
			mLogger.debug("传入的xml数据解析完毕。。。");
		}
		catch (Exception e)
		{
			mLogger.error("传入的xml信息异常！");
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean xmlTransform(InputStream cInputStream, String cAppPath)
	{
		// Have the XSLTProcessorFactory obtain a interface to a
		// new XSLTProcessor object.
		try
		{
			String tOutputType = (String) mTransferData.getValueByName("OutputType");
			String tLanguage = (String) mTransferData.getValueByName("Language");
			String tTemplateFile = cAppPath
					+ PrintFunc.getTempleteFile(mXmlFunc.getPrintName(), tOutputType, tLanguage);

			// tTemplateFile = "c:/printtest/1s.xsl";
			mLogger.debug("xslPath:" + tTemplateFile);
			// 通过流读取xml文件的内容
			String xmlContent = "";
			InputStreamReader fs = new InputStreamReader(cInputStream, "utf-8");
			int b = 0;
			while ((b = fs.read()) != -1)
			{
				xmlContent += String.valueOf((char) b);
			}
			InputStream xmlStream = new ByteArrayInputStream(xmlContent.getBytes("UTF-8"));
			InputStream xsltStream = new FileInputStream(tTemplateFile);
			Source xsltSource = new StreamSource(xsltStream);
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer trans = factory.newTransformer(xsltSource);

			StreamResult result = new StreamResult(new FileOutputStream(cAppPath + "vtsfile/" + mTempFileName));
			trans.transform(new StreamSource(xmlStream), result);

			mResult.add(mTempFileName);

			mLogger.debug("Transform Success!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}

		return true;
	}

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

		File tFile = new File("c:/PutFile2.xml");
		FileInputStream tFis = new FileInputStream(tFile);

		Object tObj = tFis;

		InputStream is = (InputStream) tObj;
		
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("OutputType", "3");
		tTransferData.setNameAndValue("Language", "zh");
		
		VData tVData = new VData();
		tVData.add(is);
		tVData.add(tGI);
		tVData.add("D:/lis-national-Works/lis-national/ui/");
		tVData.add(tTransferData);

		XslPrint tSP = new XslPrint();
		tSP.submitData(tVData, null);
		// tSP.submitPrint(tGI, is, "c:/printtest/");
		// tSP.transformInputstream(tFis);
	}

	public boolean submitData(VData cVData, String cOperater)
	{
		boolean tFlag = true;
		if (!getInputData(cVData))
		{
			CError tError = new CError();
			tError.moduleName = "XslPrint";
			tError.functionName = "getInputData";
			tError.errorMessage = "获得信息异常！";
			mErrors.addOneError(tError);
			tFlag = false;
		}

		// 得到外部传入的数据，将数据备份到本类中
		if (tFlag&&!parserIS(new ByteArrayInputStream(mbyte)))
		{
			CError tError = new CError();
			tError.moduleName = "XslPrint";
			tError.functionName = "parserIS";
			tError.errorMessage = "传入的xml信息异常！";
			mErrors.addOneError(tError);
			tFlag = false;
		}

		mTempFileName = PrintFunc.getTempFileName(mGlobalInput) + ".txt";

		// 得到外部传入的数据，将数据备份到本类中
		if (tFlag&&!xmlTransform(new ByteArrayInputStream(mbyte), mAppPath))
		{
			CError tError = new CError();
			tError.moduleName = "XslPrint";
			tError.functionName = "xmlTransform";
			tError.errorMessage = "读取xml信息异常！";
			mErrors.addOneError(tError);
			tFlag = false;
		}
		if (tFlag)
		{
			// 打印成功
			 PrintFunc.addPrintLog(mTransferData, "0", "打印成功",mGlobalInput.Operator);
		}
		else
		{
			// 打印失败，错误信息放到第三个参数
			 PrintFunc.addPrintLog(mTransferData, "1", mErrors.getFirstError(),mGlobalInput.Operator);
		}

		return tFlag;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		InputStream tInputStream = (InputStream) cInputData.get(0);
		mbyte = PrintFunc.transformInputstream(tInputStream);
		mGlobalInput = (GlobalInput) cInputData.get(1);
		mAppPath = (String) cInputData.get(2);
		mTransferData = (TransferData) cInputData.get(3);

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
