package com.sinosoft.print.manage;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.print.func.*;

public class LPrtDefinitionBL
{
private static Logger logger = Logger.getLogger(LPrtDefinitionBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	private MMap map = new MMap();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private LPrtDefinitionSchema mLPrtDefinitionSchema = new LPrtDefinitionSchema();

	private LPrtTempleteLogSchema mLPrtTempleteLogSchema = new LPrtTempleteLogSchema();

	private String mPrintID;

	public LPrtDefinitionBL()
	{}

	/**
	 * 传输数据的公共方法
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate)
	{
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
		{
			return false;
		}
		// 删除时校验该打印项目是否有默认模板
		if (!checkData(cOperate))
		{
			return false;
		}

		// 进行业务处理
		if (!dealData())
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtDefinitionBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败LPrtDefinitionBL-->dealData!";
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
			logger.debug("Start LPrtDefinitionBL Submit...");

			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mInputData, null))
			{
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);

				CError tError = new CError();
				tError.moduleName = "LPrtDefinitionBL";
				tError.functionName = "LPrtDefinitionBL";
				tError.errorMessage = "数据提交失败!";

				this.mErrors.addOneError(tError);
				return false;
			}

			logger.debug("---LPrtDefinitionBL---");

		}
		mInputData = null;
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData()
	{
		LPrtDefinitionDB tLPrtDefinitionDB = new LPrtDefinitionDB();
		if (this.mOperate.equals("INSERT||MAIN"))
		{
			String tCurrentDate = PubFun.getCurrentDate();
			String tCurrentTime = PubFun.getCurrentTime();
			mLPrtDefinitionSchema.setMakeDate(tCurrentDate);
			mLPrtDefinitionSchema.setMakeTime(tCurrentTime);
			mLPrtDefinitionSchema.setModifyTime(tCurrentTime);
			mLPrtDefinitionSchema.setModifyDate(tCurrentDate);
			mLPrtDefinitionSchema.setOperator(mGlobalInput.Operator);
			String tNo = PubFun1.CreateMaxNo("PRINTID", 6);
			mLPrtDefinitionSchema.setPrintID(tNo);
			String tPrintName = mLPrtDefinitionSchema.getPrintName();
			ExeSQL tExe = new ExeSQL();
			String strSql = "select Printname from Lprtdefinition where Printname= '" + tPrintName + "'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(strSql);
			sqlbv.put("tPrintName", tPrintName);
			if (!tExe.getOneValue(sqlbv).equals(tPrintName))
			{
				map.put(mLPrtDefinitionSchema, "INSERT");
			}
			else
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LPrtDefinitionBL";
				tError.functionName = "dealData()";
				tError.errorMessage = "系统中已存在该打印名称！";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		if (this.mOperate.equals("UPDATE||MAIN"))
		{
			tLPrtDefinitionDB.setPrintID(mLPrtDefinitionSchema.getPrintID());
			tLPrtDefinitionDB.getInfo();
			String tCurrentDate = PubFun.getCurrentDate();
			String tCurrentTime = PubFun.getCurrentTime();
			mLPrtDefinitionSchema.setMakeDate(tLPrtDefinitionDB.getMakeDate());
			mLPrtDefinitionSchema.setMakeTime(tLPrtDefinitionDB.getMakeTime());
			mLPrtDefinitionSchema.setModifyTime(tCurrentTime);
			mLPrtDefinitionSchema.setModifyDate(tCurrentDate);
			mLPrtDefinitionSchema.setOperator(mGlobalInput.Operator);
			String tPrintName = mLPrtDefinitionSchema.getPrintName();
			String tPrintName1 = tLPrtDefinitionDB.getPrintName();
			map.put(mLPrtDefinitionSchema, "UPDATE");
			// 打印定义名称修改后 模板名称相应的也要修改
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql("update  lprttemplete set  templetename = '?tPrintName?'" + "where  templetename ='?tPrintName1?'");
			sqlbv1.put("tPrintName", tPrintName);
			sqlbv1.put("tPrintName1", tPrintName1);
			map.put(sqlbv1, "UPDATE");
		}
		if (this.mOperate.equals("DELETE||MAIN"))
		{
			map.put(mLPrtDefinitionSchema, "DELETE");
		}
		mLPrtTempleteLogSchema = PrintFunc.getTemLog(mOperate.substring(0, 6), mLPrtDefinitionSchema.getPrintID(), "","0",mGlobalInput.Operator);
		map.put(mLPrtTempleteLogSchema, "INSERT");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		this.mLPrtDefinitionSchema.setSchema((LPrtDefinitionSchema) cInputData.getObjectByObjectName("LPrtDefinitionSchema", 0));
		this.mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		return true;
	}

	/**
	 *数据校验，如果操作为DELETE且该打印项目有打印模板，则不能删除该打印项
	 */
	private boolean checkData(String cOperate)
	{
		String tPrintName = mLPrtDefinitionSchema.getPrintName();
		String tSql1 = "select count(*) from lprttemplete where  templetename ='?tPrintName?'";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tSql1);
		sqlbv2.put("tPrintName", tPrintName);
		ExeSQL tExe1 = new ExeSQL();
		if (cOperate.equals("DELETE||MAIN") && !tExe1.getOneValue(sqlbv2).equals("0"))
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtDefinitionBL";
			tError.functionName = "checkData";
			tError.errorMessage = "该打印项目有打印模板，则不能删除该打印定义!";
			this.mErrors.addOneError(tError);
			return false;
		}
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
			mPrintID = mLPrtDefinitionSchema.getPrintID();
			mResult.add(mPrintID);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtDefinitionBL";
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
