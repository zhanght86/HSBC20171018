package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class DealWithQuestUI {
private static Logger logger = Logger.getLogger(DealWithQuestUI.class);
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	public CErrors mErrors = new CErrors();
	private VData mInputData;

	/** 数据操作字符串 */
	private String mOperate;

	public DealWithQuestUI() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mOperate = cOperate;

		DealWithQuestBL tDealWithQuestBL = new DealWithQuestBL();

		logger.debug("---UI BEGIN---");
		if( !tDealWithQuestBL.submitData( cInputData, mOperate ) )
		{
			// @@错误处理
		//	CError.buildErr(this,"");
			mErrors.copyAllErrors(tDealWithQuestBL.mErrors);
			return false;
		}
		mResult = tDealWithQuestBL.getResult();
		return true;
	}

	/**
	 * 返回结果方法
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	// @Main
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "002";
		tG.ManageCom = "86";
		tG.ComCode = "01";
	}

}
