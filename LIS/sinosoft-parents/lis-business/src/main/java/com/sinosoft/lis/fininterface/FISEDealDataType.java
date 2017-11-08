package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.FIDataDealTypeDefSchema;
import com.sinosoft.lis.schema.FIAboriginalDataForDealSchema;
import com.sinosoft.lis.vschema.FIAboriginalDataForDealSet;
import com.sinosoft.lis.db.FIDataDealTypeDefDB;
import com.sinosoft.lis.schema.FIAboriginalDataSchema;
import com.sinosoft.lis.vschema.FIAboriginalDataSet;
import com.sinosoft.lis.vschema.FIDataDealTypeDefSet;

/**
 * <p>
 * ClassName: FISEDealDataType
 * </p>
 * <p>
 * Description: 财务接口->数据二次处理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * @Database: 财务接口
 * @author：ZhongYan
 * @version：1.0
 * @CreateDate：2008-08-11
 */

public class FISEDealDataType
{
private static Logger logger = Logger.getLogger(FISEDealDataType.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 存储处理信息 */
	private VData mInputInfo = new VData();

	private FIAboriginalDataForDealSet mFIAboriginalDataForDealSet = new FIAboriginalDataForDealSet();

	private FIAboriginalDataSet mFIAboriginalDataSet = new FIAboriginalDataSet();

	private FIAboriginalDataForDealSchema mFIAboriginalDataForDealSchema = new FIAboriginalDataForDealSchema();

	private FIAboriginalDataSchema mFIAboriginalDataSchema = new FIAboriginalDataSchema();

	private FIDataDealTypeDefSchema mFIDataDealTypeDefSchema = new FIDataDealTypeDefSchema();

	private MMap mmap = new MMap();

	/** 业务数据 */
	GlobalInput mGlobalInput = new GlobalInput();

	String StartDate = "";

	String EndDate = "";

	String BatchNo = "";

	String VersionNo = "";

	String CostID = "";

	String DataType = "";

	public FISEDealDataType()
	{}

	public static void main(String[] args)
	{
		FISEDealDataType tFISEDealDataType = new FISEDealDataType();
	}

	public boolean dealProcess(VData cInputData)
	{
		if (!getInputData(cInputData))
		{

		}
		if (!InitInfo())
		{

		}
		if (!DealWithData())
		{

		}
		if (!WriteLog("SUCC"))
		{

		}
		return true;
	}

	private boolean getInputData(VData cInputData)
	{
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		StartDate = (String) cInputData.get(1);
		BatchNo = (String) cInputData.get(2);
		VersionNo = (String) cInputData.get(3);
		mFIAboriginalDataForDealSet = (FIAboriginalDataForDealSet) cInputData.getObjectByObjectName("FIAboriginalDataForDealSet", 0);
		DataType = mFIAboriginalDataForDealSet.get(1).getDataType();
		if (mGlobalInput == null)
		{
			buildError("getInputData", "前台传入的登陆信息为空!");
			return false;
		}
		if (StartDate == null || StartDate.equals(""))
		{
			buildError("getInputData", "没有起始日期!");
			return false;
		}
		if (BatchNo == null || BatchNo.equals(""))
		{
			buildError("getInputData", "没有批次号码!");
			return false;
		}
		if (VersionNo == null || VersionNo.equals(""))
		{
			buildError("getInputData", "没有版本号码!");
			return false;
		}

		return true;
	}

	private boolean InitInfo()
	{
		try
		{

			mInputInfo.add(mGlobalInput);
			mInputInfo.add(StartDate);
			mInputInfo.add(BatchNo);
			mInputInfo.add(VersionNo);
			FIDataDealTypeDefSet tFIDataDealTypeDefSet = new FIDataDealTypeDefSet();
			FIDataDealTypeDefDB tFIDataDealTypeDefDB = new FIDataDealTypeDefDB();
			tFIDataDealTypeDefDB.setVersionNo(VersionNo);
			tFIDataDealTypeDefDB.setDataType(DataType);
			tFIDataDealTypeDefSet = tFIDataDealTypeDefDB.query();
			if (tFIDataDealTypeDefSet.size() > 1)
			{
				logger.debug("DataType查询出多条记录");
			}
			mFIDataDealTypeDefSchema = tFIDataDealTypeDefSet.get(1);

		}
		catch (Exception ex)
		{
			buildError("InitInfo", ex.getMessage());
			return false;
		}
		return true;
	}

	private boolean DealWithData()
	{
		try
		{
			mFIAboriginalDataSet = DealOperationDataInfo(mFIAboriginalDataForDealSet);
			return true;
		}
		catch (Exception ex)
		{
			buildError("DealWithData", ex.getMessage());
			return false;
		}
	}

	private FIAboriginalDataSet DealOperationDataInfo(FIAboriginalDataForDealSet tFIAboriginalDataForDealSet)
			throws Exception
	{
		FIAboriginalDataSet tFIAboriginalDataSet = new FIAboriginalDataSet();
		try
		{
			Class tClass = Class.forName(mFIDataDealTypeDefSchema.getProcessClass());
			DataTypeProcess mDataTypeProcessClass = (DataTypeProcess) tClass.newInstance();
			VData sVData = new VData();
			sVData.add(mGlobalInput);
			sVData.add(StartDate);
			sVData.add(EndDate);
			sVData.add(BatchNo);
			sVData.add(VersionNo);
			sVData.add(tFIAboriginalDataForDealSet);

			tFIAboriginalDataSet = mDataTypeProcessClass.SEDealInfo(sVData);
			mmap.put(tFIAboriginalDataSet, "INSERT");
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql("update FIAboriginalDataForDeal set DataState = '01' where batchno = '?BatchNo?' and DataType= '?DataType?' and datastate ='00' ");
			sqlbv.put("BatchNo", BatchNo);
			sqlbv.put("DataType", DataType);
			mmap.put(sqlbv, "UPDATE");			
			VData tInputData = new VData();
			tInputData.add(mmap);
			PubSubmit tPubSubmit = new PubSubmit();
			if (tPubSubmit.submitData(tInputData, ""))
			{
//				ExeSQL tExeSQl = new ExeSQL();
//				tExeSQl.execSQL("update FIAboriginalDataForDeal set DataState = '01' where batchno = '" +
//	                    BatchNo + "' and DataType= '" + DataType + "' and datastate ='00' ");
			}
			else
			{
				logger.debug("数据二次处理规则处理类错误！");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return tFIAboriginalDataSet;
	}

	private boolean WriteLog(String tFlag)
	{
		// try
		// {
		// DataToSubmit tDataToSubmit = new DataToSubmit();
		// VData lInputData = new VData();
		// LIDistillLogSet tLIDistillLogSet = new LIDistillLogSet();
		// LIDistillLogSchema tLIDistillLogSchema = new LIDistillLogSchema();
		// tLIDistillLogSchema.setBatchNo(BatchNo);
		// tLIDistillLogSchema.setStartDate(StartDate);
		// tLIDistillLogSchema.setEndDate(EndDate);
		// tLIDistillLogSchema.setFlag(tFlag);
		// tLIDistillLogSchema.setMakeDate(PubFun.getCurrentDate());
		// tLIDistillLogSchema.setMakeTime(PubFun.getCurrentTime());
		// tLIDistillLogSchema.setManageCom(mGlobalInput.ManageCom);
		// tLIDistillLogSchema.setOperator(mGlobalInput.Operator);
		// tLIDistillLogSet.add(tLIDistillLogSchema);
		// lInputData.add(tLIDistillLogSet);
		// if(!tDataToSubmit.submitData(lInputData,""))
		// {
		// buildError("WriteLog","记录提数日志出错！");
		// return false;
		// }
		//
		// return true;
		// }
		// catch (Exception ex)
		// {
		// buildError("WriteLog",ex.getMessage());
		// return false;
		// }
		return true;
	}

	public boolean WriteErrLog()
	{
		// try
		// {
		// if(this.mErrors.needDealError())
		// {
		// DataToSubmit tDataToSubmit = new DataToSubmit();
		// VData lInputData = new VData();
		// LIDistillLogSet tLIDistillLogSet = new LIDistillLogSet();
		// LIDistillLogSchema tLIDistillLogSchema = new LIDistillLogSchema();
		// tLIDistillLogSchema.setBatchNo(BatchNo);
		// tLIDistillLogSchema.setStartDate(StartDate);
		// tLIDistillLogSchema.setEndDate(EndDate);
		// tLIDistillLogSchema.setFlag("Lose");
		// tLIDistillLogSchema.setFilePath(this.mErrors.getFirstError());
		// tLIDistllLogSchema.setMakeDate(PubFun.getCurrentDate());
		// tLIDistillLogSchema.setMakeTime(PubFun.getCurrentTime());
		// tLIDistillLogSchema.setManageCom(mGlobalInput.ManageCom);
		// tLIDistillLogSchema.setOperator(mGlobalInput.Operator);
		// tLIDistillLogSet.add(tLIDistillLogSchema);
		// lInputData.add(tLIDistillLogSet);
		// if (!tDataToSubmit.submitData(lInputData, "")) {
		// this.mErrors = new CErrors();
		// buildError("WriteErrLog",
		// "记录提数日志出错！"+tDataToSubmit.mErrors.getFirstError());
		// return false;
		// }
		// return true;
		// }
		// else
		// {
		// return true;
		// }
		//
		// }
		// catch (Exception ex)
		// {
		// this.mErrors = new CErrors();
		// buildError("WriteLog",ex.getMessage());
		// return false;
		// }
		return true;
	}

	private void buildError(String szFunc, String szErrMsg)
	{
		logger.debug("错误");
		// CError cError = new CError();
		// cError.moduleName = "FInDealEngine";
		// cError.functionName = szFunc;
		// cError.errorMessage = szErrMsg;
		// this.mErrors.addOneError(cError);
		// logger.debug(szErrMsg);
	}

}
