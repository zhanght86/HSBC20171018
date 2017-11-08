package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:暂收费修改
 * @version 1.0
 */
public class TempFeeUpdateUI implements BusinessService{
private static Logger logger = Logger.getLogger(TempFeeUpdateUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public TempFeeUpdateUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		logger.debug("UI BEGIN");
		this.mOperate = cOperate;
		mInputData = (VData) cInputData.clone();

		TempFeeUpdateBL tTempFeeUpdateBL = new TempFeeUpdateBL();

		if (tTempFeeUpdateBL.submitData(cInputData, mOperate) == false) 
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tTempFeeUpdateBL.mErrors);
			mInputData.clear();
			return false;
		}
		else
			mInputData = tTempFeeUpdateBL.getResult();
		logger.debug("tempfeeupdateUI End");

		return true;
	}

	public VData getResult() {
		return mInputData;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
