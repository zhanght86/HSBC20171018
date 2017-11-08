package com.sinosoft.print.manage;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.print.func.*;
import com.sinosoft.utility.*;

public class LPrtXmlStyleBL
{
private static Logger logger = Logger.getLogger(LPrtXmlStyleBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	private MMap map = new MMap();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private LPrtXmlStyleSchema mLPrtXmlStyleSchema = new LPrtXmlStyleSchema();
	
	private LPrtTempleteDB mLPrtTempleteDB = new LPrtTempleteDB();

	private LPrtTempleteLogSchema mLPrtTempleteLogSchema = new LPrtTempleteLogSchema();

	private ExportFile  mExportFile = new ExportFile();
	
	/** 业务处理相关变量 */

	private String mTempleteID = "";

	private String mFileName = "";
	
	private String mState = "";

	public LPrtXmlStyleBL()
	{}

	/**
	 * 传输数据的公共方法
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate)
	{
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
		{
			return false;
		}
		// 进行业务处理
		if (!dealData())
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtXmlStyleBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败LPrtXmlStyleBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData())
		{
			return false;
		}
		else
		{
			logger.debug("Start LPrtXmlStyleBL Submit...");

			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mInputData, null))
			{
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);

				CError tError = new CError();
				tError.moduleName = "LPrtXmlStyleBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";

				this.mErrors.addOneError(tError);
				return false;
			}

			logger.debug("---LPrtXmlStyleBL---");

		}
		mInputData = null;
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData()
	{
		mTempleteID = mLPrtXmlStyleSchema.getTempleteID();
		mLPrtTempleteDB.setTempleteID(mTempleteID);
		mLPrtTempleteDB.getInfo();
		mState = mLPrtTempleteDB.getState();
		//xml文件导出的文件名根据状态增加测试或发布字样
		if(mState.equals("1"))
		{
			mFileName = mTempleteID +" - release";
		}
		else if(mState.equals("2"))
		{
			mFileName = mTempleteID + " - test";
		}
		String tSql = "select printid from lprtrelated where templeteid = '?mTempleteID?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("mTempleteID", mTempleteID);
		ExeSQL tExe = new ExeSQL();
		String tPrintID = tExe.getOneValue(sqlbv);
		//数据导出，生成XmlExportNew对象
		if (!mExportFile.exportData(mTempleteID))
		{
			CError tError = new CError();
			tError.moduleName = "LPrtXmlStyleBL";
			tError.functionName = "dealData";
			tError.errorMessage = mExportFile.getErrors().getFirstError();
			mErrors.addOneError(tError);
			return false;
		}
		mLPrtTempleteLogSchema = PrintFunc.getTemLog("EXPORTFILE", tPrintID, mTempleteID, "3", mGlobalInput.Operator);
		map.put(mLPrtTempleteLogSchema, "INSERT");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		mLPrtXmlStyleSchema.setSchema((LPrtXmlStyleSchema) cInputData.getObjectByObjectName("LPrtXmlStyleSchema", 0));
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		return true;
	}

	/**
     * 准备往后层输出所需要的数据
     * 输出：如果准备数据时发生错误则返回false,否则返回true
     */
	private boolean prepareOutputData()
	{
		try
		{
			mInputData.clear();
			mInputData.add(map);
			mResult.clear();
			mResult = mExportFile.getResult();
			mResult.add(mFileName);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtXmlStyleBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 获取处理结果
	 */
	public VData getResult()
	{
		return mResult;
	}

	/**
	 * 获取错误信息
	 */
	public CErrors getErrors()
	{
		return mErrors;
	}
}
