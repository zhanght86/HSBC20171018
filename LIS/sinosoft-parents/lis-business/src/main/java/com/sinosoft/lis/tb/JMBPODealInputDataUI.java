package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;

/**
 * <p>
 * Title: BPODealInputDataUI
 * </p>
 * <p>
 * Description: 录入外包数据导入处理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author ln
 * @version 1.0
 */

public class JMBPODealInputDataUI {
private static Logger logger = Logger.getLogger(JMBPODealInputDataUI.class);
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public JMBPODealInputDataUI() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(String tBussNo, GlobalInput tGI) {

		JMBPODealInputDataBL tJMBPODealInputDataBL = new JMBPODealInputDataBL();
		tJMBPODealInputDataBL.submitData(tBussNo, tGI);

		if (tJMBPODealInputDataBL.mErrors.needDealError()) {
			this.mErrors.clearErrors();
			boolean flag;

			for (int i = 0; i < tJMBPODealInputDataBL.mErrors.getErrorCount(); i++) {
				flag = true;

				for (int j = 0; j < this.mErrors.getErrorCount(); j++) {
					if (tJMBPODealInputDataBL.mErrors.getError(i).errorMessage
							.equals(this.mErrors.getError(j).errorMessage)) {
						flag = false;

						break;
					}
				}

				if (flag) {
					this.mErrors.addOneError(tJMBPODealInputDataBL.mErrors
							.getError(i));
				}
			}
			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		JMBPODealInputDataUI JMBPODealInputDataUI1 = new JMBPODealInputDataUI();
	}
}
