package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class LDScanUI implements BusinessService
{
private static Logger logger = Logger.getLogger(LDScanUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	public LDScanUI()
	{}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate)
	{
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		mInputData = cInputData;

		LDScanBL tLDScanBL = new LDScanBL();
		
		if (!tLDScanBL.submitData(mInputData, mOperate)){
			// @@错误处理
			this.mErrors.copyAllErrors(tLDScanBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDExRateUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mInputData = null;
		return true;
	}







	public VData getResult()
	{
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
