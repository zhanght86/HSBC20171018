package com.sinosoft.print.manage;
import org.apache.log4j.Logger;

import com.sinosoft.service.*;
import com.sinosoft.utility.*;

public class LPrtTempleteUI implements BusinessService
{
private static Logger logger = Logger.getLogger(LPrtTempleteUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	public LPrtTempleteUI()
	{}

	public boolean submitData(VData cInputData, String cOperate)
	{
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		LPrtTempleteBL tLPrtTempleteBL = new LPrtTempleteBL();

		tLPrtTempleteBL.submitData(cInputData, mOperate);
		// 如果有需要处理的错误，则返回
		if (tLPrtTempleteBL.mErrors.needDealError())
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tLPrtTempleteBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPrtTempleteUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate.equals("INSERT||MAIN") || mOperate.equals("UPDATE||MAIN"))
		{
			this.mResult.clear();
			this.mResult = tLPrtTempleteBL.getResult();
		}
		return true;
	}

	/** 获取处理结果 */
	public VData getResult()
	{
		return mResult;
	}

	/** 获取错误信息 */
	public CErrors getErrors()
	{
		return mErrors;
	}

}
