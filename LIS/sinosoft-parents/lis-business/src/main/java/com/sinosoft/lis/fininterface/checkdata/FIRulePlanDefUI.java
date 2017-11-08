
package com.sinosoft.lis.fininterface.checkdata;
import org.apache.log4j.Logger;

import com.sinosoft.lis.fininterface.checkdata.FIRulePlanDefBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.FIRulePlanDefSchema;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;

public class FIRulePlanDefUI  implements BusinessService
{
private static Logger logger = Logger.getLogger(FIRulePlanDefUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	// 业务处理相关变量
	/** 全局数据 */
	private FIRulePlanDefSchema  mFIRulePlanDefSchema = new FIRulePlanDefSchema(); 
	private GlobalInput mGlobalInput = new GlobalInput();
	
		public FIRulePlanDefUI()
	{}

	/**
	 * 传输数据的公共方法
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
		FIRulePlanDefBL tFIRulePlanDefBL = new FIRulePlanDefBL();
		logger.debug("Start FICostDataAcquisitionDef UI Submit...");
		tFIRulePlanDefBL.submitData(mInputData, mOperate);
		logger.debug("End FICostDataAcquisitionDef UI Submit...");
		// 如果有需要处理的错误，则返回
		if (tFIRulePlanDefBL.mErrors.needDealError())
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tFIRulePlanDefBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIRulePlanDefUI";
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
			mInputData.add(this.mFIRulePlanDefSchema);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIRulePlanDefUI";
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
		if(this.mOperate.equals("DELETE||MAIN"))
		{
			if(!FIRulePlanDefDetailDeleteFail())
			{
				CError tError = new CError();
				tError.moduleName = "FIRulePlanDefUI";
				tError.functionName = "dealData";
				tError.errorMessage = "该条记录已被校验计划明细定义页面引用，请先在子页面中删除这些被引用数据后再进行此操作";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		if(this.mOperate.equals("INSERT||MAIN"))
		{
			ExeSQL tExeSQL = new ExeSQL();
			String sql = "";
			int tCount;
			sql = "select COUNT(Sequence) from FIRulePlanDef where VersionNo = '?VersionNo?' ";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("VersionNo", this.mFIRulePlanDefSchema.getVersionNo());
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
		}
		return tReturn;
	}
	
	private boolean InsertOtherValue()
	{
		ExeSQL tExeSQL = new ExeSQL();
		String sql = "";
		int tValue;
		sql="select MAX(Sequence) from FIRulePlanDef where VersionNo = '?VersionNo?' ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("VersionNo", this.mFIRulePlanDefSchema.getVersionNo());
		tValue = Integer.parseInt(tExeSQL.getOneValue(sqlbv1))+1;
		logger.debug(tExeSQL.getOneValue(sqlbv1));
		mFIRulePlanDefSchema.setSequence(tValue);
		return true;
	}
	private boolean InsertFirstValue()
	{
		mFIRulePlanDefSchema.setSequence(1);
		logger.debug("firstorder====="+mFIRulePlanDefSchema.getSequence());
		return true;
	}
	
	private boolean FIRulePlanDefDetailDeleteFail()
	{
		boolean tReturn = true;
		int Count;
		ExeSQL tExeSQL = new ExeSQL();
		String sql = "select COUNT(*) from FIRulePlanDefDetail where VersionNo='?VersionNo?' and RulePlanID='?RulePlanID?'";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(sql);
		sqlbv2.put("VersionNo", this.mFIRulePlanDefSchema.getVersionNo());
		sqlbv2.put("RulePlanID", this.mFIRulePlanDefSchema.getRulesPlanID());
		Count = Integer.parseInt(tExeSQL.getOneValue(sqlbv2));
		if(Count >= 1)
		{
			tReturn = false;
		}
		return tReturn;
	}
	
		
	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		// 全局变量
		this.mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		this.mFIRulePlanDefSchema.setSchema((FIRulePlanDefSchema) cInputData.getObjectByObjectName("FIRulePlanDefSchema", 0));
		if (mGlobalInput == null)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIRulePlanDefUI";
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
