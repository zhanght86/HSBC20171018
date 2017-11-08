package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: BPODealInputDataUI
 * </p>
 * <p>
 * Description: 录入外包数据导入处理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author Fuqx
 * @version 1.0
 */

public class BPODealInputDataUI {
private static Logger logger = Logger.getLogger(BPODealInputDataUI.class);
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public BPODealInputDataUI() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		BPODealInputDataBL tBPODealInputDataBL = new BPODealInputDataBL();
		tBPODealInputDataBL.submitData(cInputData, cOperate);

		if (tBPODealInputDataBL.mErrors.needDealError()) {
			this.mErrors.clearErrors();
			boolean flag;

			for (int i = 0; i < tBPODealInputDataBL.mErrors.getErrorCount(); i++) {
				flag = true;

				for (int j = 0; j < this.mErrors.getErrorCount(); j++) {
					if (tBPODealInputDataBL.mErrors.getError(i).errorMessage
							.equals(this.mErrors.getError(j).errorMessage)) {
						flag = false;

						break;
					}
				}

				if (flag) {
					this.mErrors.addOneError(tBPODealInputDataBL.mErrors
							.getError(i));
				}
			}
			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		BPODealInputDataUI BPODealInputDataUI1 = new BPODealInputDataUI();
	}
}
