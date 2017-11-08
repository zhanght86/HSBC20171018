package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.schema.FIOperationLogSchema;
import com.sinosoft.lis.schema.FIOperationParameterSchema;
import com.sinosoft.lis.schema.FIDataDealErrSchema;
import com.sinosoft.lis.vschema.FIOperationLogSet;
import com.sinosoft.lis.vschema.FIOperationParameterSet;
import com.sinosoft.lis.vschema.FIDataDealErrSet;
import com.sinosoft.lis.pubfun.*;
/*
 * fanxin
 * 2008-09-05
*/
public class LogInfoSubmit
{
private static Logger logger = Logger.getLogger(LogInfoSubmit.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	
	private VData mInputDealErrData = new VData();
	// 业务处理相关变量
	/** 全局数据 */
	private String mContent = "";
	
	private FIOperationLogSchema mFIOperationLogSchema = new FIOperationLogSchema();
	private FIOperationParameterSet mFIOperationParameterSet = new FIOperationParameterSet();
	private FIDataDealErrSet mFIDataDealErrSet = new FIDataDealErrSet();
	
	public LogInfoSubmit()
	{}
	
	/**
	 * 传输数据的公共方法
	 */
	public boolean submitLogData(VData cInputData,String txt)
	{
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData,txt))
		{
			return false;
		}
		// 进行业务处理
		if (!dealData())
		{
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData())
		{
			return false;
		}
		LogInfoSave tLogInfoSave = new LogInfoSave();
		logger.debug("Start LogInfo Save!");
		logger.debug(1);
		tLogInfoSave.submitLogData(mInputData,mContent);
		logger.debug("End LogInfo Save!");
		// 如果有需要处理的错误，则返回		
		if(tLogInfoSave.mErrors.needDealError())
		{
//			 @@错误处理
			this.mErrors.copyAllErrors(tLogInfoSave.mErrors);
			CError tError = new CError();
			tError.moduleName = "LogInfoSubmit";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		return true;
	}
	
	public static void main(String[] args)
	{
		/*boolean OperationLog;
		boolean DataDealErr;
		FIOperationLogSchema tFIOperationLogSchema = new FIOperationLogSchema();
		FIOperationParameterSchema tFIOperationParameterSchema1 = new FIOperationParameterSchema();
		FIOperationParameterSchema tFIOperationParameterSchema2 = new FIOperationParameterSchema();
		FIOperationParameterSet tFIOperationParameterSet = new FIOperationParameterSet();
		FIDataDealErrSchema tFIDataDealErrSchema1 = new FIDataDealErrSchema();
		FIDataDealErrSchema tFIDataDealErrSchema2 = new FIDataDealErrSchema();
		FIDataDealErrSet tFIDataDealErrSet = new FIDataDealErrSet();
		tFIOperationLogSchema.setEventNo("12583");
		tFIOperationLogSchema.setEventType("Event");
		tFIOperationLogSchema.setLogFileName("aaa");
		tFIOperationLogSchema.setLogFilePath("D:\\");
		tFIOperationLogSchema.setPerformState("Common");	
		tFIOperationLogSchema.setothernoMark("k");
		tFIOperationLogSchema.setOperator("001");
		tFIOperationLogSchema.setMakeDate(PubFun.getCurrentDate());
		tFIOperationLogSchema.setMakeTime(PubFun.getCurrentTime());
		/*tFIOperationParameterSchema1.setEventNo("12582");
		tFIOperationParameterSchema1.setEventType("Event");
		tFIOperationParameterSchema1.setParameterType("Type9");
		tFIOperationParameterSchema1.setParameterMark("Mark1");
		tFIOperationParameterSchema1.setLogFilePath("10010");
		tFIOperationParameterSchema2.setEventNo("12582");
		tFIOperationParameterSchema2.setEventType("Event");
		tFIOperationParameterSchema2.setParameterType("Type2");
		tFIOperationParameterSchema2.setParameterMark("Mark2");
		tFIOperationParameterSchema2.setLogFilePath("66666");
		tFIOperationParameterSet.add(tFIOperationParameterSchema1);
		tFIOperationParameterSet.add(tFIOperationParameterSchema2);
		String Content = "1111111111";
		tFIDataDealErrSchema1.setEeeSerialNo("13802");
		tFIDataDealErrSchema1.setErrStage("A10");
		tFIDataDealErrSchema1.setErrType("Err1");
		tFIDataDealErrSchema1.setBatchNo("123");
		tFIDataDealErrSchema1.setAFSerialNo("456");
		tFIDataDealErrSchema1.setBussDate("1999-08-09");
		tFIDataDealErrSchema1.setErrInfo("This is a Error");
		tFIDataDealErrSchema1.setErrDealState("B20");
		tFIDataDealErrSchema1.setDealDescription("C30");
		tFIDataDealErrSchema1.setMakeDate(PubFun.getCurrentDate());
		tFIDataDealErrSchema1.setMakeTime(PubFun.getCurrentTime());
		tFIDataDealErrSchema2.setEeeSerialNo("13803");
		tFIDataDealErrSchema2.setErrStage("A10");
		tFIDataDealErrSchema2.setErrType("Err1");
		tFIDataDealErrSchema2.setBatchNo("123");
		tFIDataDealErrSchema2.setAFSerialNo("456");
		tFIDataDealErrSchema2.setBussDate("1999-08-09");
		tFIDataDealErrSchema2.setErrInfo("This is a Error");
		tFIDataDealErrSchema2.setErrDealState("B20");
		tFIDataDealErrSchema2.setDealDescription("C30");
		tFIDataDealErrSchema2.setMakeDate(PubFun.getCurrentDate());
		tFIDataDealErrSchema2.setMakeTime(PubFun.getCurrentTime());
		tFIDataDealErrSet.add(tFIDataDealErrSchema1);
		tFIDataDealErrSet.add(tFIDataDealErrSchema2);
		LogInfoSubmit tLogInfoSubmit = new LogInfoSubmit();
		VData tVData = new VData();
		tVData.addElement(tFIOperationLogSchema);
		tVData.addElement(tFIOperationParameterSet);
		OperationLog=tLogInfoSubmit.submitLogData(tVData,Content);
		VData tVData1 = new VData();
		tVData1.addElement(tFIDataDealErrSet);
		DataDealErr=tLogInfoSubmit.submitDealErrData(tVData1);
		logger.debug("OperationLog==============="+OperationLog+"||||||||||||"+"DataDealErr======="+DataDealErr);//本地测试用*/
		
	}
	
	public boolean prepareOutputData()
	{

		try
		{
			mInputData.clear();
			mInputData.add(this.mFIOperationLogSchema);
			mInputData.add(this.mFIOperationParameterSet);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LogInfoSubmit";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
	
	public boolean dealData()
	{
		boolean tReturn = true;
		return tReturn;
	}
	
	public boolean getInputData(VData cInputData,String txt)
	{
		this.mFIOperationLogSchema = (FIOperationLogSchema) cInputData.getObjectByObjectName("FIOperationLogSchema", 0);
		this.mFIOperationParameterSet = (FIOperationParameterSet) cInputData.getObjectByObjectName("FIOperationParameterSet", 0);
		this.mContent = txt;
		return true;
	}
	
	public boolean submitDealErrData(VData cInputDealErrData)
	{
//		 得到外部传入的数据,将数据备份到本类中
		if (!getInputDealErrData(cInputDealErrData))
		{
			return false;
		}
		// 进行业务处理
		if (!dealDealErrData())
		{
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputDealErrData())
		{
			return false;
		}
		LogInfoSave tLogInfoSave = new LogInfoSave();
		logger.debug("Start LogInfo Save!");
		logger.debug(1);
		tLogInfoSave.submitLogDealErrData(mInputDealErrData);
		logger.debug("End LogInfo Save!");
		// 如果有需要处理的错误，则返回		
		if(tLogInfoSave.mErrors.needDealError())
		{
			 //@@错误处理
			this.mErrors.copyAllErrors(tLogInfoSave.mErrors);
			CError tError = new CError();
			tError.moduleName = "LogInfoSubmit";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputDealErrData = null;
		return true;
	}
	public boolean prepareOutputDealErrData()
	{

		try
		{
			mInputDealErrData.clear();
			mInputDealErrData.add(this.mFIDataDealErrSet);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LogInfoSubmit";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
	
	public boolean dealDealErrData()
	{
		boolean tReturn = true;
		return tReturn;
	}
	
	public boolean getInputDealErrData(VData cInputDealErrData)
	{
		this.mFIDataDealErrSet = (FIDataDealErrSet) cInputDealErrData.getObjectByObjectName("FIDataDealErrSet", 0);
		return true;
	}
	
	
}
