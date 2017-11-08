package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class DELPictureUI {
private static Logger logger = Logger.getLogger(DELPictureUI.class);
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/**
	 * 09-11-24 回执核销界面的“删除影像件”功能
	 * */
	public DELPictureUI() {
	}

	// @Method
	/**
	 * 数据提交方法
	 * 
	 * @param cInputData
	 *            传入的数据
	 * @param cOperate
	 *            数据操作字符串
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mOperate = cOperate;

		DELPictureBL DELPictureBL = new DELPictureBL();

		logger.debug("---UI BEGIN---");
		if (DELPictureBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(DELPictureBL.mErrors);
			return false;
		} else {
			mResult = DELPictureBL.getResult();
		}
		logger.debug(mErrors.toString());
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		

	}
}
