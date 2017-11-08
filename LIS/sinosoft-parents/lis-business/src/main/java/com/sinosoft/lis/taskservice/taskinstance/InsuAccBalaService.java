package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.InsuAccBalaManu;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.InsuAccBala;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 万能险保单账户结算
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: ChinaSoft
 * </p>
 * 
 * @author zhangtao pst
 * @version 1.0 2.0
 * @CreateDate：2005-07-28 2007-11-22
 */
public class InsuAccBalaService extends TaskThread {
private static Logger logger = Logger.getLogger(InsuAccBalaService.class);
	public InsuAccBalaService() {
	}

	public boolean dealMain() {

		//处理的机构
		GlobalInput tGlobalInput = (GlobalInput) mParameters.get("GlobalInput");

		if (tGlobalInput == null) {
			tGlobalInput = new GlobalInput();
			tGlobalInput.Operator = "001";
			tGlobalInput.ManageCom = "86";
		}
		//结算日
		String tBalaDate = (String) mParameters.get("BalaDate");
		logger.debug("The BalaDate is :" + tBalaDate);

		//险种
		String tRiskCode = (String) mParameters.get("RiskCode");
		logger.debug("The RiskCode is :" + tRiskCode);
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("BalaDate", tBalaDate);
		tTransferData.setNameAndValue("RiskCode", tRiskCode);
		tTransferData.setNameAndValue("ManageCom", tGlobalInput.ManageCom);

		VData tVData = new VData();
		tVData.addElement(tTransferData);
		InsuAccBalaManu tInsuAccBalaManu = new InsuAccBalaManu();
		if (!tInsuAccBalaManu.submitData(tVData, ""))
		{
			return false;
		}
		return true;
	}

	public static void main(String str[]) {
		InsuAccBalaService tInsuAccBalaService = new InsuAccBalaService();
		tInsuAccBalaService.dealMain();
		// tInsuAccBalaService.run();
	}
}
