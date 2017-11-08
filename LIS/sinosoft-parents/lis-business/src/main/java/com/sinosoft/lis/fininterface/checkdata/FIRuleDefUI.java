
package com.sinosoft.lis.fininterface.checkdata;
import org.apache.log4j.Logger;

import com.sinosoft.lis.fininterface.checkdata.FIRuleDefBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.FIRuleDefSchema;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;

public class FIRuleDefUI  implements BusinessService
{
private static Logger logger = Logger.getLogger(FIRuleDefUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	// 业务处理相关变量
	/** 全局数据 */
	private FIRuleDefSchema  mFIRuleDefSchema = new FIRuleDefSchema(); 
	private GlobalInput mGlobalInput = new GlobalInput();
	
	public FIRuleDefUI()
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
		FIRuleDefBL tFIRuleDefBL = new FIRuleDefBL();
		logger.debug("Start FIRuleDefUI Submit...");
		tFIRuleDefBL.submitData(mInputData, mOperate);
		logger.debug("End FIRuleDefUI Submit...");
		// 如果有需要处理的错误，则返回
		if (tFIRuleDefBL.mErrors.needDealError())
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tFIRuleDefBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIRuleDefUI";
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
			mInputData.add(this.mFIRuleDefSchema);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIRuleDefUI";
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
		return tReturn;
	}


	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		this.mFIRuleDefSchema.setSchema((FIRuleDefSchema) cInputData.getObjectByObjectName("FIRuleDefSchema", 0));
		if (mGlobalInput == null)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIRuleDefUI";
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
