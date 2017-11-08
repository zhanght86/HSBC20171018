
package com.sinosoft.lis.fininterface.checkdata;
import org.apache.log4j.Logger;

import com.sinosoft.lis.fininterface.checkdata.FIRulePlanDefDetailBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.FIRulePlanDefDetailSchema;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;

public class FIRulePlanDefDetailUI  implements BusinessService
{
private static Logger logger = Logger.getLogger(FIRulePlanDefDetailUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	// 业务处理相关变量
	/** 全局数据 */
	private FIRulePlanDefDetailSchema  mFIRulePlanDefDetailSchema = new FIRulePlanDefDetailSchema(); 
	private GlobalInput mGlobalInput = new GlobalInput();
	
	public FIRulePlanDefDetailUI()
	{}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate)
	{
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		if (!getInputData(cInputData))
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
		FIRulePlanDefDetailBL tFIRulePlanDefDetailBL = new FIRulePlanDefDetailBL();
		logger.debug("Start FIRulePlanDefDetail UI Submit...");
		tFIRulePlanDefDetailBL.submitData(mInputData, mOperate);
		logger.debug("End FIRulePlanDefDetail UI Submit...");
		// 如果有需要处理的错误，则返回
		if (tFIRulePlanDefDetailBL.mErrors.needDealError())
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tFIRulePlanDefDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIRulePlanDefDetailUI";
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
	}

	private boolean prepareOutputData()
	{
		try
		{
			mInputData.clear();
			mInputData.add(this.mGlobalInput);
			mInputData.add(this.mFIRulePlanDefDetailSchema);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIRulePlanDefDetailUI";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData()
	{
		boolean tReturn = true;
		// 此处增加一些校验代码
		if(this.mOperate.equals("INSERT||MAIN"))
		{
			ExeSQL tExeSQL = new ExeSQL();
			String sql = "";
			int tCount;
			sql = "select COUNT(Sequence) from FIRulePlanDefDetail where VersionNo = '?VersionNo?' and RulePlanID = '?RulePlanID?' ";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("VersionNo", this.mFIRulePlanDefDetailSchema.getVersionNo());
			sqlbv.put("RulePlanID", this.mFIRulePlanDefDetailSchema.getRulePlanID());
			tCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv));
			logger.debug(tExeSQL.getOneValue(sqlbv));
			if(tCount == 0)
			{
				tReturn = InsertFirstValue();
			}
			else if(tCount >=1)
			{
				tReturn = InsertOtherValue();
			}
			if (!FIRulePlanDefDetailInsertFail())
			{
				CError tError = new CError();
				tError.moduleName = "FIRulePlanDefDetailUI";
				tError.functionName = "dealData";
				tError.errorMessage = "您输入的记录在数据库中已经存在，请重新录入";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return tReturn;
	}
	
	
	private boolean FIRulePlanDefDetailInsertFail()
	{
		boolean tReturn = true;
		int Count;
		ExeSQL tExeSQL = new ExeSQL();
		String sql = "select COUNT(*) from FIRulePlanDefDetail where VersionNo='?VersionNo?' and RulePlanID = '?RulePlanID?' and RuleID = '?RuleID?' ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("VersionNo", this.mFIRulePlanDefDetailSchema.getVersionNo());
		sqlbv1.put("RulePlanID", this.mFIRulePlanDefDetailSchema.getRulePlanID());
		sqlbv1.put("RuleID", this.mFIRulePlanDefDetailSchema.getRuleID());
		Count = Integer.parseInt(tExeSQL.getOneValue(sqlbv1));
		if (Count >= 1)
		{
			tReturn = false;
		}
		return tReturn;
	}
	
	private boolean InsertOtherValue()
	{
		ExeSQL tExeSQL = new ExeSQL();
		String sql = "";
		int tValue;
		sql="select MAX(Sequence) from FIRulePlanDefDetail where VersionNo = '?VersionNo?' and RulePlanID = '?RulePlanID?' ";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(sql);
		sqlbv2.put("VersionNo", this.mFIRulePlanDefDetailSchema.getVersionNo());
		sqlbv2.put("RulePlanID", this.mFIRulePlanDefDetailSchema.getRulePlanID());
		tValue = Integer.parseInt(tExeSQL.getOneValue(sqlbv2))+1;
		logger.debug(tExeSQL.getOneValue(sqlbv2));
		mFIRulePlanDefDetailSchema.setSequence(tValue);
		return true;
	}
	private boolean InsertFirstValue()
	{
		mFIRulePlanDefDetailSchema.setSequence(1) ;
		logger.debug("firstorder====="+mFIRulePlanDefDetailSchema.getSequence());
		return true;
	}


	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		this.mFIRulePlanDefDetailSchema.setSchema((FIRulePlanDefDetailSchema) cInputData.getObjectByObjectName("FIRulePlanDefDetailSchema", 0));
		if (mGlobalInput == null)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIRulePlanDefDetailUI";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的信息！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public CErrors getErrors() {
		return this.mErrors;
	}

	public VData getResult() {
		return null;
	}

}
