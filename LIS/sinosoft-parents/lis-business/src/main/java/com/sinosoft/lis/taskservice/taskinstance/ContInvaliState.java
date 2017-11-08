package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.f1print.BqNameFun;

/**
 * <p>Title: 失效批处理TASK类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008-10-15</p>
 * <p>Company: SinoSoft</p>
 * @author lijiaqiang
 * @version 1.0
 */

public class ContInvaliState extends TaskThread {
private static Logger logger = Logger.getLogger(ContInvaliState.class);
	public CErrors mErrors = new CErrors();

	public ContInvaliState() {
	}

	public boolean dealMain() {
		String tCurrentDate = PubFun.getCurrentDate();
		logger.debug("run begin,system date:" + tCurrentDate);

		String tManageCom = "";//放置需要处理的各分公司的代码。如果没有设置机构参数，则为空。
		String tmpString = "";
		//最多32个
		for (int i = 0; i <= 50; i++) {
			tmpString = (String) mParameters.get("ManageCom" + String.valueOf(i));
			if (tmpString != null && !tmpString.trim().equals("")) {
				tManageCom += "'" + tmpString + "',";
			}
		}
		tManageCom = BqNameFun.delPreSuffix(tManageCom, ",");//去掉字符串最后多余的逗号
		logger.debug("The manageCom is :" + tManageCom);
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ManageCom", tManageCom);
		tVData.add(tTransferData);

		//add by jiaqiangli 2008-10-15 失效批处理
		ContInvaliBL tContInvaliBL = new ContInvaliBL();
		
		logger.debug("start to deal Available");
		if (!tContInvaliBL.submitData(tVData, "Available")) {
			mErrors.copyAllErrors(tContInvaliBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "ContInvaliBL";
			tError.functionName = "run";
			tError.errorMessage = "调用错误ContInvaliBL";
			this.mErrors.addOneError(tError);
			logger.debug("run failed");
		}
		logger.debug("end to deal Available");
		
		return true;
	}
	public boolean dealMain(VData tVData) {
		String tCurrentDate = PubFun.getCurrentDate();
		logger.debug("run begin,system date:" + tCurrentDate);

		String tManageCom = "";//放置需要处理的各分公司的代码。如果没有设置机构参数，则为空。
		String tmpString = "";
		//最多32个
		for (int i = 0; i <= 50; i++) {
			tmpString = (String) mParameters.get("ManageCom" + String.valueOf(i));
			if (tmpString != null && !tmpString.trim().equals("")) {
				tManageCom += "'" + tmpString + "',";
			}
		}
		tManageCom = BqNameFun.delPreSuffix(tManageCom, ",");//去掉字符串最后多余的逗号
		logger.debug("The manageCom is :" + tManageCom);		
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ManageCom", tManageCom);
		tVData.add(tTransferData);

		//add by jiaqiangli 2008-10-15 失效批处理
		ContInvaliBL tContInvaliBL = new ContInvaliBL();
		
		logger.debug("start to deal Available");
		if (!tContInvaliBL.submitData(tVData, "Available")) {
			mErrors.copyAllErrors(tContInvaliBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "ContInvaliBL";
			tError.functionName = "run";
			tError.errorMessage = "调用错误ContInvaliBL";
			this.mErrors.addOneError(tError);
			logger.debug("run failed");
		}
		logger.debug("end to deal Available");
		
		return true;
	}
	public static void main(String[] args) {
		ContInvaliState tContInvaliState = new ContInvaliState();
		tContInvaliState.dealMain();
	}
}
