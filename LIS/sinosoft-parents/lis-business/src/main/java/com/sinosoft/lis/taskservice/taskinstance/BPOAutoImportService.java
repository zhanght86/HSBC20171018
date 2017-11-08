package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.tb.BPODealInputDataUI;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 业务系统
 * </p>
 * <p>
 * Description: 录入外包数据处理－后台自动处理入口
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Fuqx
 * @version 1.0
 */

public class BPOAutoImportService extends TaskThread {
private static Logger logger = Logger.getLogger(BPOAutoImportService.class);
	public BPOAutoImportService() {
	}

	public boolean dealMain() {
		/* 业务处理逻辑 */
		logger.debug("...BPOAutoImportService Start..."
				+ PubFun.getCurrentDate() + " " + PubFun.getCurrentTime());
		try {
			// add 2008.5.27
			 String tOperator = (String)this.mParameters.get("WBOperator");
			// test
//			String tOperator = "WB001";
			// ended add 2008.5.27

			// 准备传输数据 VData
			VData tVData = new VData();
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("tOperator", tOperator);
			tVData.add(tTransferData);
			// 数据传输
			BPODealInputDataUI tBPODealInputDataUI = new BPODealInputDataUI();
			tBPODealInputDataUI.submitData(tVData, "INSERT");

			int n = tBPODealInputDataUI.mErrors.getErrorCount();
			logger.debug("---ErrCount---" + n);
			String Content;
			if (n == 0) {
				Content = "处理成功! ";
			} else {
				String strErr = "\\n";
				for (int i = 0; i < n; i++) {
					strErr += (i + 1)
							+ ": "
							+ tBPODealInputDataUI.mErrors.getError(i).errorMessage
							+ "; \\n";
				}
				Content = "部分投保单处理失败，原因是: " + strErr;
			}
			logger.debug(Content);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.debug("...BPOAutoImportService End..."
				+ PubFun.getCurrentDate() + " " + PubFun.getCurrentTime());
		return true;
		/* end 业务处理逻辑 */
	}

	public static void main(String[] args) {
		BPOAutoImportService bpoautoimportservice = new BPOAutoImportService();
		bpoautoimportservice.dealMain();
	}
}
