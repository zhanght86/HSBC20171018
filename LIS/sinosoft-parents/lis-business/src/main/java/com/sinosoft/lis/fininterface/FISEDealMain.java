package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.db.FIRulesVersionDB;
import com.sinosoft.lis.db.FIAboriginalDataForDealDB;
import com.sinosoft.lis.vschema.FIRulesVersionSet;
import com.sinosoft.lis.vschema.FIAboriginalDataForDealSet;
import com.sinosoft.lis.vschema.FIAboriginalDataSet;

/**
 * <p>
 * ClassName: FISEDealMain
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

public class FISEDealMain
{
private static Logger logger = Logger.getLogger(FISEDealMain.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 存储处理信息 */
	private VData mInputInfo = new VData();

	/** 存储业务数据类型 */
	public FIAboriginalDataSet mFIAboriginalDataSet = new FIAboriginalDataSet();

	private FIRulesVersionSet mFIRulesVersionSet;

	private FIAboriginalDataForDealSet mFIAboriginalDataForDealSet;

	private FISEDealDataType mFISEDealDataType[];

	/** 业务数据 */
	GlobalInput mGlobalInput = new GlobalInput();

	String StartDate = "";

	String EndDate = "";

	String BatchNo = "";

	String VersionNo = "";

	/** 容器 */
	public MMap map = new MMap();

	public FISEDealMain()
	{}

	public boolean dealProcess(VData cInputData)
	{
		if (!getInputData(cInputData))
		{
			return false;
		}
		if (!InitInfo())
		{
			return false;
		}
		if (!DealWithData())
		{
			return false;
		}
		if (!WriteLog("SUCC"))
		{
			return false;
		}

		return true;
	}

	// public boolean dealRProcess(VData cInputData)
	// {
	// if (!getRInputData(cInputData))
	// {
	// return false;
	// }
	// if (!DealRWithData())
	// {
	// return false;
	// }
	// if (!WriteLog("SUCC"))
	// {
	// return false;
	// }
	//
	// return true;
	// }

	private boolean getInputData(VData cInputData)
	{
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		StartDate = (String) cInputData.get(1);
		EndDate = (String) cInputData.get(2);
		BatchNo = (String) cInputData.get(3);
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
		if (EndDate == null || EndDate.equals(""))
		{
			buildError("getInputData", "没有终止日期!");
			return false;
		}

		return true;
	}

	// private boolean getRInputData(VData cInputData)
	// {
	// mGlobalInput.setSchema((GlobalInput)
	// cInputData.getObjectByObjectName("GlobalInput", 0));
	// StartDate = (String) cInputData.get(1);
	// EndDate = (String) cInputData.get(2);
	// BatchNo = (String) cInputData.get(3);
	// VersionNo = (String) cInputData.get(4);
	// mFIAboriginalDataForDealSet = (FIAboriginalDataForDealSet)
	// cInputData.getObjectByObjectName("FIAboriginalDataForDealSet", 0);
	// if (mGlobalInput == null)
	// {
	// buildError("getInputData", "前台传入的登陆信息为空!");
	// return false;
	// }
	// if (StartDate == null || StartDate.equals(""))
	// {
	// buildError("getInputData", "没有起始日期!");
	// return false;
	// }
	// if (EndDate == null || EndDate.equals(""))
	// {
	// buildError("getInputData", "没有终止日期!");
	// return false;
	// }
	//
	// return true;
	// }

	private boolean getRuleVersion(String sDate, String eDate)
	{
		String sql = " select * from (select * from FIRulesVersion where StartDate<=to_date('?sDate?','yyyy-mm-dd') " + " and EndDate>to_date('?sDate?','yyyy-mm-dd') union all "
				+ " select * from FIRulesVersion where startdate>=to_date('?sDate?','yyyy-mm-dd')  "
				+ " and enddate<to_date('?eDate?','yyyy-mm-dd') "
				+ " union all select * from FIRulesVersion where " + " startdate<=to_date('?eDate?','yyyy-mm-dd') and enddate is null ) g order by startdate desc ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("sDate", sDate);
		sqlbv.put("eDate", eDate);
		
		FIRulesVersionDB tFIRulesVersionDB = new FIRulesVersionDB();
		logger.debug(sql);
		mFIRulesVersionSet = tFIRulesVersionDB.executeQuery(sqlbv);
		if (mFIRulesVersionSet.size() > 1)
		{
			logger.debug("查询版本多于一个");
			return false;
		}
		if (mFIRulesVersionSet.size() == 0)
		{
			logger.debug("无可用的版本");
			return false;
		}
		VersionNo = mFIRulesVersionSet.get(1).getVersionNo();
		return true;
	}

	private boolean InitInfo()
	{
		try
		{
			if (!getRuleVersion(StartDate, EndDate))
			{
				buildError("getInputData", "没有终止日期!");
				return false;
			}
		}
		catch (Exception ex)
		{
			buildError("InitInfo", ex.getMessage());
			return false;
		}
		return true;
	}

	// private boolean DealRWithData()
	// {
	// try
	// {
	// String cycDate = this.StartDate;
	// while (cycDate.compareTo(this.EndDate) <= 0)
	// {
	// mFISEDealDataType = new
	// FISEDealDataType[mFIAboriginalDataForDealSet.size()];
	// for (int i = 0; i < mFIAboriginalDataForDealSet.size(); i++)
	// {
	// mFISEDealDataType[i] = new FISEDealDataType();
	// FIAboriginalDataForDealSchema tFIAboriginalDataForDealSchema = new
	// FIAboriginalDataForDealSchema();
	// tFIAboriginalDataForDealSchema = mFIAboriginalDataForDealSet.get(i + 1);
	// mInputInfo.clear();
	// mInputInfo.add(mGlobalInput);
	// mInputInfo.add(cycDate);
	// mInputInfo.add(BatchNo);
	// mInputInfo.add(VersionNo);
	// mInputInfo.add(tFIAboriginalDataForDealSchema);
	// mFIAboriginalDataSet.add(mFISEDealDataType[i].dealProcess(mInputInfo));
	// if (mFISEDealDataType[i].mErrors.needDealError())
	// {
	// this.mErrors.copyAllErrors(mFISEDealDataType[i].mErrors);
	// return false;
	// }
	// }
	// // for (int j = 0; j < mFIAboriginalDataForDealSet.size(); j++)
	// // {
	// // //这儿不用改成02
	// // mFIAboriginalDataForDealSet.get(j + 1).setDataState("02");
	// // }
	// map.put(mFIAboriginalDataSet, "INSERT");
	// VData tInputInfo = new VData();
	// tInputInfo.add(map);
	// PubSubmit tPubSubmit = new PubSubmit();
	// if (!tPubSubmit.submitData(tInputInfo, ""))
	// {
	// this.mErrors.copyAllErrors(tPubSubmit.mErrors);
	// buildError("DealWithData", "数据提交失败!");
	// return false;
	// }
	// cycDate = PubFun.calDate(cycDate, 1, "D", null);
	// }
	// return true;
	// }
	// catch (Exception ex)
	// {
	// buildError("DealRWithData", ex.getMessage());
	// return false;
	// }
	// }

	private boolean DealWithData()
	{
		try
		{
			String cycDate = this.StartDate;
			while (cycDate.compareTo(this.EndDate) <= 0)
			{
				SSRS tSSRS = new SSRS();
				ExeSQL tExeSQL = new ExeSQL();
				String sql = "select distinct DataType from FIAboriginalDataForDeal where batchno = '?BatchNo?' order by DataType ";
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql(sql);
				sqlbv1.put("BatchNo", BatchNo);
				tSSRS = tExeSQL.execSQL(sqlbv1);

				// 判断是否有记录如果没有记录则返回空
				if (tSSRS == null || tSSRS.getMaxRow() == 0)
				{
					return false;
				}
				else
				{
					mFISEDealDataType = new FISEDealDataType[tSSRS.getMaxRow()];
				}
				for (int i = 1; i <= tSSRS.getMaxRow(); i++)
				{
					//mFIAboriginalDataForDealSet.clear();
					String tDataType = tSSRS.GetText(i, 1);
					FIAboriginalDataForDealDB tFIAboriginalDataForDealDB = new FIAboriginalDataForDealDB();
					SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
					sqlbv2.sql("select * from FIAboriginalDataForDeal a where a.batchno = '?BatchNo?' and a.DataType= '?tDataType?' and a.DataState ='00' ");
					sqlbv2.put("BatchNo", BatchNo);
					sqlbv2.put("tDataType", tDataType);
					mFIAboriginalDataForDealSet = tFIAboriginalDataForDealDB.executeQuery(sqlbv2);			
					mInputInfo.clear();
					mInputInfo.add(mGlobalInput);
					mInputInfo.add(cycDate);
					mInputInfo.add(BatchNo);
					mInputInfo.add(VersionNo);
					mInputInfo.add(mFIAboriginalDataForDealSet);
					mFISEDealDataType[i - 1] = new FISEDealDataType();
					mFISEDealDataType[i - 1].dealProcess(mInputInfo);					
				}

				// 对一个批次做整个的修改
				// map.put("update FIAboriginalDataForDeal set DataState = '01'
				// where batchno = '" + BatchNo
				// + "' and datastate ='01' ", "UPDATE");
				// map.put("update FIAboriginalDataForDeal set DataState = '02'
				// where batchno = '"+BatchNo+"' and datastate!='02' and
				// 时间","UPDATE");

				cycDate = PubFun.calDate(cycDate, 1, "D", null);
			}
			return true;
		}
		catch (Exception ex)
		{
			buildError("DealWithData", ex.getMessage());
			return false;
		}
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
		// tLIDistillLogSchema.setMakeDate(PubFun.getCurrentDate());
		// tLIDistillLogSchema.setMakeTime(PubFun.getCurrentTime());
		// tLIDistillLogSchema.setManageCom(mGlobalInput.ManageCom);
		// tLIDistillLogSchema.setOperator(mGlobalInput.Operator);
		// tLIDistillLogSet.add(tLDistillLogSchema);
		// lInputData.add(tLIDistillLogSet);
		// if (!tDataToSubmit.submitData(lIputData, "")) {
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
	// CError cError = new CError();
	// cError.moduleName = "FInDealEngine";
	// cError.functionName = szFunc;
	// cError.errorMessage = szErrMsg;
	// this.mErrors.addOneError(cError);
	// logger.debug(szErrMsg);
	}

	public static void main(String[] args)
	{
	// VData vData = new VData();
	// GlobalInput tG = new GlobalInput();
	// tG.Operator = "tk";
	// tG.ManageCom = "86";
	// String bdate = "2006-11-04";
	// String edate = "2006-11-04";
	// vData.addElement(tG);
	// vData.addElement(bdate);
	// vData.addElement(edate);
	// FInDealEngine tFInDealEngine = new FInDealEngine();
	// if(!tFInDealEngine.dealProcess(vData))
	// {
	// logger.debug(tFInDealEngine.mErrors.getFirstError());
	//        }
	}

}
