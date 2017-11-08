package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.vschema.FIDataDealTypeDefSet;
import com.sinosoft.lis.db.FIDataDealTypeDefDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.vschema.FIRulesVersionSet;
import com.sinosoft.lis.db.FIRulesVersionDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.vschema.FIDataDealErrSet;
import com.sinosoft.lis.db.FIPeriodManagementDB;
import com.sinosoft.lis.vschema.FIPeriodManagementSet;
import com.sinosoft.lis.schema.FIOperationLogSchema;
import com.sinosoft.lis.vschema.FIOperationParameterSet;
import com.sinosoft.lis.schema.FIOperationParameterSchema;

/**
 * <p>
 * ClassName: FISEDistillMain
 * </p>
 * <p>
 * Description: 财务接口-二次数据提取
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

public class FISEDistillMain
{
private static Logger logger = Logger.getLogger(FISEDistillMain.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 存储处理信息 */
	private VData mInputInfo = new VData();

	/** 存储业务数据类型 */
	private FIDataDealTypeDefSet mFIDataDealTypeDefSet;

	private FIRulesVersionSet mFIRulesVersionSet;

	private FISEDistillDealDataType mFISEDistillDealDataType[];

	/** 业务数据 */
	GlobalInput mGlobalInput = new GlobalInput();

	String StartDate = "";

	String EndDate = "";

	String BatchNo = "";
        String Mark ="";
	String VersionNo = "";

	String DataType = "";
        private final String enter = "\r\n"; // 换行符
        public LogInfoDeal tLogInfoDeal = null;
        public FIDataDealErrSet mFIDataDealErrSet = new FIDataDealErrSet();
        private FIOperationLogSchema mFIOperationLogSchema = new
                FIOperationLogSchema();


	public FISEDistillMain()
	{
//            tLogInfoDeal = new LogInfoDeal();
                if (!PrintData()) {
                        Mark = "日志文件产生失败!";
                        buildError("FIDistillMain", "日志文件产生失败");
                }
                WriteLog("采集数据源批处理开始启动。启动日期: " + PubFun.getCurrentDate()
                                + ",启动时间:" + PubFun.getCurrentTime() + enter,false);

        }

        private boolean PrintData(){
                mFIOperationLogSchema.setEventType("11");
                mFIOperationLogSchema.setOperator(mGlobalInput.Operator);
                FIOperationParameterSet tFIOperationParameterSet = new FIOperationParameterSet();
                FIOperationParameterSchema tFIOperationParameterschema = new FIOperationParameterSchema();
                tFIOperationParameterschema.setEventType("11");
                tFIOperationParameterschema.setParameterType("StartDate");
                tFIOperationParameterschema.setParameterMark("起始日期");
                tFIOperationParameterschema.setParameterValue(StartDate);
                tFIOperationParameterSet.add(tFIOperationParameterschema);
                tFIOperationParameterschema = new FIOperationParameterSchema();
                tFIOperationParameterschema.setEventType("11");
                tFIOperationParameterschema.setParameterType("EndDate");
                tFIOperationParameterschema.setParameterMark("终止日期");
                tFIOperationParameterschema.setParameterValue(EndDate);
                tFIOperationParameterSet.add(tFIOperationParameterschema);

                VData tVData = new VData();
                tVData.add(mFIOperationLogSchema);
                tVData.add(tFIOperationParameterSet);
//                if(!tLogInfoDeal.submitLogData(tVData)){
//                    return false;
//                }
                return true;
    }
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
		// if (!WriteLog("SUCC"))
		// {
		// return false;
		// }

		return true;
	}

	private boolean getInputData(VData cInputData)
	{
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		StartDate = (String) cInputData.get(1);
		EndDate = (String) cInputData.get(2);
		DataType = (String) cInputData.get(3);
		if (mGlobalInput == null)
		{
                    Mark = "前台传入的登陆信息为空!";

			return false;
		}
		if (StartDate == null || StartDate.equals(""))
		{
			Mark = "没有起始日期!";
			return false;
		}
		if (EndDate == null || EndDate.equals(""))
		{
			Mark = "没有终止日期!";
			return false;
		}

		return true;
	}
        /*****
                 * 固定写文件并且告诉该文件日志是否结束
                 * @param strMsg
                 * @param bFlag
                 */
                private void WriteLog(String strMsg,boolean bFlag){

//                        tLogInfoDeal.printotherlog(strMsg);
//                        tLogInfoDeal.completeotherlog(bFlag);
                }


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

        private boolean getPeriodManagement(String sDate, String eDate) {
                StringBuffer oStringBuffer = new StringBuffer(1024);
                oStringBuffer.append("进入会计期间核对，针对前台得到的起止日期核对是否有对应的开启会计期间对应。" + enter);

                String sql =
                            "  select * from FIPeriodManagement where StartDate<=to_date('?sDate?','yyyy-mm-dd') " +
                            " and EndDate>=to_date('?eDate?','yyyy-mm-dd') and state='1' order by StartDate asc";
                SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
                sqlbv1.sql(sql);
                sqlbv1.put("sDate", sDate);
                sqlbv1.put("eDate", eDate);
                    FIPeriodManagementSet tFIPeriodManagementSet = new
                            FIPeriodManagementSet();
                    FIPeriodManagementDB tFIPeriodManagementDB = new FIPeriodManagementDB();
                    logger.debug(sql);
                    tFIPeriodManagementSet = tFIPeriodManagementDB.executeQuery(sqlbv1);
                    if (tFIPeriodManagementSet.size() ==0) {
                        Mark = "您输入的时间区间所对应的会计区间尚未开启，请重新输入起始日期！";
                        oStringBuffer.append(Mark + enter);
                        WriteLog(oStringBuffer.toString(), false);
                        buildError("getPeriodManagement", Mark);
                        return false;
                    }else if(tFIPeriodManagementSet.size() !=1){
                        Mark = "查询到"+tFIPeriodManagementSet.size()+"个有效会计区间！开始日期分别是";

                        for(int i =0;i<mFIRulesVersionSet.size();i++){
                            if(i==0){
                                Mark += tFIPeriodManagementSet.get(i + 1).getstartdate();
                            }else{
                                Mark += "," + tFIPeriodManagementSet.get(i + 1).getstartdate();
                            }
                        }
                        oStringBuffer.append(Mark + enter);
                        WriteLog(oStringBuffer.toString(), false);
                        return false;
                    }
                    oStringBuffer.append("会计期间校验通过。" + enter);
                    WriteLog(oStringBuffer.toString(), false);
                    return true;
    }
	private boolean InitInfo()
	{
		try
		{
                    if (!getPeriodManagement(StartDate, EndDate)) {
                        throw new Exception("InitInfo初始化时校验会计期间失败");
                    }

                    //查询这期间有多少个版本
                    if (!getRuleVersion(StartDate, EndDate)) {
                        throw new Exception("InitInfo初始化时核对版本时失败");
                    }


                    FIDataDealTypeDefDB tFIDataDealTypeDefDB = new
                            FIDataDealTypeDefDB();
                    // 判断两种情况，1某种数据类型2所有数据类型
                    if (DataType == null || "".equals(DataType)) {
                    	SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
                    	sqlbv2.sql("select * from FIDataDealTypeDef where versionno='?VersionNo?'");
                    	sqlbv2.put("VersionNo", VersionNo);
                        mFIDataDealTypeDefSet = tFIDataDealTypeDefDB.
                                                executeQuery(sqlbv2);
                    } else {
                    	SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
                    	sqlbv3.sql("select * from FIDataDealTypeDef where versionno='?VersionNo?' and DataType= '?DataType?'");
                    	sqlbv3.put("VersionNo", VersionNo);
                    	sqlbv3.put("DataType", DataType);
                        mFIDataDealTypeDefSet = tFIDataDealTypeDefDB.
                                                executeQuery(sqlbv3);
                    }
                    if (mFIDataDealTypeDefSet.size() > 0) {
                        mFISEDistillDealDataType = new FISEDistillDealDataType[
                                mFIDataDealTypeDefSet.size()];
                        for (int i = 1; i <= mFIDataDealTypeDefSet.size(); i++) {
                            mFISEDistillDealDataType[i -
                                    1] = new FISEDistillDealDataType(tLogInfoDeal);
                            if (!mFISEDistillDealDataType[i -
                                1].
                                InitFISEDistillDealDataType(mFIDataDealTypeDefSet.
                                    get(i))) {
                                Mark = "数据类型初始化失败！";
                                return false;
                            }
                        }
                        return true;
                    } else {
                        Mark = "未找到本版本下任何数据类型定义，初始化失败";
                        return false;
                    }
		}
		catch (Exception ex)
		{
			buildError("InitInfo", ex.getMessage());
			return false;
		}

	}

	private boolean DealWithData()
	{
		try
		{
			String cycDate = this.StartDate;
			// VersionNo = mFIRulesVersionSet.get(1).getVersionNo();
			BatchNo = PubFun1.CreateMaxNo("FinBatch", 20);
//			while (cycDate.compareTo(this.EndDate) <= 0)
//			{
				mInputInfo.clear();
				mInputInfo.add(mGlobalInput);
				mInputInfo.add(StartDate);
				mInputInfo.add(EndDate);
				mInputInfo.add(BatchNo);
				mInputInfo.add(VersionNo);

				for (int i = 0; i < mFISEDistillDealDataType.length; i++)
				{
					if (!mFISEDistillDealDataType[i].dealProcess(mInputInfo))
					{
						this.mErrors.copyAllErrors(mFISEDistillDealDataType[i].mErrors);
						return false;
					}
					if (mFISEDistillDealDataType[i].mErrors.needDealError())
					{
						this.mErrors.copyAllErrors(mFISEDistillDealDataType[i].mErrors);
						return false;
					}

					// if (mFISEDistillDealDataType[i].mFIDataDealErrSet !=
					// null)
					// {
					// mFIDataDealErrSet.add(mFISEDistillDealDataType[i].mFIDataDealErrSet);
					// }
				}
//				cycDate = PubFun.calDate(cycDate, 1, "D", null);
//			}
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
