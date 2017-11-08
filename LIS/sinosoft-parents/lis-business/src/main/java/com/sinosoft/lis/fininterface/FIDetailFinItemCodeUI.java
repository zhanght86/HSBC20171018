package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.FIDetailFinItemCodeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.FIDetailFinItemCodeSchema;
import com.sinosoft.lis.vschema.FIDetailFinItemCodeSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;

/**
 * <p>
 * ClassName: FIDetailFinItemCodeUI
 * </p>
 * <p>
 * Description: 财务接口-财务规则参数管理-明细科目分支影射定义UI
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

public class FIDetailFinItemCodeUI  implements BusinessService
{
private static Logger logger = Logger.getLogger(FIDetailFinItemCodeUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData tTransferdata = new TransferData();

	/** 业务处理相关变量 */
	private FIDetailFinItemCodeSchema mFIDetailFinItemCodeSchema = new FIDetailFinItemCodeSchema();

	//private FIDetailFinItemCodeSet mFIDetailFinItemCodeSet = new FIDetailFinItemCodeSet();

	private FIDetailFinItemCodeBL mFIDetailFinItemCodeBL = new FIDetailFinItemCodeBL();

	private String mVersionNo = "";
	private String mFinItemID = "";
	private String mJudgementNo = "";
	private String mLevelConditionValue = "";	
	

	public FIDetailFinItemCodeUI()
	{}
	
	public String getVersionNo()
	{
		return mVersionNo;
	}	

	public String getFinItemID()
	{
		return mFinItemID;
	}
	
	public String getJudgementNo()
	{
		return mJudgementNo;
	}
	
	public String getLevelConditionValue()
	{
		return mLevelConditionValue;
	}
	

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
		logger.debug("getInputData");
		if (!getInputData(cInputData))
		{
			return false;
		}

		logger.debug("dealData");
		if (!dealData())
		{
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData())
		{
			return false;
		}
		logger.debug("Start FIDetailFinItemCodeUI Submit...");
		logger.debug("VersionNo:" + mFIDetailFinItemCodeSchema.getVersionNo());
		logger.debug("FinItemID:" + mFIDetailFinItemCodeSchema.getFinItemID());
		logger.debug("AssociatedID:" + mFIDetailFinItemCodeSchema.getJudgementNo());

		mFIDetailFinItemCodeBL.submitData(mInputData, mOperate);

		logger.debug("End FIDetailFinItemCodeUI Submit...");
		// 如果有需要处理的错误，则返回
		if (mFIDetailFinItemCodeBL.mErrors.needDealError())
		{
			// @@错误处理
			this.mErrors.copyAllErrors(mFIDetailFinItemCodeBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIDetailFinItemCodeUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate.equals("QUERY||MAIN"))
		{
			this.mResult.clear();
			this.mResult = mFIDetailFinItemCodeBL.getResult();
		}
		mInputData = null;
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * @param cInputData VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData)
	{
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0)); //(GlobalInput)强制类型转换
		this.mFIDetailFinItemCodeSchema.setSchema((FIDetailFinItemCodeSchema) cInputData.getObjectByObjectName("FIDetailFinItemCodeSchema", 0));
		if (mGlobalInput == null)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDetailFinItemCodeUI";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的信息！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * @return
	 */
	private boolean dealData()
	{
		boolean tReturn = true;

		if (this.mOperate.equals("INSERT||MAIN"))
		{
			//明细科目分支影射定义页面点击“添加”按钮重复录入的校验
			if (!FIDetailFinItemCodeInsertFail())
			{
				CError tError = new CError();
				tError.moduleName = "FIDetailFinItemCodeUI";
				tError.functionName = "dealData";
				tError.errorMessage = "您输入的记录在数据库中已经存在，请重新录入";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return tReturn;
	}
	
	private boolean FIDetailFinItemCodeInsertFail()
	{
		boolean tReturn = true;
		int Count;
		ExeSQL tExeSQL = new ExeSQL();
		String sql = "select COUNT(*) from FIDetailFinItemCode where VersionNo='?VersionNo?'  and FinItemID='?FinItemID?'  and JudgementNo='?JudgementNo?'  and LevelConditionValue='?LevelConditionValue?' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("VersionNo", this.mFIDetailFinItemCodeSchema.getVersionNo());
		sqlbv.put("FinItemID", this.mFIDetailFinItemCodeSchema.getFinItemID());
		sqlbv.put("JudgementNo", this.mFIDetailFinItemCodeSchema.getJudgementNo());
		sqlbv.put("LevelConditionValue", this.mFIDetailFinItemCodeSchema.getLevelConditionValue());
		Count = Integer.parseInt(tExeSQL.getOneValue(sqlbv));
		if (Count >= 1)
		{
			tReturn = false;
		}
		return tReturn;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 * @return
	 */
	private boolean prepareOutputData()
	{
		try
		{
			mInputData.clear();
			mInputData.add(this.mGlobalInput);
			mInputData.add(this.mFIDetailFinItemCodeSchema);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDetailFinItemCodeUI";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult()
	{
		tTransferdata.setNameAndValue("String", mJudgementNo);
    	mResult.add(tTransferdata);
		return this.mResult;

	}

	public static void main(String[] args)
	{
		VData tVData = new VData();
		String sqlstr = "select * from FIDetailFinItemCode where VersionNo='ver02' and FinItemID='000001' and JudgementNo='01' and LevelConditionValue='0' ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sqlstr);
		FIDetailFinItemCodeSchema tFIDetailFinItemCodeSchema = new FIDetailFinItemCodeSchema();
		FIDetailFinItemCodeSet tFIDetailFinItemCodeSet = new FIDetailFinItemCodeSet();
		FIDetailFinItemCodeDB tFIDetailFinItemCodeDB = tFIDetailFinItemCodeSchema.getDB();
		tFIDetailFinItemCodeSet = tFIDetailFinItemCodeDB.executeQuery(sqlbv1);
		if (tFIDetailFinItemCodeSet.size() > 0)
		{
			tFIDetailFinItemCodeSchema = tFIDetailFinItemCodeSet.get(1);
			tFIDetailFinItemCodeSchema.setVersionNo("ver1");
			tFIDetailFinItemCodeSchema.setFinItemID("000001");
			tFIDetailFinItemCodeSchema.setJudgementNo("01");
			tFIDetailFinItemCodeSchema.setLevelConditionValue("0");
			tFIDetailFinItemCodeSchema.setLevelCode("0401");
			tFIDetailFinItemCodeSchema.setNextJudgementNo("");			
			tFIDetailFinItemCodeSchema.setReMark("描述");
		}
		else
		{
			logger.debug("error");
			return;
		}
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tVData.clear();
		// 提交
		tVData.addElement(tG);
		tVData.addElement("861100");
		tVData.addElement(tFIDetailFinItemCodeSchema);
		tVData.addElement("5");
		FIDetailFinItemCodeUI tFIDetailFinItemCodeUI = new FIDetailFinItemCodeUI();
		tFIDetailFinItemCodeUI.submitData(tVData, "UPDATE||MAIN");
		// logger.debug("Error:" +
		// tFIDetailFinItemCodeUI.mErrors.getFirstError());
	}

	public CErrors getErrors() {
		return this.mErrors;
	}

}
