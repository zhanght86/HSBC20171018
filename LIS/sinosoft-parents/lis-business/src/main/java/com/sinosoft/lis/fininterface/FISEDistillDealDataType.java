package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.vschema.FICostDataAcquisitionDefSet;
import com.sinosoft.lis.db.FICostDataAcquisitionDefDB;
import com.sinosoft.lis.schema.FIDataDealTypeDefSchema;
import com.sinosoft.lis.pubfun.PubFun;

/**
 * <p>
 * ClassName: FISEDistillDealDataType
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

public class FISEDistillDealDataType
{
private static Logger logger = Logger.getLogger(FISEDistillDealDataType.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 存储处理信息 */
	private VData mInputInfo = new VData();

	private FIDataDealTypeDefSchema mFIDataDealTypeDefSchema = new FIDataDealTypeDefSchema();

	private FISEDistillDealAquisition mFISEDistillDealAquisition[];

	/** 业务数据 */
	GlobalInput mGlobalInput = new GlobalInput();

	String StartDate = "";

	String EndDate = "";
        public LogInfoDeal tLogInfoDeal ;
	String BatchNo = "";
        String ErrInfo = "";
        String ErrType = "";
        private final String enter = "\r\n"; // 换行符
        StringBuffer logString = new StringBuffer();
	String VersionNo = "";
        public FiDistillErrInfo fidistillerrinfo = new FiDistillErrInfo();
	public FISEDistillDealDataType(LogInfoDeal oDeal)
	{
            tLogInfoDeal = oDeal;
        }

	public static void main(String[] args)
	{
		//FISEDistillDealDataType FISEDistillDealDataType = new FISEDistillDealDataType();
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
		if (!WriteLog("SUCC"))
		{
			return false;
		}

		return true;
	}

        /*****
         * 固定写文件并且告诉该文件日志是否结束
         * @param strMsg
         * @param bFlag
         */
        private void WriteLog(String strMsg, boolean bFlag) {
//
//            tLogInfoDeal.printotherlog(strMsg);
//            tLogInfoDeal.completeotherlog(bFlag);
        }
        public void AddLogString(String lineString){
               logString.append(lineString + enter);
       }


	private boolean getInputData(VData cInputData)
	{
            try {
                WriteLog("进入FIDistillDealCertificate的getInputData方法", false);
                mGlobalInput.setSchema((GlobalInput) cInputData.
                                       getObjectByObjectName("GlobalInput", 0));
                StartDate = (String) cInputData.get(1);
                EndDate = (String) cInputData.get(2);
                BatchNo = (String) cInputData.get(3);
                VersionNo = (String) cInputData.get(4);
                //mFIDataDealTypeDefSchema =(FIDataDealTypeDefSchema)cInputData.getObjectByObjectName("FIDataDealTypeDefSchema", 0);

                if (mGlobalInput == null) {
                    buildError("getInputData", "前台传入的登陆信息为空!");
                    return false;
                }
                if (StartDate == null || StartDate.equals("")) {
                    buildError("getInputData", "没有起始日期!");
                    return false;
                }
                if (EndDate == null || EndDate.equals("")) {
                    buildError("getInputData", "没有终止日期!");
                    return false;
                }
                if (BatchNo == null || BatchNo.equals("")) {
                    buildError("getInputData", "没有批次号码!");
                    return false;
                }
                if (VersionNo == null || VersionNo.equals("")) {
                    buildError("getInputData", "没有版本号码!");
                    return false;
                }

                return true;
            } catch (Exception e) {
                        buildError("getInputData", "获取参数时出现异常，异常信息为：" + e.getMessage());
                        WriteLog("获取参数时出现异常，异常信息为：" + e.getMessage(), false);
                        return false;
                }
	}

	public boolean InitFISEDistillDealDataType(FIDataDealTypeDefSchema tFIDataDealTypeDefSchema)
	{
		mFIDataDealTypeDefSchema = tFIDataDealTypeDefSchema;
		return true;
	}

	private boolean InitInfo()
	{
		try
		{
			mInputInfo.clear();
			mInputInfo.add(mGlobalInput);
			mInputInfo.add(StartDate);
			mInputInfo.add(EndDate);
			mInputInfo.add(BatchNo);
			mInputInfo.add(VersionNo);
	        mInputInfo.add(mFIDataDealTypeDefSchema);

			String DataType = mFIDataDealTypeDefSchema.getDataType();
			FICostDataAcquisitionDefSet tFICostDataAcquisitionDefSet = new FICostDataAcquisitionDefSet();
			FICostDataAcquisitionDefDB tFICostDataAcquisitionDefDB = new FICostDataAcquisitionDefDB();
			tFICostDataAcquisitionDefDB.setCostOrDataID(DataType);
			tFICostDataAcquisitionDefDB.setVersionNo(VersionNo);
			tFICostDataAcquisitionDefDB.setAcquisitionType("02");
			tFICostDataAcquisitionDefSet = tFICostDataAcquisitionDefDB.query();

			if (tFICostDataAcquisitionDefSet.size() > 0)
			{
				mFISEDistillDealAquisition = new FISEDistillDealAquisition[tFICostDataAcquisitionDefSet.size()];
				for (int i = 1; i <= tFICostDataAcquisitionDefSet.size(); i++)
				{
					mFISEDistillDealAquisition[i - 1] = new FISEDistillDealAquisition();
					if (!mFISEDistillDealAquisition[i - 1].InitFISEDistillDealAquisition(tFICostDataAcquisitionDefSet.get(i)))
					{
						buildError("InitInfo", "处理引擎初始化失败");
						return false;
					}
				}
				return true;
			}
			else
			{
				buildError("InitInfo", "处理引擎初始化失败,无任何数据类型定义");
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
			for (int i = 0; i < mFISEDistillDealAquisition.length; i++)
			{
				if (!mFISEDistillDealAquisition[i].dealProcess(mInputInfo))
				{
					this.mErrors.copyAllErrors(mFISEDistillDealAquisition[i].mErrors);
					return false;
				}

				if (mFISEDistillDealAquisition[i].mErrors.needDealError())
				{
					this.mErrors.copyAllErrors(mFISEDistillDealAquisition[i].mErrors);
					return false;
				}
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
