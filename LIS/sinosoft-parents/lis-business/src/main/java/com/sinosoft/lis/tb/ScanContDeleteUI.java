package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 6.0
 */
public class ScanContDeleteUI {
private static Logger logger = Logger.getLogger(ScanContDeleteUI.class);
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public ScanContDeleteUI() {
	}

	/**
	 * 不执行任何操作，只传递数据给下一层
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		ScanContDeleteBL tScanContDeleteBL = new ScanContDeleteBL();

		if (!tScanContDeleteBL.submitData(cInputData, cOperate)) {
			// 错误处理
			this.mErrors.copyAllErrors(tScanContDeleteBL.mErrors);
			return false;
		} else {
			// 获取BL层的结果
			mResult = tScanContDeleteBL.getResult();
		}
		return true;
	}

	/**
	 * 返回从BL层得到的结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		ScanContDeleteUI scancontdeleteui = new ScanContDeleteUI();
	}
}
